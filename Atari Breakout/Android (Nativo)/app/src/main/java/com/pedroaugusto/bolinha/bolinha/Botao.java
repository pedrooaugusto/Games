package com.pedroaugusto.bolinha.bolinha;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Pedro on 08/06/2016.
 */
public abstract class Botao extends ObjetoRectangulo {

    public boolean isPressed = false;
    public String texto;
    public Botao(String texto)
    {
        this.texto = texto;
    }
    @Override
    public abstract void draw(Canvas canvas, Paint p);

    public boolean click(float x, float y)
    {
        if((x > getX()) && (x < (getX() + getWidth()))
                && (y > getY()) && (y < (getY() + getHeight())))
            return true;
        return false;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public static class BotaoMovimento extends Botao
    {
        public BotaoMovimento(String texto, char direction) {
            super(texto);
            float wt = GameView.LARGURA_TELA, ht = GameView.ALTURA_TELA;
            if(direction == 'l')
            {
                setPosisao((0.02f*wt), (0.77f*ht));
                setDimensao((0.15f*wt), (0.20f*ht));
            }
            else
            {
                setPosisao((0.83f*wt), (0.77f*ht));
                setDimensao((0.15f*wt), (0.20f*ht));
            }
        }
        @Override
        public void draw(Canvas c, Paint p) {
            if(isPressed)
            {
                p.setColor(new Color().parseColor("#414142"));
            }
            else
                p.setColor(new Color().parseColor("#151517"));
            Retangulo r = new Retangulo(getX(), getY(), getWidth(), getHeight()).toRect();
            c.drawRect(r.x, r.y, r.w, r.h, p);
            p.setColor(new Color().parseColor("#04174E"));
            c.drawCircle(getX() + (getWidth() / 2), getY() + (getHeight() / 2), getHeight() / 2.3f, p);
            p.setColor(Color.WHITE);
            p.setTextSize(getHeight()/2);
            c.drawText(texto, getX() + (getHeight()/2)*0.9f, getY() + (getHeight()/2)*1.25f, p);
        }
    }

    public static class BotaoRetry extends Botao
    {
        private String message;
        public BotaoRetry() {
            super("RETRY");
            setDimensao((GameView.LARGURA_TELA*0.4f),
                    (GameView.ALTURA_TELA*0.15f));
            setPosisao((GameView.LARGURA_TELA*0.3f),
                    (GameView.ALTURA_TELA*0.425f));
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public void draw(Canvas canvas, Paint p) {

            Retangulo g = new Retangulo(getX(), getY(), getWidth(), getHeight()).toRect();
            Rect reta = new Rect((int)g.x, (int)g.y, (int)g.w, (int)g.h);
            Rect bounds = new Rect();
            p.setDither(true);
            p.setColor(Color.rgb(22, 73, 154));
            p.setShadowLayer(4, 2, 2, Color.DKGRAY);
            p.setTextSize(GameView.LARGURA_TELA*0.05f);
                canvas.drawRect(reta, p);
            p.setColor(Color.WHITE);
            p.getTextBounds(texto, 0, texto.length(), bounds);
                canvas.drawText(texto, (getX() + getWidth())/2 + (bounds.width()/2),
                        ((getY() + getHeight())) - (bounds.height()/2f), p);
            p.setShadowLayer(10, -2, -2, Color.LTGRAY);
            p.getTextBounds(message, 0, message.length(), bounds);
                canvas.drawText(message, (GameView.LARGURA_TELA/2) - (bounds.width()/2),
                        GameView.ALTURA_TELA*0.3f, p);
        }
        public void drawProgress(Canvas canvas, Paint p, float progress)
        {
            Retangulo g = new Retangulo(getX()*progress, getY(), getWidth()*progress,
                    getHeight());
            Retangulo agora = g;
            g = g.toRect();
            Rect reta = new Rect((int)g.x, (int)g.y, (int)g.w, (int)g.h);
            Rect bounds = new Rect();
            p.setDither(true);
            p.setColor(Color.rgb(22, 73, 154));
            p.setShadowLayer(4, 2, 2, Color.DKGRAY);
            p.setTextSize((GameView.LARGURA_TELA*0.05f)*progress);
            canvas.drawRect(reta, p);
            p.setColor(Color.WHITE);
            p.getTextBounds(texto, 0, texto.length(), bounds);
            canvas.drawText(texto, (getX()*progress + getWidth()*progress)/2 + (bounds.width()/2),
                    ((getY() + getHeight())) - (bounds.height()/2f), p);
            p.setShadowLayer(10, -2, -2, Color.LTGRAY);
            p.getTextBounds(message, 0, message.length(), bounds);
            canvas.drawText(message, (GameView.LARGURA_TELA/2)*progress - (bounds.width()/2),
                    (GameView.ALTURA_TELA*0.3f), p);
            p.setMaskFilter(null);
            p.setShadowLayer(0,0,0, Color.TRANSPARENT);
        }
    }
}
