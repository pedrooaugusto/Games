package com.pedroaugusto.cobrinha;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Looper;
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
    private Cobra cobra;
    private Moeda moeda;
    public boolean PAUSE = false;
    public boolean gameOver = false;
    private TempoMoeda tempo;
    private ControleBotao botao;
    public boolean retry = false;
    private Colisao colisao;
    public GameView(Context context) {
        super(context);
        //colisao = new Colisao(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //colisao = new Colisao(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //colisao = new Colisao(context);
        init(context);
    }
    private void init(Context context)
    {
        this.ALTURA_TELA = getSizeScreen(context).y;
        this.LARGURA_TELA = getSizeScreen(context).x;
        this.moeda = new Moeda();
        this.cobra = new Cobra();
        this.cobra.initCobra();
        this.botao = new ControleBotao();
        tempo = new TempoMoeda(moeda, cobra);
        colisao = new Colisao(this);
        p.setAntiAlias(true);
        getHolder().addCallback(this);
        myThread = new Loop(this, getHolder());
    }
    private void startGame()
    {
        this.moeda = new Moeda();
        cobra.initCobra();
        p = new Paint();
        p.setAntiAlias(true);
        PAUSE = false;
       // gameOver = true;
    }
    public void pause(boolean really)
    {
        if(!really)
            gameOver = true;
        else
        {
            gameOver = true;
            retry = true;
        }
    }
    public void continuar(boolean really)
    {
       if(!really)
           gameOver = false;
       else
       {
           tempo.restart();
           startGame();
           gameOver = false;
           retry = false;
       }
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
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(!retry)
            {
                int a = botao.onClick(event.getX(), event.getY());
                if(a == 41 && !retry)
                {
                    if(gameOver)
                        continuar(false);
                    else
                        pause(false);
                    Vibrator v = (Vibrator) getContext().
                            getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(50);
                }
                else if(a != 0)
                {
                    cobra.choose(a);
                    Vibrator v = (Vibrator) getContext().
                            getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(50);
                }
            }
            else
            {
                continuar(true);
            }
        }
        return true;
    }

    public void update(Canvas ctx)
    {
        {
            clear(ctx);
            drawMenu(ctx, p);
            cobra.move();
            moeda.move();
            colisao.algumaColisao(cobra, moeda, tempo);
            botao.draw(ctx, p);
            cobra.draw(ctx, p);
            moeda.draw(ctx, p);
        }
    }
    private void drawMenu(Canvas canvas, Paint p)
    {
        String texto = "Pontos:  "+cobra.getPartes().size()+"  " +
                "Left "+tempo.getInfo()+"s to lose "+cobra.getPerdeSeMorrer()+
                " units";
        p.setTextSize(LARGURA_TELA*0.05f);
        Rect bounds = new Rect();
        p.getTextBounds(texto, 0, texto.length(), bounds);
        p.setColor(Color.BLACK);
        canvas.drawRect(0, 0, LARGURA_TELA, ALTURA_TELA*0.13f, p);
        p.setColor(Color.WHITE);
        canvas.drawText(texto, LARGURA_TELA*0.5f - bounds.width()*0.5f,
                ((ALTURA_TELA*0.13f)*0.5f) + bounds.height(), p);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        myThread.setRunning(true);
        myThread.start();
        tempo.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        myThread.setRunning(false);
        tempo.terminar();
        while (retry) {
            try {
                myThread.join();
                tempo.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
    private void clear(Canvas ctx)
    {
        Paint p32 = new Paint();
        p32.setColor(new Color().rgb(46, 25, 97));
        ctx.drawRect(0, 0, LARGURA_TELA, ALTURA_TELA, p32);
    }
}
