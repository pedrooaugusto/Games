package com.pedroaugusto.bolinha;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Created by Pedro on 06/06/2016.
 */
public abstract class ObjetoRectangulo {
    private String nome;
    private Point dimensao;
    private Point posisao;
    private float velocidadeY, velocidadeX;
    private Color cor;

    public abstract void draw(Graphics2D canvas);

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
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
