/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Card;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Pedro
 */
public class Card extends JLabel
{
    final int ID;
    final Icon padrao;
    final Icon real;
    boolean virado = false;
    public Card(int ID, Icon padrao, Icon real)
    {
        this.ID = ID;
        this.padrao = padrao;
        this.real = real;
        this.setIcon(padrao);
        this.setOpaque(true);
        offFocus();
        this.setBackground(new Color(51, 51, 51));
        this.setHorizontalAlignment(JLabel.CENTER);
    }
    private void loseShine()
    {
       this.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(51, 51, 51), 3), 
                        new javax.swing.border.LineBorder(
                                new java.awt.Color(255, 255, 255), 1, false)));
    }
    private void onFocus()
    {
        this.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(51, 51, 51), 3),
                new javax.swing.border.LineBorder(
                        Color.RED, 2, true)));
    }
    private void offFocus()
    {
        this.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(51, 51, 51), 3), 
                        new javax.swing.border.LineBorder(
                                new Color(106,90,205), 2, true)));
    }
    public int getID()
    {
        return ID;
    }
    public void imagemPadrao(boolean haveFocus)
    {
       this.setIcon(padrao);
       if(haveFocus){offFocus();}
    }
    public void imagemReal(boolean haveFocus)
    {
        this.setIcon(real);
        if(haveFocus){onFocus();}
    }
    public Icon getImagemReal()
    {
        return real;
    }
    public Icon getImagemPadrao()
    {
        return padrao;
    }
    public void setVirado(boolean virado)
    {
        this.virado = virado;
        loseShine();
    }
    public boolean isVirado()
    {
        return virado;
    }

    @Override
    public void setEnabled(final boolean enabled)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Card.super.setEnabled(enabled);
            }
        });
    }
    
}
