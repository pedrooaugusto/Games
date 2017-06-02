package com.pedroaugusto.bolinha.bolinha;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
/*
    ISSO NÃO ESTA MAIS EM USO
    PARECE QUE CHAMAR O DRAW
    DE UMA VIEW SOMENTE PARA TER
    ACESSO NOVAMENTE AO CANVAS
    DEMORA MUITO EM FUNÇÃO DISSO
    MUDEI PARA SURFACEVIEW, MAS
    AINDA NOTO UM LAG...
 */
public class DepreciatedGameMain extends View{
    public static float ALTURA_TELA;
    public static float LARGURA_TELA;
    public final int frames = 15;
    private final Handler handler = new Handler();
    private final Runnable run = new Runnable(){
        public void run()
        {
            invalidate();
        }
    };
    private final Paint p = new Paint();
    private final Barra barra;
    private final Bola bola;
    private final Botoes botoes;
    private final Colisao colisao;
    private final ControleObstaculo obstaculos;
    public DepreciatedGameMain(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        colisao = new Colisao(context);
        this.ALTURA_TELA = getSizeScreen(context).y;
        this.LARGURA_TELA = getSizeScreen(context).x;
        this.obstaculos = new ControleObstaculo();
        this.barra = new Barra();
        this.bola = new Bola();
        this.botoes = new Botoes();
        p.setAntiAlias(true);
    }
    @Override
    public void onDraw(final Canvas canvas)
    {
        //super.onDraw(canvas);
        /*Bitmap bitmap = Bitmap.createBitmap((int)LARGURA_TELA, (int)ALTURA_TELA, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);*/
        loop(canvas);
        p.setColor(Color.WHITE);
        p.setTextSize(25);
        canvas.drawText(canvas.isHardwareAccelerated()+"", 300, 300, p);
        //setBackgroundDrawable(new BitmapDrawable(bitmap));
        handler.postDelayed(run, frames);
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
            Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
        }
        botoes.click(event);
        return true;
    }
    public void loop(final Canvas ctx)
    {
        barra.atualizarVelocidade(botoes.getButtons());
        barra.move();
        bola.move();
        colisao.algumaColisao(bola, barra, obstaculos.getList());
        botoes.draw(ctx, p);
        barra.draw(ctx, p);
        obstaculos.draw(ctx, p);
        bola.draw(ctx, p);
    }
    public void drawCoordenadas(ObjetoRectangulo c, Canvas ctx)
    {
        //ctx.drawText();
    }
   /* public static Rect getRectRelative(float x, float y, float width, float height)
    {
        Rect r = new Rect();
        r.set((int)(x*LARGURA_TELA), (int)(y*ALTURA_TELA), (int)((x*LARGURA_TELA)+(width*LARGURA_TELA)),
                (int)((y*ALTURA_TELA)+(height*ALTURA_TELA)));
        return r;
    }
    public  static Rect getRect(float x, float y, float width, float height)
    {
        Rect r = new Rect();
        r.set((int)(x), (int)(y), (int)((x)+(width)),
                (int)((y)+(height)));
        return r;
    }*/
}
