package com.pedroaugusto.cobrinha;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Pedro on 18/07/2016.
 */
public class Moeda extends ObjetoRectangulo
{
    private float raio = GameView.LARGURA_TELA*0.025f;
    private float sinDivide = 30;
    private float cosDivide = 30;
    private float consta = 0;
    private float amplitude = 40;
    private float originalX;
    private float originalY;
    private float top = 18;
    public Moeda()
    {
        setNome("Moeda");
        setCor(new Color().rgb(243, 221, 30));
        setPosisao(GameView.LARGURA_TELA*0.5f, GameView.ALTURA_TELA*0.5f);
        setDimensao(raio*2, raio*2);
        this.originalX = getX();
        this.originalY = getY();
    }
    @Override
    public void draw(Canvas canvas, Paint p) {
        p.setShadowLayer(10, 0, 0, Color.YELLOW);
        p.setColor((getCor()));
        canvas.drawCircle(getX()+(getWidth()/2), getY()+(getWidth()/2), raio, p);
        p.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
    }
    public void generate(float velCobra)
    {
        //POR FAVOR NOTE O generate() que chama genarate(float f)
        float c = velCobra;
        Random r = new Random();
        float novox = r.nextInt((int)(GameView.LARGURA_TELA - c)) + c;
        float novoy = (int)(GameView.ALTURA_TELA*0.01) + r.nextInt((int)(GameView.ALTURA_TELA*0.6f - c)) + c;
        setX(novox);
        setY(novoy);
        this.originalX = getX();
        this.originalY = getY();
    }
    public void generate()
    {
        generate((GameView.LARGURA_TELA*0.065f));
    }
    private float[] track(float x, float y, float top, float ampl)
    {
        float topn = top + this.top;
        float xn = (float) (x + ampl * Math.sin(topn / sinDivide));
        float yn = (float)(((topn / GameView.ALTURA_TELA) < 0.15) ? y + 10 :
                1 + y + ampl * Math.cos(topn / cosDivide));
        if (xn < raio*2)
            xn = raio*2;
        if (xn > GameView.LARGURA_TELA - raio*2)
            xn = GameView.LARGURA_TELA - raio*2;
        if (yn < raio*2)
            yn = raio*2;
        if (yn > GameView.ALTURA_TELA - raio*2)
            yn = GameView.ALTURA_TELA - raio*2;
        return new float[]{xn, topn, yn};
    }
    public void move()
    {
        float coisas[] = track(originalX, originalY, consta, amplitude);
        setX(coisas[0]);
        setY(coisas[2]);
        consta = coisas[1];
    }

}
