package com.pedroaugusto.cobrinha;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Pedro on 18/07/2016.
 */
public class Parte extends ObjetoRectangulo{

    private boolean enabled = true;

    public Parte() {
        setNome("Parte");
        setCor(Color.WHITE);
        setDimensao((GameView.LARGURA_TELA*0.03f),
                (GameView.LARGURA_TELA*0.03f));
        setPosisao(-45, -45);
    }
    @Override
    public void draw(Canvas canvas, Paint paint) {
        setCor(prestesMorrer());
        paint.setColor(getCor());
        paint.setShadowLayer(5, 0, 0, new Color().rgb(234, 234, 234));
        Retangulo r = new Retangulo(getX(), getY(), getWidth(), getHeight());
        r = r.toRect();
        canvas.drawRect(r.x, r.y, r.w, r.h, paint);
        paint.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
    }
    private int prestesMorrer()
    {
        if(this.enabled)
            return Color.WHITE;
        else
        if(getCor() == Color.WHITE)
            return new Color().rgb(46, 25, 97);
        else
            return Color.WHITE;
    }
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
