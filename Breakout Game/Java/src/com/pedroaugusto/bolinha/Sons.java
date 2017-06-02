package com.pedroaugusto.bolinha;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Created by Pedro on 08/06/2016.
 */
public class Sons
{
    final Media wallBepp;
    final Media blueBepp[] = new Media[6];
    final Media paddleBepp;
    final Media ballOrignBepp;
    final Media deadBepp;
    private int numBatidasBola = 1;
    public Sons()
    {
        JFXPanel fxPanel = new JFXPanel();
        wallBepp = new Media(getClass().getResource("sons/wallbeep.mp3").toExternalForm());
        
        blueBepp[0] = new Media(getClass().getResource("sons/bluebeep.mp3").toExternalForm());
        blueBepp[1] = new Media(getClass().getResource("sons/enemyslain.mp3").toExternalForm());
        blueBepp[2] = new Media(getClass().getResource("sons/doublekill.mp3").toExternalForm());
        blueBepp[3] = new Media(getClass().getResource("sons/triplekill.mp3").toExternalForm());
        blueBepp[4] = new Media(getClass().getResource("sons/quadrakill.mp3").toExternalForm());
        blueBepp[5] = new Media(getClass().getResource("sons/pentakill.mp3").toExternalForm());

        paddleBepp = new Media(getClass().getResource("sons/paddlebeep.mp3").toExternalForm());
        ballOrignBepp = new Media(getClass().getResource("sons/balloriginbeep.mp3").toExternalForm());
        deadBepp = new Media(getClass().getResource("sons/deadbeep.mp3").toExternalForm());
    }

    public void startWallBeep()
    {
        new Play(wallBepp).tocar();
    }

    public void startBlueBeep()
    {
        if(GameView.TYPE_SOUND == 0)
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
        public Play(Media sound)
        {
            this.mediaPlayer = new MediaPlayer(sound);
        }
        public void tocar()
        {
            if(GameView.ENABLED_SOM)
                start();
        }
        @Override
        public void run(){
            mediaPlayer.play();
        }
    }
}
