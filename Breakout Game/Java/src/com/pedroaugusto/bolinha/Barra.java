package com.pedroaugusto.bolinha;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
/**
 * Created by Pedro on 06/06/2016.
 */
public class Barra extends ObjetoRectangulo {
    private final float decreaseMovementTax = 0.7f;
    private float aceleration;
    public float velocidadeLimite;
    private int vidas = 4;
    public Barra ()
    {
        setNome("Barra");
        setCor(new Color(28, 28, 197));
        setVidas(4);
        setDimensao((GameView.LARGURA_TELA*0.22f), (GameView.ALTURA_TELA*0.028f));
        setPosisao(0, (GameView.ALTURA_TELA*0.9f));
        setVelocidadeX(0);
        setVelocidadeLimite((getWidth()/5));
        setAceleration((getWidth()/10));
    }
    @Override
    public void draw(final Graphics2D ctx)
    {
        ctx.setColor(getCor());
        RoundRectangle2D.Double ff = new RoundRectangle2D.Double(getX(), getY(), getWidth(), 
                getHeight(), 10, 10);
        ctx.fill(ff);
        ctx.setColor(Color.RED);
        for(int i = 0; i < getVidas(); i++)
        {
            Ellipse2D.Double circle = new Ellipse2D.Double((GameView.LARGURA_TELA*0.01+
                    (GameView.LARGURA_TELA*0.02*i)), GameView.ALTURA_TELA*0.01, 
                    GameView.ALTURA_TELA*0.03f, GameView.ALTURA_TELA*0.03f);
            ctx.fill(circle);
        }
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
    public void moveTP(int x)
    {
        setX(x-getWidth()/2);
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
