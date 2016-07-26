package com.pedroaugusto.cobrinha;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Pedro on 07/06/2016.
 */
public class Loop extends Thread
{
    GameView surfaceView;
    SurfaceHolder holder;
    private boolean running;
    public Loop(GameView surfaceView, SurfaceHolder h)
    {
        this.surfaceView = surfaceView;
        holder = h;
    }
    @Override
    public void run() {
        Canvas canvas;
        while(running)
        {
            if(!this.surfaceView.gameOver)
            {
                canvas = null;
                canvas = holder.lockCanvas(null);
                if(canvas != null)
                {
                    synchronized(this.holder)
                    {
                        this.surfaceView.update(canvas);
                    }
                    this.holder.unlockCanvasAndPost(canvas);
                }
                try
                {
                    sleep(1000/8);
                } catch(InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
