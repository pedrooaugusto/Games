package com.pedroaugusto.bolinha;

/**
 * Created by Pedro on 08/06/2016.
 */
public class Retangulo
{
    public float x, y, w, h;

    public Retangulo(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
    }
    public Retangulo toRect()
    {
        w += x;
        h += y;
        return this;
    }
}
