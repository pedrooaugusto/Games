/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

/**
 *
 * @author Pedro
 */
public class Historico {
    private int a;
    private int b;
    private int xa, ya;
    private int xb, yb;

    public Historico(int ... values) {
        this.a = values[0];
        this.b = values[1];
        this.xa = values[2];
        this.ya = values[3];
        this.xb = values[4];
        this.yb = values[5];
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getXa() {
        return xa;
    }

    public void setXa(int xa) {
        this.xa = xa;
    }

    public int getYa() {
        return ya;
    }

    public void setYa(int ya) {
        this.ya = ya;
    }

    public int getXb() {
        return xb;
    }

    public void setXb(int xb) {
        this.xb = xb;
    }

    public int getYb() {
        return yb;
    }

    public void setYb(int yb) {
        this.yb = yb;
    }
    
}
