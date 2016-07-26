/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CardImagem;

import Interface.JpnlBoard;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Pedro
 */
public class ImageCard extends JLabel implements MouseListener, MouseMotionListener
{
    private boolean isDrag = false;
    private final Timer timer;
    private Card card;
    private final Imagem imagem;
    private int currentImg;
    public ImageCard(ImageIcon img, Rectangle inicial, int id) {
        this.timer = new Timer(170, (ActionEvent e) -> {
            if (isDrag) {
                getParent().setComponentZOrder(this, 0);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        changeImg(2);
                    }
                });
                setBorder(BorderFactory.createLineBorder(Color.RED, 5, false));
                setSize((int)(card.getWidth()*0.7), (int)(card.getHeight()*0.7));
                getParent().repaint();
            }
        });
        timer.setRepeats(false);
        card = new Card(id, inicial);
        imagem = new Imagem(img);
        this.currentImg = 1;
        changeImg(1);
        setBounds(inicial);
        setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    private void changeImg(int which)
    {
        ImageIcon i = null;
        switch(which)
        {
            case 1:
                i = imagem.getImagemOriginal();
                break;
            case 2:
                i = imagem.getImagemPequena();
                break;
            case 3:
                i = imagem.getImagemSombreada();
                break;
            default:
                i = null;
        }
        setIcon(i);
    }
    private int getCurrentImg()
    {
        return currentImg;
    }
    public Card getCard()
    {
        return card;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        isDrag = true;
        atual = this;
        timer.start();
    }    
    ImageCard atual;
    @Override
    public void mouseReleased(MouseEvent e) {
        isDrag = false;
        Rectangle area = getBounds();
        ImageCard objeto2 = ((JpnlBoard)getParent()).getBoard().pesquisa(area);
        if(objeto2 != null && !objeto2.equals(this))
            ((JpnlBoard)getParent()).getBoard().changePosition(card.getId(), 
                    objeto2.card.getId(), false);
        else
            setBounds(card.getInicial());
        setBorder(null);
        atual.changeImg(1);
        changeImg(1);
        setSize((int)(card.getWidth()*1), (int)(card.getHeight()*1));
    }

    @Override
    public void mouseDragged(MouseEvent evt) 
    {
        if(isDrag)
        {
            setLocation(getLocation().x + evt.getX() - (int) (card.getWidth() * 0.5),
                    getLocation().y + evt.getY() - (int) (card.getHeight() * 0.5));
            Rectangle area = getBounds();
            ImageCard objeto2 = ((JpnlBoard)getParent()).getBoard().pesquisa(area);
            if(objeto2 != null && !objeto2.equals(atual) && !objeto2.equals(this))
            {
                if(atual == this)
                    atual.changeImg(2);
                else
                    atual.changeImg(1);
                atual = objeto2;
            }
            else if(objeto2 != null && !objeto2.equals(this))
                atual.changeImg(3);
        }
    }    
    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
