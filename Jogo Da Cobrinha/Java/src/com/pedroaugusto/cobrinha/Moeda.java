/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pedroaugusto.cobrinha;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 *
 * @author Pedro
 */
public class Moeda extends ObjetoRectangulo{
    private float raio = GameView.LARGURA_TELA*0.012f;
    private float sinDivide = 30;
    private float cosDivide = 30;
    private float consta = 0;
    private float amplitude = 40;
    private float originalX;
    private float originalY;
    private float top = 15;
    public Moeda() {
        setNome("Moeda");
        setCor(new Color(243, 221, 30));
        setPosisao(GameView.LARGURA_TELA*0.5f, GameView.ALTURA_TELA*0.5f);
        setDimensao(raio*2, raio*2);
        this.originalX = getX();
        this.originalY = getY();
    }
    public void generate(float velCobra)
    {
        float c = velCobra;
        Random r = new Random();
        float novox = r.nextInt((int)(GameView.LARGURA_TELA - c)) + c;
        float novoy = r.nextInt((int)(GameView.ALTURA_TELA - c)) + c;
        setX(novox);
        setY(novoy);
	this.originalX = getX();
	this.originalY = getY();
    }
    public void generate()
    {
        generate((GameView.LARGURA_TELA*0.035f));
    }
    private float[] track(float x, float y, float top, float ampl)
    {
        float topn = top + this.top;
       	float xn = (float) (x + ampl * Math.sin(topn / sinDivide));
       	float yn = (float)((topn / GameView.ALTURA_TELA < 0.65) ? y + 10 : 
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
    @Override
    public void draw(Graphics2D canvas) {
        drawBorder(canvas, new Color(209, 208, 210), 0.8f);
        canvas.setColor(getCor());
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double circle = new Ellipse2D.Double(getX(), getY(), raio*2, raio*2);
        canvas.fill(circle);
    }
    
}
