package com.pedroaugusto.cobrinha;

/**
 * Created by Pedro on 18/07/2016.
 */
public class TempoMoeda extends Thread
{
    private Moeda moeda;
    private long comeco;
    private float tempo = 5;
    private float tempo_total = 5;
    private boolean alive = true;
    private Cobra cobra;
    public TempoMoeda(Moeda moeda, Cobra cobra) {
        this.moeda = moeda;
        this.cobra = cobra;
        comeco = System.currentTimeMillis()/1000;
    }

    @Override
    public void run() {
        while(alive)
        {
            float tempod = tempo;
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if(tempod != tempo)
                continue;
            tempo--;
            if(tempo == -1)
            {
                tempo = tempo_total;
                moeda.generate();
                cobra.startAnimation();
            }
        }
    }

    public void restart()
    {
        tempo = tempo_total;
    }

    public void terminar()
    {
        this.alive = false;
    }

    public String getInfo()
    {
        return (int)tempo+"";
    }
}
