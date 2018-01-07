package com.pedroaugusto.cobrinha;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pedro on 19/07/2016.
 */
public class ControleBotao
{
    private Map<Integer, Botao> map = new HashMap<>(4);
    public ControleBotao()
    {
        Botao up = new Botao(GameView.LARGURA_TELA*(0.5f-0.075f),
                GameView.ALTURA_TELA*0.65f, "Up");
        Botao down = new Botao(GameView.LARGURA_TELA*(0.5f-0.075f),
                GameView.ALTURA_TELA*0.65f+(GameView.LARGURA_TELA*0.15f*2), "Down");
        Botao left = new Botao(GameView.LARGURA_TELA*(0.275f),
                GameView.ALTURA_TELA*0.7345f, "Left");
        Botao right = new Botao(GameView.LARGURA_TELA*(0.575f),
            GameView.ALTURA_TELA*0.7345f, "Right");
        Botao pause = new Botao(GameView.LARGURA_TELA*0.82f,
                GameView.ALTURA_TELA*0.83f, "P/C");
        map.put(38, up);
        map.put(40, down);
        map.put(37, left);
        map.put(39, right);
        map.put(41, pause);
    }
    public void draw(Canvas canvas, Paint p)
    {
        for(Botao b : map.values()){
            b.draw(canvas, p);
        }
    }
    public int onClick(float x, float y)
    {
        int d[] = {38, 40, 37, 39, 41};
        for(int i = 0; i < 5; i++){
            if(map.get(d[i]).click(x, y))
                return d[i];
        }
        return 0;
    }
    private class Botao extends ObjetoRectangulo
    {
        private String text;
        public Botao(float x, float y, String text)
        {
            this.text = text;
            setNome("Botao");
            setCor(new Color().argb(138, 96, 98, 101));
            setDimensao(GameView.LARGURA_TELA*0.15f, GameView.LARGURA_TELA*0.15f);
            setPosisao(x, y);
        }
        public boolean click(float x, float y)
        {
            if((x > getX() && x < getX() + getWidth()) &&
                    (y > getY() && y < getY() + getHeight()))
                return true;
            return false;
        }
        @Override
        public void draw(Canvas canvas, Paint p) {
            p.setColor(getCor());
            Rect bounds = new Rect();
            Retangulo r = new Retangulo(getX(), getY(), getWidth(), getHeight());
            r = r.toRect();
            canvas.drawRect(r.x, r.y, r.w, r.h, p);
            p.setTextSize(GameView.LARGURA_TELA*0.05f);
            p.setColor(Color.WHITE);
            p.getTextBounds(text, 0, text.length(), bounds);
            canvas.drawText(text, (getX()+getWidth()*0.5f) - bounds.width()/2,
                    getY()+getHeight()*0.5f+bounds.height()*0.5f, p);
        }
    }
}
