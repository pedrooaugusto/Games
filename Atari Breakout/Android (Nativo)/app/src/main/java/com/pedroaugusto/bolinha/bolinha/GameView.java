package com.pedroaugusto.bolinha.bolinha;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Pedro on 07/06/2016.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    public static float LARGURA_TELA;
    public static float ALTURA_TELA;
    private Loop myThread;
    private Paint p = new Paint();
    private Barra barra;
    private Bola bola;
    private Botoes botoes;
    private final Colisao colisao;
    private ControleObstaculo obstaculos;
    private Botao.BotaoRetry retry;
    public boolean PAUSE = false;
    private final GameOver gameOver = new GameOver();
    public GameView(Context context) {
        super(context);
        colisao = new Colisao(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        colisao = new Colisao(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        colisao = new Colisao(context);
        init(context);
    }
    private void init(Context context)
    {
        this.ALTURA_TELA = getSizeScreen(context).y;
        this.LARGURA_TELA = getSizeScreen(context).x;
        this.obstaculos = new ControleObstaculo();
        this.barra = new Barra();
        this.bola = new Bola();
        this.botoes = new Botoes();
        this.retry = new Botao.BotaoRetry();
        p.setAntiAlias(true);
        getHolder().addCallback(this);
        myThread = new Loop(this, getHolder());
    }
    private void startGame()
    {
        this.obstaculos = new ControleObstaculo();
        this.barra = new Barra();
        this.bola = new Bola();
        this.botoes = new Botoes();
        this.retry = new Botao.BotaoRetry();
        p = new Paint();
        p.setAntiAlias(true);
        barra.setVidas(3);
        PAUSE = false;
    }
    private Point getSizeScreen(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        return size;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(!PAUSE && !gameOver.isAlive())
        {
            if(botoes.click(event) && event.getAction() == MotionEvent.ACTION_DOWN)
            {
                Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(50);
            }
        }
        if(PAUSE)
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN && retry.click(event.getX(), event.getY()))
            {
                 startGame();
            }
        }
        return true;
    }

    public void update(Canvas ctx)
    {
        if(!gameOver.isAlive())
        {
            clear(ctx);
            barra.atualizarVelocidade(botoes.getButtons());
            barra.move();
            bola.move();
            colisao.algumaColisao(bola, barra, obstaculos.getList());
            botoes.draw(ctx, p);
            barra.draw(ctx, p);
            obstaculos.draw(ctx, p);
            bola.draw(ctx, p);
            if(barra.getVidas() == -1 || obstaculos.getList().size() == 0)
            {
                gameOver.setAlive(true);
            }
        }
        else
        {
            if(obstaculos.getList().size() == 0)
                retry.setMessage("YOU WIN!");
            else
                retry.setMessage("GAME OVER");
            if(gameOver.getWidth() > LARGURA_TELA)
            {
                gameOver.reset();
                PAUSE = true;
                clear(ctx);
                retry.draw(ctx, p);
            }
            else
            {
                clear(ctx);
                retry.drawProgress(ctx, p, gameOver.getWidth() / LARGURA_TELA);
                gameOver.attWidth();
            }
        }
    }
    class GameOver
    {
        private int width = 0;
        private boolean isAlive = false;

        public boolean isAlive() {
            return isAlive;
        }

        public void setAlive(boolean alive) {
            isAlive = alive;
        }

        public void reset()
        {
            width = 0;
            isAlive = false;
        }
        public void attWidth()
        {
            width+=LARGURA_TELA/10;
        }
        public int getWidth()
        {
            return width;
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        myThread.setRunning(true);
        myThread.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        myThread.setRunning(false);
        while (retry) {
            try {
                myThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    private void clear(Canvas ctx)
    {
        Paint p32 = new Paint();
        p32.setColor(Color.BLACK);
        ctx.drawRect(0, 0, LARGURA_TELA, ALTURA_TELA, p32);
    }
}
