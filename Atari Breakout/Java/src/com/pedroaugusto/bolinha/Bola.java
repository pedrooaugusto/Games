package com.pedroaugusto.bolinha;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

/**
 * Created by Pedro on 06/06/2016.
 */
public class Bola extends ObjetoRectangulo {

    private final int raio = 12;
    private final int maxVel = 40;
    private final int restituicao = -1;
    private boolean recemNascido = true;
    private Point velocidadeRecemNascido;
    private Point velocidadeNormal;
    private long horarioNascimento = 0;
    public Bola()
    {
        setNome("Bola");
        setCor(new Color(245, 240, 244));
        setDimensao(24, 24);
        setPosisao(GameView.LARGURA_TELA*0.5f, GameView.ALTURA_TELA*0.6f);
        //Velocidade perfeita pra low lvl : x and y = /2, /3
        velocidadeNormal =  new Point(getHeight()/2f, getHeight()/2f);
        setVelocidadeX(0);
        setVelocidadeY((getHeight()/3f));
        velocidadeRecemNascido =  new Point(getHeight()/2.8f, getHeight()/3f);
        setRecemNascido(true);
    }
    @Override
    public void draw(Graphics2D canvas) {
        canvas.setColor(getCor());
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double circle = new Ellipse2D.Double(getX(), getY(), raio*2, raio*2);
        canvas.fill(circle);
    }
    public int getRaio()
    {
        return raio;
    }

    public boolean isRecemNascido() {
        return recemNascido;
    }

    public void setRecemNascido(boolean recemNascido) {
        if(recemNascido)
            horarioNascimento = System.currentTimeMillis();
        this.recemNascido = recemNascido;
    }

    public int getRestituicao() {
        return restituicao;
    }

    private void checarVelocidade()
    //Checa se a bola acabou de morrer. E faz as alteraçõeas necessárias.
    {
        int i = (getVelocidadeX() > 0 ? 1 : -1);
        int j = (getVelocidadeY() > 0 ? 1 : -1);
        i = (getVelocidadeX() == 0 ? 0 : i);
        long now = System.currentTimeMillis();
        if(now - horarioNascimento > 5000)
        {
            setVelocidadeX(velocidadeNormal.x * i);
            setVelocidadeY(velocidadeNormal.y * j);
        }
        else
        {
            setVelocidadeX(velocidadeRecemNascido.x*i);
            setVelocidadeY(velocidadeRecemNascido.y*j);
        }
    }
    public void move() {
        checarVelocidade();
        float x, y;
        x = getX() + getVelocidadeX();
        y = getY() + getVelocidadeY();
        setX(x);
        setY(y);
    }
}
