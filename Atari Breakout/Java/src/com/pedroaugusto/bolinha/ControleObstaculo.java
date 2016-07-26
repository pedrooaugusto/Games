package com.pedroaugusto.bolinha;


import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Pedro on 06/06/2016.
 */
public class ControleObstaculo {

    private List<Obstaculo> obstaculos = new ArrayList<Obstaculo>();

    public ControleObstaculo()
    {
        //create(false);
    }

    public List<Obstaculo> getList()
    {
        return obstaculos;
    }
    public void create(boolean aleatorio)
    {
        obstaculos.clear();
        float widthObstaculo = Obstaculo.dimensions().x;
        float heightObstaculo = Obstaculo.dimensions().y;
        int linhas = (int)(GameView.LARGURA_TELA/widthObstaculo);
        int colunas = (int)((GameView.ALTURA_TELA*0.3f)/(heightObstaculo));
        int espacamento = (int)((GameView.LARGURA_TELA*0.051)/(linhas+1));
        int espacamento2 = (int)((GameView.ALTURA_TELA*0.05)/(colunas+1));
        System.out.println(GameView.TYPE_OBS);
        if(GameView.TYPE_OBS == 0)
            for(int i = 0; i < linhas; i++)
            {
                for(int j = 0; j < colunas; j++)
                {
                    int x = (int)(i * widthObstaculo)+(espacamento*(i+1));
                    int y = 80+(int)(j * heightObstaculo)+(espacamento2*(j+1));
                    Random r = new Random();
                    int v = r.nextInt(256);
                    int ve = r.nextInt(256);
                    int a = r.nextInt(256);
                    Obstaculo o = new Obstaculo(x, y, v, ve, a);
                    obstaculos.add(o);
                }
            }
        else
            for(int i = 0; i < colunas; i++)
            {
                int total = 0;
                do
                {
                    Random r = new Random();
                    int v = r.nextInt(256);
                    int ve = r.nextInt(256);
                    int a = r.nextInt(256);
                    int x = total + espacamento;
                    int y = (int)(i*heightObstaculo+(espacamento*(i+1)));
                    Obstaculo obs = new Obstaculo(x, y, v, ve, a);
                    int w = r.nextInt((int)(GameView.LARGURA_TELA*0.6));
                    w = (total + w + espacamento >= (GameView.LARGURA_TELA - espacamento) ? (int)((GameView.LARGURA_TELA
                            - total) - espacamento) : w);
                    obs.setDimensao(w - espacamento, heightObstaculo);
                    obstaculos.add(obs);
                    total = total + w + espacamento;
                }while(total < (GameView.LARGURA_TELA - espacamento));
            }
    }

    public void draw(Graphics2D ctx)
    {
        for(Obstaculo a : obstaculos)
            a.draw(ctx);
    }
}
