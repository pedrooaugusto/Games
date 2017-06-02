package com.pedroaugusto.bolinha;

import java.util.List;


/**
 * Created by Pedro on 06/06/2016.
 */
public class Colisao
{
    private Sons sons = null;
    public Colisao()
    {
        sons = new Sons();
    }

    public boolean algumaColisao(Bola b, Barra bar, List<Obstaculo> obs)
    {
        boolean alguma = false;
        if(barraColisaoTerreno(bar))
            alguma = true;
        if(bolaColisaoTerreno(b, bar))
            alguma = true;
        if(barraColisaoBola(b, bar))
            alguma = true;
        if(bolaColisaoObstaculo(b, obs))
            alguma = true;
        return alguma;
    }

    private boolean bolaColisaoObstaculo(Bola b, List<Obstaculo> lista)
    {
        boolean achou = false;
        for(int i = 0; i < lista.size(); i++)
        {
            if (colidiu(b, lista.get(i), false, 0)) 
            {
                sons.startBlueBeep();
                b.setVelocidadeY(b.getVelocidadeY()*b.getRestituicao());
                b.setVelocidadeX(emQueParte(b, lista.get(i)));
                achou = true;
                lista.remove(i);
                break;
            }
        }
        return achou;
    }

    private boolean barraColisaoBola(Bola b, Barra bar)
    {
        if(colidiu(b, bar, true, 0.9f))
        {
            sons.startPaddleBeep();
            b.setVelocidadeY(b.getVelocidadeY()*b.getRestituicao());
            b.setVelocidadeX(emQueParte(b, bar));
            b.setY(bar.getY() - b.getRaio()*2);
            return true;
        }
        return false;
    }

    private boolean barraColisaoTerreno(Barra b)
    {
        if(b.getX() + b.getVelocidadeX() < 0 - b.getWidth()*0.65)
        {
            b.setX(0 - b.getWidth()*0.65f);
            return true;
        }
        else if(b.getX() + b.getVelocidadeX() > GameView.LARGURA_TELA - b.getWidth()*0.35f)
        {
            b.setX( GameView.LARGURA_TELA - b.getWidth()*0.35f);
            return true;
        }
        return false;
    }

    private boolean bolaColisaoTerreno(Bola b, Barra barra)
    {
        if(b.getY() < b.getRaio())//Colisão teto
        {
            sons.startWallBeep();
            b.setVelocidadeY(b.getVelocidadeY()*b.getRestituicao());
            b.setY(b.getRaio());
            return true;
        }
        if(b.getY() > GameView.ALTURA_TELA - b.getRaio())//Colisao chao
        {
            sons.startDeadBeep();
            barra.perderVida();
            if(barra.getVidas() != -1)
            {
                b.setVelocidadeY(b.getVelocidadeY());
                b.setY(GameView.ALTURA_TELA * 0.6f);
                b.setX(GameView.LARGURA_TELA * 0.5f);
                b.setVelocidadeX(0);
                b.setRecemNascido(true);
            }
            return true;
        }
        if(b.getX() > GameView.LARGURA_TELA - b.getRaio())//Colisão lado direito
        {
            sons.startWallBeep();
            b.setVelocidadeX(b.getVelocidadeX()*b.getRestituicao());
            b.setX((GameView.LARGURA_TELA - (b.getRaio())));
            return true;
        }
        if(b.getX() < b.getRaio())//Colisão lado esquerdo
        {
            sons.startWallBeep();
            b.setVelocidadeX(b.getVelocidadeX()*b.getRestituicao());
            b.setX(b.getRaio());
            return true;
        }
        return false;
    }

    private float emQueParte(Bola bola, ObjetoRectangulo objeto)
    {
        if(bola.getX() < objeto.getX() + (objeto.getWidth() * 0.35))
                return Math.abs(bola.getVelocidadeY()) * (-1);
        else if(bola.getX() + bola.getWidth() < objeto.getX() + (objeto.getWidth() * 0.7))
                return 0;
        return Math.abs(bola.getVelocidadeY());
    }
    public static boolean colidiu (ObjetoRectangulo objeto2, ObjetoRectangulo objeto1,
                                   boolean margemErro, float valor)
    {
        float x1 = (objeto1.getX());
        float y1 = (objeto1.getY());
        float w1 = (objeto1.getWidth());
        float h1 = (objeto1.getHeight());

        float x2 = (objeto2.getX());
        float y2 = (objeto2.getY());
        float w2 = (objeto2.getWidth());
        float h2 = (objeto2.getHeight());
        if ((x2 > x1 + w1 || y2 > y1 + h1) || (x2 + w2 < x1 || y2 + h2 < y1)) {
            return false;
        }
        if (margemErro) {
            if (x1 < x2 && (x1 + w1 * valor) < x2) {
                return false;
            } else if ((x2 + w2) < (x1 + w1 * (1 - valor))) {
                return false;
            }
        }
        return true;
    }

}
