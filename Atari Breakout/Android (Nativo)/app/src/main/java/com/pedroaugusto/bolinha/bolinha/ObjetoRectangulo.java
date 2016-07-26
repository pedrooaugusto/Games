package com.pedroaugusto.bolinha.bolinha;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.pedroaugusto.bolinha.bolinha.Point;
/**
 * Created by Pedro on 06/06/2016.
 */
public abstract class ObjetoRectangulo {
    private String nome;
    private Point dimensao;
    private Point posisao;
    private float velocidadeY, velocidadeX;
    private String cor;

    public abstract void draw(Canvas canvas, Paint p);

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getVelocidadeX() {
        return velocidadeX;
    }

    public void setVelocidadeX(float velocidade) {
        this.velocidadeX = velocidade;
    }

    public float getVelocidadeY() {
        return velocidadeY;
    }

    public void setVelocidadeY(float velocidade) {
        this.velocidadeY = velocidade;
    }

    public Point getDimensao() {
        return dimensao;
    }

    public void setDimensao(float width, float height) {
        this.dimensao = new Point(width, height);
    }

    public Point getPosisao() {
        return posisao;
    }

    public void setPosisao(float x, float y) {
        this.posisao = new Point(x, y);
    }

    public void setX(float x) {
        this.posisao = new Point(x, posisao.y);
    }

    public void setY(float y) {
        this.posisao = new Point(posisao.x, y);
    }

    public float getX() {
        return posisao.x;
    }

    public float getY() {
        return posisao.y;
    }

    public float getWidth() {
        return dimensao.x;
    }

    public float getHeight() {
        return dimensao.y;
    }

    @Override
    public String toString() {
        return "ObjetoRectangulo{" +
                "nome='" + nome + '\'' +
                ", dimensao=" + dimensao.y +
                ", posisao=" + posisao.x +
                ", velocidadeY=" + velocidadeY +
                ", velocidadeX=" + velocidadeX +
                ", cor='" + cor + '\'' +
                '}';
    }
}
