/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pedroaugusto.cobrinha;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Pedro
 */
public class GameView extends JPanel{
    public final static int ALTURA_TELA = 500;
    public final static int LARGURA_TELA = 975;
    private BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
    private Graphics2D big;
    boolean firstTime = true;
    private Timer timer;
    private Cobra cobra = new Cobra();
    private Moeda moeda = new Moeda();
    private Colisao colisao = new Colisao(this);
    private TempoMoeda tempo;
    private Map<Integer, Boolean> keys = new HashMap<>(4);
    public GameView() {
        keys.put(39, false);
        keys.put(37, false);
        keys.put(38, false);
        keys.put(40, false);
        setOpaque(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!timer.isRunning())
                {
                    int x = (int)(GameView.LARGURA_TELA*0.5 - GameView.LARGURA_TELA*0.1);
                    int y = (int)(GameView.ALTURA_TELA*0.5 - GameView.ALTURA_TELA*0.04);
                    int w = (int)(GameView.LARGURA_TELA*0.2);
                    int h = (int)(GameView.ALTURA_TELA*0.08);
                    if((e.getX() > x && e.getX() < x + w) && (e.getY() > y && e.getY() < y + h))
                    {
                        resumeGame();
                        limpaTela(); 
                        keys.put(39, false);
                        keys.put(37, false);
                        keys.put(38, false);
                        keys.put(40, false);
                        cobra.initCobra();
                        tempo.restart();
                    }

                }
            }
        });
        timer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cobra.move();
                moeda.move();
                repaint();
            }
        });
        timer.setDelay(1000/8);
        timer.setRepeats(true);
        timer.start();
        tempo = new TempoMoeda(moeda, cobra);
        tempo.start();
        startGame();
    }
    public void startGame()
    {
        cobra.initCobra();
    }
    public void resumeGame()
    {
        timer.start();
    }
    public void pause(boolean finish)
    {
        timer.stop();
        if(finish)
            stopGame("GAME OVER", big);
    }
    
    public void loop(Graphics2D g2d)
    {
            /*g2d.setBackground(new Color(46, 25, 97, 0));
            g2d.setColor(new Color(46, 25, 97, 255));
            g2d.fillRect(0, 0, getSize().width, getSize().height);
            g2d.clearRect(5, 5, getSize().width-10, getSize().height-10);
            colisao.algumaColisao(cobra, moeda, tempo);
            drawInfo(g2d);
            cobra.draw(g2d);
            moeda.draw(g2d);*/
        /*
            Para dar um efeito legal de full transparencia
            Tire o bloco acima do comentário,
            e ponha o bloco abaixo em comentário
            Ponha a tela principal com undecorated = true;
            E na segunda linha do construtor da tela Main
            tire o que estiver do //
        */
            checkFirstTime();
            limpaTela();
            colisao.algumaColisao(cobra, moeda, tempo);
            drawInfo(big);
            cobra.draw(big);
            moeda.draw(big);
            g2d.drawImage(bi, 0, 0, this);
            g2d.dispose();
            
    }
    @Override
    public void paint(Graphics g) {
        update(g);
    }
    
    @Override
    public void update(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        loop(g2d);
    }
    public void movimentoSetaUp(int key)
    {
       	switch(key)
	{
            case 37: case 38: case 39: case 40:
            keys.put(key, false);
            break;	
	}
    }
    public void movimentoSetaDown(int key)
    {
        if(!(keys.get(37) || keys.get(38) || keys.get(39) || keys.get(40)))
	{
            if(keys.containsKey(key))
            {
            	cobra.choose(key);
                keys.put(key, true);
            }
        }	
    }
    public void checkFirstTime()
    {
        if (firstTime) {
            Dimension dim = getSize();
            int w = dim.width;
            int h = dim.height;
            bi = (BufferedImage) createImage(w, h);
            big = bi.createGraphics();
            big.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            big.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            firstTime = false;
        } 
    }
    private void drawInfo(Graphics2D big)
    {
        big.setColor(Color.BLACK);
        big.fillRect(0, 0, LARGURA_TELA, (int)(ALTURA_TELA*0.12f));
        big.setFont(new Font("Segoe UI Light", Font.PLAIN, 23));
        int tam = big.getFontMetrics().stringWidth("Pontos:  "+cobra.getPartes().size()+"  Left 30s to lose 5 units");
        int f = big.getFontMetrics().getHeight();
        big.setColor(Color.WHITE);
        big.drawString("Pontos:  "+cobra.getPartes().size()+"  Left "+tempo.getInfo()+"s to lose "+cobra.getPerdeSeMorrer()+
                " units", (int)(LARGURA_TELA*0.5 - tam*0.5), 
                (int)(ALTURA_TELA*0.07));
            big.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            big.setColor(Color.WHITE);
            big.drawString("Use as setas para mover. p == pause, r == resume", 10, ALTURA_TELA*0.98f);
            big.drawString("Acesse: pedrooaugusto.github.com", LARGURA_TELA*0.75f, ALTURA_TELA*0.98f);
            
    }
    public void stopGame(String message, Graphics2D g2d)
    {
        big.setColor(new Color(21, 33, 121));
        big.fillRect((int)(GameView.LARGURA_TELA*0.5 - GameView.LARGURA_TELA*0.1),
                (int)(GameView.ALTURA_TELA*0.5 - GameView.ALTURA_TELA*0.04), 
                (int)(GameView.LARGURA_TELA*0.2),
                (int)(GameView.ALTURA_TELA*0.08));
        big.setColor(Color.white);
        big.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        int tam = big.getFontMetrics().stringWidth("RETRY");
        
        big.drawString("RETRY", (int)(GameView.LARGURA_TELA*0.5 - tam*0.5),
                (int)(GameView.ALTURA_TELA*0.5 + 10));
        
        tam = big.getFontMetrics().stringWidth(message);
        big.drawString(message, (int)(GameView.LARGURA_TELA*0.5 - tam*0.5),
                (int)(GameView.ALTURA_TELA*0.5 - GameView.ALTURA_TELA*0.1));
        big.setColor(Color.LIGHT_GRAY);
        big.drawString(message, (int)(GameView.LARGURA_TELA*0.5 - tam*0.5)+2,
                (int)(GameView.ALTURA_TELA*0.5 - GameView.ALTURA_TELA*0.1)+2);
        
    }
    public void limpaTela()
    {
        big.clearRect(0, 0, getSize().width, getSize().height);
        big.setColor(new Color(46, 25, 97));
        big.fillRect(0, 0, getSize().width, getSize().height);
    }
    
}
