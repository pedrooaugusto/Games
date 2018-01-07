/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Card.Card;
import Card.CardEstatico;
import Embaralhar.Embaralhar;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author Pedro
 */
public class Quadro extends JPanel
{
    private Card primeCard, segunCard;
    private boolean clickUm = true;
    private int linha, coluna, cardsRestantes, numeroClicks = 0;
    private boolean ninguemPodeClicarEmNada = false;
    private int pontos;
    private List<Card> cards = new ArrayList<>();
    private Jogo jogo;
    private int ponto[] = new int[2];//0 = acertou 1 = errou
    public Quadro()
    {
        this.coluna = 5;
        this.linha = 2;     
    }
    public Quadro(int linha, int coluna, Jogo jogo)
    {
        this.jogo = jogo;
        this.numeroClicks = 0;
        this.pontos = 0;
        this.linha = linha;
        this.coluna = coluna;
        cardsRestantes = coluna * linha;
        calcularPontos();
        setOpaque(false);
        setLayout(new GridLayout(linha, coluna));
        new Thread()
        {
            @Override
            public void run()
            {
                Embaralhar emb = new Embaralhar((Quadro.this.coluna * Quadro.this.linha)/2);
                List<CardEstatico> cardEs = emb.getCardes();
                for(CardEstatico c : cardEs)
                {
                    Card a = new Card(c.getID(), gerarIMG(c.getUrlImagePadr()), 
                            gerarIMG(c.getUrlImageReal()));
                    a.addMouseListener(new MouseAdapter(){
                        @Override
                        public void mouseClicked(MouseEvent evt)
                        {   
                            if(!ninguemPodeClicarEmNada)
                                click2(evt).start();
                        }
                    });
                    cards.add(a);
                }
                for(Card c : cards)
                {
                    add(c);
                }
                Quadro.this.jogo.stopLoading();
                if(Quadro.this.jogo.crono.isAlive())
                {
                    Quadro.this.jogo.crono.reiniciar();
                }
                else
                {
                    Quadro.this.jogo.crono.start();
                }
            }
        }.start();
    }
    public Thread showAll()
    {
        return new Thread()
        {
            public void run()
            {
                for(Card c : cards)
                {
                    c.imagemReal(false);
                }
                try
                {
                    sleep(4000);
                }catch(Exception e)
                {
                    System.out.println("Interface -> Quadro -> ShowAll() -> linha 131");
                }
                for(Card c : cards)
                {
                    if(!c.isVirado())
                        c.imagemPadrao(false);
                }
            }
        };
    }
    public int restam()
    {
        return cardsRestantes;
    }
    private void calcularPontos()
    {
        if(coluna * linha <= 8)
        {
            ponto[0] = 4;
            ponto[1] = 2;
        }
        else
        {
            if(coluna * linha <= 12)
            {
                ponto[0] = 6;
                ponto[1] = 3;
            }
            else
            {
                if(linha * coluna <= 16)
                {
                    ponto[0] = 8;
                    ponto[0] = 5;
                }
                else
                {
                    if(linha * coluna == 28)
                    {
                        ponto[0] = 12;
                        ponto[1] = 4;
                    }
                    else
                    {
                        ponto[0] = 12;
                        ponto[1] = 6;
                    }
                }
            }
        }
    }
    private Thread click2(final MouseEvent evt)
    {
        return new Thread()
        {
            public void run()
            {
                Card comp = (Card) evt.getComponent();
                if(!comp.isVirado())
                {
                    numeroClicks++;
                    EventQueue.invokeLater(new Runnable()
                    {
                        public void run()
                        {
                            jogo.click(numeroClicks);
                        }
                    });
                    if(clickUm)
                    {
                        primeCard = (Card) evt.getComponent();
                        primeCard.imagemReal(true);
                        clickUm = !clickUm;
                    }
                    else
                    {
                        if(primeCard != (Card) evt.getComponent())
                        {
                            segunCard = (Card) evt.getComponent();
                            segunCard.imagemReal(true);
                            ninguemPodeClicarEmNada = true;
                            if(primeCard.getID() == segunCard.getID())
                            {
                                primeCard.setVirado(true);
                                segunCard.setVirado(true);
                                cardsRestantes -= 2;
                                pontos += ponto[0];
                            }
                            else
                            {
                                try
                                {
                                    Thread.sleep(1000);
                                }
                                catch(InterruptedException ex)
                                {
                                    Logger.getLogger(Quadro.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                primeCard.imagemPadrao(true);
                                segunCard.imagemPadrao(true);
                                pontos -= ponto[1];
                            }
                            clickUm = !clickUm;
                            ninguemPodeClicarEmNada = false;
                            EventQueue.invokeLater(new Runnable()
                            {
                                public void run()
                                {
                                    jogo.attPontos(pontos);
                                }
                            });
                        }
                    }
                    if(cardsRestantes == 0)
                    {
                        EventQueue.invokeLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Quadro.this.jogo.crono.setStop(true);
                                Quadro.this.removeAll();
                                setLayout(new GridLayout(1, 1));
                                repaint();
                                JLabel l = new JLabel();
                                l.setIcon(new ImageIcon(getClass().getResource("/Interface/michell.jpg")));
                                l.setHorizontalAlignment(JLabel.CENTER);
                                add(l);
                            }
                        });
                    }
                }

            }
        };
    }
    private ImageIcon gerarIMG(ImageIcon inicial)
    {
        int width = (870/coluna);
        int height = (496/linha);
        ImageIcon img2 = new ImageIcon(inicial.getImage().getScaledInstance(width, 
                height, Image.SCALE_DEFAULT));
        return img2;
    }
    private ImageIcon gerarIMG(String url)
    {
        return gerarIMG(new ImageIcon(url));
    }
}
