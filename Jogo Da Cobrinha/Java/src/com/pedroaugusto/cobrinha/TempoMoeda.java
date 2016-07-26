/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pedroaugusto.cobrinha;

import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class TempoMoeda extends Thread{
    private final Moeda moeda;
    private long comeco;
    private float tempo = 5;
    private float tempo_total = 5;
    private boolean alive = true;
    private final Cobra cobra;
    public TempoMoeda(Moeda moeda, Cobra cobra) {
        this.moeda = moeda;
        this.cobra = cobra;
        comeco = System.currentTimeMillis()/1000;
    }

    @Override
    public void run() {
        while(true)
        {
            float tempod = tempo;
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TempoMoeda.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(tempod != tempo)
                continue;
            tempo--;
            if(tempo == -1)
            {
                tempo = tempo_total;
                cobra.startAnimation();
            }
        }
    }
    private void attTime()
    {
        synchronized(this)
        {
            tempo = tempo_total - (System.currentTimeMillis()/1000 - comeco);
        }
    }
    public void restart()
    {
        tempo = tempo_total;
    }
    private void checkTime()
    {
        if(tempo == -1)
        {
            moeda.generate();
            comeco = System.currentTimeMillis()/1000;
            tempo = tempo_total;
        }
    }
    public String getInfo()
    {
        return (int)tempo+"";
    }
}
