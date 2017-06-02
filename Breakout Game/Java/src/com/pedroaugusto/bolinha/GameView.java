/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pedroaugusto.bolinha;

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
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Pedro
 */
public class GameView extends JPanel{
    public final static int ALTURA_TELA = 520;
    public final static int LARGURA_TELA = 979;
    public static boolean ENABLED_SOM = true;
    public static int TYPE_SOUND = 0;
    public static int TYPE_OBS = 0;
    int x = 0, y = 0;
    Bola bola = new Bola();
    ControleObstaculo controleObstaculo = new ControleObstaculo();
    Barra barra = new Barra();
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    Graphics2D big;
    boolean firstTime = true;
    Colisao colisao = new Colisao();
    private Timer timer;
    boolean botoes[] = {false, false};
    public GameView() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                barra.moveTP(e.getX());
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(barra.getVidas() == -1 || controleObstaculo.getList().isEmpty())
                {
                    int x = (int)(GameView.LARGURA_TELA*0.5 - GameView.LARGURA_TELA*0.1);
                    int y = (int)(GameView.ALTURA_TELA*0.5 - GameView.ALTURA_TELA*0.04);
                    int w = (int)(GameView.LARGURA_TELA*0.2);
                    int h = (int)(GameView.ALTURA_TELA*0.08);
                    if((e.getX() > x && e.getX() < x + w) && (e.getY() > y && e.getY() < y + h))
                    {
                        barra.setVidas(4);
                        controleObstaculo.create(firstTime);
                        bola.setRecemNascido(true);
                        bola.setX(GameView.LARGURA_TELA*0.5f);
                        bola.setY(GameView.ALTURA_TELA*0.6f);
                    }
                }
            }
        });
        controleObstaculo.create(firstTime);
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bola.move();
                repaint();
            }
        });
        timer.setDelay(30);
        timer.setRepeats(true);
        timer.start();
    }
    @Override
    public void paint(Graphics g) {
        update(g);
    }
    
    @Override
    public void update(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(barra.getVidas() != -1 && !controleObstaculo.getList().isEmpty())
        {
            loop(g2d);
        }
        else
        {
            if(barra.getVidas() == -1)
                stopGame("GAME OVER", g2d);
            else
                stopGame("YOU WIN!!", g2d);
        }
    }
    private void drawInfo(Graphics2D g)
    {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, 18));
        g.drawString("Tecla p para pausar.   Tecla r para resume", 100, 20);
    }
    public void pause()
    {
        timer.stop();
    }
    public void resume()
    {
        timer.start();
    }
    public void loop(Graphics2D g2d)
    {
        checkFirstTime();
        limpaTela();
        controleObstaculo.draw(big);
        barra.draw(big);
        barra.atualizarVelocidade(botoes);
        barra.move();
        drawInfo(big);
        colisao.algumaColisao(bola, barra, controleObstaculo.getList());
        bola.draw(big);
        g2d.drawImage(bi, 0, 0, this);
        g2d.dispose();
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
    
    public void stopGame(String message, Graphics2D g2d)
    {
        limpaTela();
        big.setColor(Color.BLACK);
        big.fillRect(0, 0, GameView.LARGURA_TELA, GameView.ALTURA_TELA);
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
        
        g2d.drawImage(bi, 0, 0, this);
    
        g2d.dispose();
    }
    
    public void moveButton(int key, boolean release)
    {
        if(release)
        {
            if(key == 39)
            {
                botoes[1] = false;
            }
            else if(key == 37)
            {
                botoes[0] = false; 
            }
        }    
        else
        {
            if(key == 39)
            {
                botoes[0] = false;
                botoes[1] = true;
            }
            else if(key == 37)
            {
                botoes[1] = false;
                botoes[0] = true;
            }
        }
    }
            
    public void limpaTela()
    {
        big.clearRect(0, 0, getSize().width, getSize().height);
        big.setColor(Color.BLACK);
        big.fillRect(0, 0, getSize().width, getSize().height);
    }
}
