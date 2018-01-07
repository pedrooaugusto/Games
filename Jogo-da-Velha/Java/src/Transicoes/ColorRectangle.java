package Transicoes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class ColorRectangle extends Rectangle{
    private Color cor;
    
    public ColorRectangle(Point location, Dimension size, Color cor)
    {
        super(location, size);
        this.cor = cor;
    }
    public void setCor(Color cor)
    {
        this.cor = cor;
    }
    public Color getCor()
    {
        return cor;
    }
}
