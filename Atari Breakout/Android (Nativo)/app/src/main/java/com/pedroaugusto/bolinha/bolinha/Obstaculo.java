package com.pedroaugusto.bolinha.bolinha;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Pedro on 06/06/2016.
 */
public class Obstaculo extends ObjetoRectangulo {
    public static float height = GameView.ALTURA_TELA * 0.04f;
    public static float width = GameView.LARGURA_TELA * 0.20f;
    private final int[] color;
    public Obstaculo(int x, int y, int... rgb)
    {
        setDimensao((int)dimensions().x, dimensions().y);
        setPosisao(x, y);
        this.color = rgb;
    }
    @Override
    public void draw(Canvas canvas, Paint p)
    {
        Retangulo g = new Retangulo(getX(), getY(), getWidth(), getHeight()).toRect();
        p.setColor(new Color().rgb(color[0], color[1], color[2]));
        canvas.drawRect(g.x, g.y, g.w, g.h, p);
    }
    public static Point dimensions()
    {
        float w =  getCalculatedDimension(width, GameView.LARGURA_TELA*0.95f);
        float h =  getCalculatedDimension(height, GameView.ALTURA_TELA*0.95f);
        return new Point(w, h);
    }
    public static float getCalculatedDimension(float num, float total)
    {
        int div = (int)(total/num);
        if(div * num != total)
        {
            float diff = (total - (div*num));
            num+=(diff/div);
            return num;
        }
        else
        {
            return num;
        }
    }
}
