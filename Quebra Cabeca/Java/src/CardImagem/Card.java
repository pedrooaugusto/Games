/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CardImagem;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Pedro
 */
public class Card {
    private int id;
    private int width;
    private int height;
    private Rectangle inicial;
    public Card(int id, Rectangle inicial)
    {
        this.id = id;
        this.inicial = inicial;
        this.height = inicial.getSize().height;
        this.width = inicial.getSize().width;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getInicial() {
        return inicial;
    }
    
    public Point getPointInicial()
    {
        return inicial.getLocation();
    }
    
    public Dimension getSizeInicial()
    {
        return inicial.getSize();
    }
    public void setInicial(Rectangle inicial) {
        this.inicial = inicial;
    }
    public void setPointInicial(Point p)
    {
        this.inicial.setLocation(p);
    }
    
}
