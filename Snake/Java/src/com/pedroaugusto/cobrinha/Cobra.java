/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pedroaugusto.cobrinha;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author Pedro
 */
public class Cobra extends ObjetoRectangulo{
    private int perdeSeMorrer = -1;
    private List<Parte> partes = new ArrayList<>();
    private float velPadrao;
    private final Turn turn = new Turn();
    public Cobra() 
    {
        setNome("Cobra");
        setCor(new Color(197, 18, 18));
        setDimensao((GameView.LARGURA_TELA*0.035f), (GameView.LARGURA_TELA*0.035f));
        setPosisao(50, (GameView.ALTURA_TELA*0.5f));
        setVelocidadeX(getWidth());
        setVelocidadeY(0);
        velPadrao = getWidth();
    }

    public List<Parte> getPartes() {
        return partes;
    }
    public void choose(int key)
    {
        turn.choose(key);
    }
    public void addPartes(Parte... partes) {
        this.partes.addAll(Arrays.asList(partes));
    }
    public void initCobra()//Add 3 partes a cobra
    {
        setPosisao(50, (GameView.ALTURA_TELA*0.5f));
        setVelocidadeX(getWidth());
        setVelocidadeY(0);
        partes.clear();
        Parte p1 = new Parte();
        p1.setPosisao(30, 30);
        Parte p2 = new Parte();
        p2.setPosisao(60, 30);
        Parte p3 = new Parte();
        p3.setPosisao(60, 30);
        addPartes(p1, p2, p3);
        attPerdeSeMorrer();
    }
    @Override
    public void draw(Graphics2D canvas) 
    {
        drawBorder(canvas, new Color(209, 208, 210), 1f);
        canvas.setColor(getCor());
        canvas.fillRect((int)getX(), (int)getY(), 
            (int)getWidth(), (int)getHeight());
        for (Parte parte : partes)
            parte.draw(canvas);
    }
    
    public void killSomeChilds()
    {
        int tam = partes.size() - 1;
        tam = (tam < 0 ? 0 : tam);
        for(int i = 0; i < perdeSeMorrer; i++)
            partes.remove(tam - i);
        for (Parte parte : partes) {
            parte.setCor(Color.WHITE);
            parte.setEnabled(true);
        }
        attPerdeSeMorrer();
    }
    
    private void movimentoContinuo()
    {
        setX(getX() + getVelocidadeX());
        setY(getY() + getVelocidadeY());
    }
    public void move()
    {
        movimentoContinuo();
        movimentarFilhos();
    }
    private void movimentarFilhos()
    {
        float auX = getX() + (getWidth()/2) - partes.get(0).getWidth()/2;
	float auY = getY() + (getHeight()/2) - partes.get(0).getHeight()/2;
        float ggX, ggY;
	for (int i = 0; i < this.partes.size(); i++) 
	{
            ggX = partes.get(i).getX();
            ggY = partes.get(i).getY();
            partes.get(i).setX(auX);
            partes.get(i).setY(auY);
            auX = ggX;
            auY = ggY;
	}
    }
    public void startAnimation()
    {
        for (int i = partes.size() - 1; i >= partes.size() - perdeSeMorrer; i--)
            partes.get(i).setEnabled(false);
        Timer t = new Timer(2000, (e) -> {killSomeChilds();});
        t.setRepeats(false);
        t.start();
    }
    public int getPerdeSeMorrer() {
        return perdeSeMorrer;
    }
    
    public void attPerdeSeMorrer()
    {
        perdeSeMorrer = (int)Math.floor((partes.size())/2);
    }
    private class Turn
    {
        public void choose(int where)
        {
            float vxy[] = {-1, -1};
            if(where == 38)
		vxy = top();
            if(where == 39)
		vxy = right();
            if(where == 40)
		vxy = bottom();
            if(where == 37)
		vxy = left();
            if(vxy != null && vxy[0] != -1){
                setVelocidadeX(vxy[0]);
                setVelocidadeY(vxy[1]);
            }
        }
        
        private float[] left()
        {
            if(!(getVelocidadeX() > 0))
		return new float[]{velPadrao*(-1), 0};
            else
                return null;
        }
        private float[] right()
        {
            if(!(getVelocidadeX() < 0))
		return new float[]{velPadrao, 0};
            else
                return null;
        }
        private float[] top()
        {
            if(!(getVelocidadeY() > 0))
		return new float[]{0, velPadrao*(-1)};
            else
                return null;
        }
        private float[] bottom()
        {
            if(!(getVelocidadeY() < 0))
		return new float[]{0, velPadrao};
            else
                return null;
        }
    }
}
