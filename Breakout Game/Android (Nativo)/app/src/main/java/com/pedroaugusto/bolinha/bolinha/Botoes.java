package com.pedroaugusto.bolinha.bolinha;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by Pedro on 07/06/2016.
 */
public class Botoes
{
    private Botao.BotaoMovimento leftButton;
    private Botao.BotaoMovimento rightButton;
    public Botoes()
    {
        leftButton = new Botao.BotaoMovimento("←", 'l');
        rightButton = new Botao.BotaoMovimento("→", 'r');
    }
    public boolean click (MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(leftButton.click(event.getX(), event.getY()))
            {
                rightButton.setPressed(false);
                leftButton.setPressed(true);
                return true;
            }
            else if(rightButton.click(event.getX(), event.getY()))
            {
                leftButton.setPressed(false);
                rightButton.setPressed(true);
                return true;
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
            {
                if(leftButton.click(event.getX(), event.getY()))
                {
                    leftButton.setPressed(false);
                }
                else if(rightButton.click(event.getX(), event.getY()))
                {
                    rightButton.setPressed(false);
                }
                return false;
            }
        return false;
    }
    public void draw(Canvas ctx, Paint p)
    {
        leftButton.draw(ctx, p);
        rightButton.draw(ctx, p);
    }
    public boolean[] getButtons()
    {
        return new boolean[]{leftButton.isPressed, rightButton.isPressed};
    }

    /*private class BotaoMovimento extends com.pedroaugusto.bolinha.bolinha.Botao
    {
        public BotaoMovimento(String texto) {
            super(texto);
        }
        @Override
        public void draw(Canvas c, Paint p) {
            if(isPressed)
            {
                p.setColor(new Color().parseColor("#414142"));
            }
            else
                p.setColor(new Color().parseColor("#151517"));
            c.drawRect(GameView.getRect(getX(), getY(), getWidth(), getHeight()), p);
            p.setColor(new Color().parseColor("#04174E"));
            c.drawCircle(getX() + (getWidth() / 2), getY() + (getHeight() / 2), getHeight() / 2.3f, p);
            p.setColor(Color.WHITE);
            p.setTextSize(getHeight()/2);
            c.drawText(texto, getX() + (getHeight()/2)*0.9f, getY() + (getHeight()/2)*1.25f, p);
        }

        public boolean isPressed() {
            return isPressed;
        }

        public void setPressed(boolean pressed) {
            isPressed = pressed;
        }
    }*/
}
