package com.pedroaugusto.bolinha.bolinha;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Pedro on 08/06/2016.
 */
public class Sons
{
    final MediaPlayer wallBepp;
    final MediaPlayer blueBepp[] = new MediaPlayer[6];
    final MediaPlayer paddleBepp;
    final MediaPlayer ballOrignBepp;
    final MediaPlayer deadBepp;
    private int numBatidasBola = 1;
    public Sons(Context ctx)
    {
        wallBepp = MediaPlayer.create(ctx, R.raw.wallbeep);

        blueBepp[0] = MediaPlayer.create(ctx, R.raw.bluebeep);
        blueBepp[1] = MediaPlayer.create(ctx, R.raw.enemyslain);
        blueBepp[2] = MediaPlayer.create(ctx, R.raw.doublekill);
        blueBepp[3] = MediaPlayer.create(ctx, R.raw.triplekill);
        blueBepp[4] = MediaPlayer.create(ctx, R.raw.quadrakill);
        blueBepp[5] = MediaPlayer.create(ctx, R.raw.pentakill);

        paddleBepp = MediaPlayer.create(ctx, R.raw.paddlebeep);
        ballOrignBepp = MediaPlayer.create(ctx, R.raw.balloriginbeep);
        deadBepp = MediaPlayer.create(ctx, R.raw.deadbeep);
    }

    public void startWallBeep()
    {
        new Play(wallBepp).tocar();
    }

    public void startBlueBeep()
    {
        if(MainActivity.TYPE_SOUND == 0)
            new Play(blueBepp[0]).tocar();
        else
            new Play(blueBepp[numBatidasBola > 5 ? 5 : numBatidasBola]).tocar();
        numBatidasBola++;
    }

    public void startPaddleBeep()
    {
        numBatidasBola = 1;
        new Play(paddleBepp).tocar();
    }

    public void startBallOrignBepp()
    {
        new Play(ballOrignBepp).tocar();
    }

    public void startDeadBeep()
    {
        numBatidasBola = 1;
        new Play(deadBepp).tocar();
    }

    private class Play extends Thread
    {
        private MediaPlayer mediaPlayer;
        public Play(MediaPlayer mediaPlayer)
        {
            this.mediaPlayer = mediaPlayer;
        }
        public void tocar()
        {
            if(MainActivity.ENABLED_SOM)
                run();
        }
        @Override
        public void run(){
            mediaPlayer.start();
        }
    }
}
