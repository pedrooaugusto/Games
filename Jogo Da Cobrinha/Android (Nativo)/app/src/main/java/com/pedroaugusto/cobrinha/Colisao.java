package com.pedroaugusto.cobrinha;

/**
 * Created by Pedro on 21/07/2016.
 */
public class Colisao {
    private final GameView gm;

    public Colisao(GameView gm) {
        this.gm = gm;
    }

    public boolean algumaColisao(Cobra cobra, Moeda moeda, TempoMoeda tp)
    {
        boolean alguma = false;
        if(cobraColisaoMoeda(cobra, moeda, tp))
            alguma = true;
        if(cobraColisaoBody(cobra))
            alguma = true;
        if(cobraColisaoWall(cobra))
            alguma = true;
        return alguma;
    }
    private boolean cobraColisaoWall(Cobra b)
    {
        boolean colidiu = false;
        if(b.getX() > GameView.LARGURA_TELA - b.getWidth())
            colidiu = true;
        else if(b.getX() < 0)
            colidiu = true;
        else if(b.getY() > GameView.ALTURA_TELA - b.getHeight())
            colidiu = true;
        else if(b.getY() < 0)
            colidiu = true;
        if(colidiu)
            gm.pause(true);
        return colidiu;
    }

    private boolean cobraColisaoMoeda(Cobra cobra, Moeda moeda, TempoMoeda tp)
    {
        if (this.colidiu(cobra, moeda, false, 0)) {
            cobra.addPartes(new Parte());
            moeda.generate(cobra.getVelocidadeX());
            tp.restart();
            if ((cobra.getPartes().size()) % 4 == 0) {
                cobra.attPerdeSeMorrer();
                //moeda.changeMovement();
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean cobraColisaoBody(Cobra cobra)
    {
        for (int i = 1; i < cobra.getPartes().size(); i++) {
            if (cobra.getPartes().get(i).isEnabled()) {
                if (colidiu(cobra, cobra.getPartes().get(i), false, 0)) {
                    //clearTimeout(gameLoop);
                    //gameLoop = null;
                    gm.pause(true);
                    return true;
                }
            }
        }
        return false;
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
