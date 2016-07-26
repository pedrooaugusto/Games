package com.pedroaugusto.bolinha.bolinha;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.provider.Settings;

/**
 * Created by Pedro on 06/06/2016.
 */
public class Bola extends ObjetoRectangulo {

    private final int raio = 15;
    private final int maxVel = 40;
    private final int restituicao = -1;
    private boolean recemNascido = true;
    private Point velocidadeRecemNascido;
    private Point velocidadeNormal;
    private long horarioNascimento = 0;
    private final RadialGradient r =new RadialGradient(8f, 80f, 90f, Color.RED, Color.WHITE, Shader.TileMode.MIRROR);
    public Bola()
    {
        setNome("Bola");
        setCor("white");
        setDimensao((GameView.LARGURA_TELA*0.025f),(GameView.LARGURA_TELA*0.025f));
        setPosisao((GameView.LARGURA_TELA*0.1f), (GameView.ALTURA_TELA*0.35f));
        //Velocidade perfeita pra low lvl : x and y = /2, /3
        velocidadeNormal =  new Point(getHeight()/1.5f, getHeight()/1.8f);
        setVelocidadeX(0);
        setVelocidadeY((getHeight()/1.8f));
        velocidadeRecemNascido =  new Point(getHeight()/2.8f, getHeight()/2.5f);
        setRecemNascido(true);
    }
    @Override
    public void draw(Canvas canvas, Paint p) {
        //p.setShadowLayer(30, -2, -2, Color.LTGRAY);
        //p.setMaskFilter(new BlurMaskFilter(2, BlurMaskFilter.Blur.NORMAL));
        //p.setShader(new LinearGradient(80, 80, 40, 40, Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));
        //p.setShader(r);
        p.setColor(new Color().parseColor(getCor()));
        canvas.drawCircle(getX()+(getWidth()/2), getY()+(getWidth()/2), raio, p);
        //p.setShader(null);
        //p.setMaskFilter(null);
        //p.setShadowLayer(0, 0, 0, Color.TRANSPARENT);

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
