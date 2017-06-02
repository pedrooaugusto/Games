package com.pedroaugusto.bolinha.bolinha;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Pedro on 06/06/2016.
 */
public class Barra extends ObjetoRectangulo {
    private final float decreaseMovementTax = 0.7f;
    private float aceleration;
    public float velocidadeLimite;
    private int vidas = 0;
    public Barra ()
    {
        setNome("Barra");
        setCor("#1C1CC5");
        setVidas(0);
        setDimensao((GameView.LARGURA_TELA*0.2f), (GameView.ALTURA_TELA*0.03f));
        setPosisao(0, (GameView.ALTURA_TELA*0.7f));
        setVelocidadeX(0);
        setVelocidadeLimite((getWidth()/5));
        setAceleration((getWidth()/8));
    }
    @Override
    public void draw(final Canvas ctx, Paint p)
    {
        Retangulo barra = new Retangulo(getX(), getY(),
                getWidth()/*0.35f*/, getHeight()).toRect();

        Retangulo barra1 = new Retangulo(getX() + getWidth()*0.35f,
                getY(),
                getWidth()*0.3f, getHeight()).toRect();

        Retangulo barra2 = new Retangulo(getX() + getWidth()*0.65f,
                getY(),
                getWidth()*0.35f, getHeight()).toRect();

        p.setColor(new Color().parseColor(getCor()));
            ctx.drawRect(barra.x, barra.y, barra.w, barra.h, p);
        /*p.setColor(Color.WHITE);
        ctx.drawRect(barra1.x, barra1.y, barra1.w, barra1.h, p);
        p.setColor(Color.GREEN);
            ctx.drawRect(barra2.x, barra2.y, barra2.w, barra2.h, p);*/
        p.setColor(Color.RED);
        p.setMaskFilter(new BlurMaskFilter(3, BlurMaskFilter.Blur.NORMAL));
        for(int i = 0; i < getVidas(); i++)
        {
            ctx.drawCircle(GameView.LARGURA_TELA*0.95f,
                    (GameView.ALTURA_TELA*0.4f)+(GameView.ALTURA_TELA*0.04f*(i+1))
                            +(i*GameView.ALTURA_TELA*0.03f),
                    GameView.ALTURA_TELA*0.025f, p);
        }
        p.setMaskFilter(null);
    }

    public float getAceleration() {
        return aceleration;
    }

    public void setAceleration(float aceleration) {
        this.aceleration = aceleration;
    }

    public void move()
    {
        setX((getX()+getVelocidadeX()));
    }


    public void perderVida()
    {
        this.vidas-=1;
    }
    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void atualizarVelocidade(boolean botoes[])
    {
        if (botoes[1])
        {
            if (getVelocidadeX() < getVelocidadeLimite())
            {
                setVelocidadeX(getVelocidadeX() + aceleration);
            }
            //setVelocidadeX(15);
        }
        if (botoes[0])
        {
            //setVelocidadeX(-15);
            if (getVelocidadeX() > -getVelocidadeLimite())
            {
                setVelocidadeX(getVelocidadeX() - aceleration);
            }
        }
        setVelocidadeX(getVelocidadeX()*decreaseMovementTax);
    }
    public void setVelocidadeLimite(float v)
    {
        this.velocidadeLimite = v;
    }

    public float getVelocidadeLimite()
    {
        return velocidadeLimite;
    }
}
