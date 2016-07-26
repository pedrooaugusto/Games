package com.pedroaugusto.bolinha;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Created by Pedro on 06/06/2016.
 */
public class Obstaculo extends ObjetoRectangulo {
    public static float height = GameView.ALTURA_TELA * 0.03f;
    public static float width = GameView.LARGURA_TELA * 0.15f;
    public Obstaculo(int x, int y, int... rgb)
    {
        setDimensao((int)dimensions().x, dimensions().y);
        setPosisao(x, y);
        setCor(new Color(rgb[0], rgb[1], rgb[2]));
    }
    @Override
    public void draw(Graphics2D canvas)
    {
        canvas.setColor(getCor());
        canvas.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
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
