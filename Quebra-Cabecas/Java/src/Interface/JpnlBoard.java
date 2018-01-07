/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import CardImagem.ImageCard;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 *
 * @author Pedro
 */
public class JpnlBoard extends JPanel{
    private Board board;
    private int espaco = 1;
    public JpnlBoard(int widthParent, int heightParent)
    {
        super();
        this.board = new Board(5, 5, widthParent, heightParent);
        setLayout(null);
        setBackground(Color.BLACK);
        addImagens();
    }
    public JpnlBoard()
    {
        this(376, 276);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(espaco != 0)
        {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(espaco));
            for (int i = 0; i < board.getColunas() + 1; ++i) {
                g2.draw(new Line2D.Float(0, i * (board.getSizePeca().height),
                        board.getWidthTotal(), i * (board.getSizePeca().height)));
            }
            for (int i = 0; i < board.getLinhas() + 1; ++i) {
                g2.draw(new Line2D.Float(i * (board.getSizePeca().width),
                        0, i * (board.getSizePeca().width), board.getHeightTotal()));
            }
        }
    }
    private void addImagens()
    {
        for (ImageCard img : board.getListaDePecas())
            add(img);
    }    
    public void solve()
    {
        espaco = 0;
        board.solve();
        removeAll();
        repaint();
        addImagens();
        Timer timer = new Timer(120, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                espaco = 1;
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    public void reset()
    {
        board.reset();
        removeAll();
        repaint();
        addImagens();
    }
    public void hardReset()
    {
        board.hardReset();
        removeAll();
        repaint();
        addImagens();
    }
    public void undo()
    {
        board.undo();
    }
    public Board getBoard()
    {
        return board;
    }
    public void setImg(String a)
    {
        board.setImgPath(a);
    }
}
