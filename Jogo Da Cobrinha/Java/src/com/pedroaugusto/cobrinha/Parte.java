/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pedroaugusto.cobrinha;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Pedro
 */
public class Parte extends ObjetoRectangulo{

    private boolean enabled = true;
    
    public Parte() {
        setNome("Parte");
        setCor(Color.WHITE);
        setDimensao((GameView.LARGURA_TELA*0.015f), 
                (GameView.LARGURA_TELA*0.015f));
        setPosisao(-45, -45);
    }
    @Override
    public void draw(Graphics2D canvas) {
        setCor(prestesMorrer());
        //drawBorder(canvas, Color.DARK_GRAY, 3);
        drawBorder(canvas, new Color(209, 208, 210), 0.8f);
        canvas.setColor(getCor());
        canvas.fill(new Rectangle2D.Float((int)getX(), (int)getY(), 
                (int)getWidth(), (int)getHeight()));
    }
    private Color prestesMorrer()
    {
        if(this.enabled)
            return Color.WHITE;
	else
            if(getCor() == Color.WHITE)
		return new Color(46, 25, 97);
            else
		return Color.WHITE;
    }
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
}
