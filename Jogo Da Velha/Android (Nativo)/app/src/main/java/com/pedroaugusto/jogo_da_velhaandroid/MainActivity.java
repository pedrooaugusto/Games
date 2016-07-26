package com.pedroaugusto.jogo_da_velhaandroid;
import android.app.*;
import android.os.*;
import android.text.*;
import android.widget.*;
import android.view.View.*;
import android.view.animation.*;
import android.animation.*;

public class MainActivity extends Activity
{
    Menu menu;
    Quadro quadro;
    TextView np1, np2, p1, p2;
    static MainActivity g;
    private int[] pontos = {0, 0};
    private int rod=-1;
    static TextView suaVez, next, rodada;
    static int pontoA, pontoB;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        np1 = (TextView) findViewById(R.id.namePlayer1);
        np2 = (TextView) findViewById(R.id.namePlayer2);
        p1 = (TextView) findViewById(R.id.pontoo);
        p2 = (TextView) findViewById(R.id.pontox);
        menu = new Menu(this);
        menu.start();
        quadro = new Quadro(this);
        g = this;
        suaVez = (TextView) findViewById(R.id.q42);
        next = (TextView) findViewById(R.id.next);
        rodada = (TextView) findViewById(R.id.rodada);
        next.setVisibility(android.view.View.GONE);
        pontoA = suaVez.getLeft() + 7;
        pontoB = getWindowManager().getDefaultDisplay().getWidth();
        pontoB -= 227;
    }
    public static void printf(String obj)
    {
        Toast.makeText(g, obj, Toast.LENGTH_SHORT).show();
    }
    public void setNames(String[] nome)
    {
        np1.setText(nome[0]);
        np2.setText(nome[1]);
    }
    public void clearQuadroOutsidePQADeadLineTaChegando()
    {
        quadro.clear();
    }
    public void updateRodada()
    {
        rod++;
        rodada.setText(""+rod);
    }
    public int getRodada() {return rod;}

    public void setPontos2()
    {
        pontos[0]+=1;
        p1.setText("Vitoriais: "+pontos[0]);
    }
    public void setPontos1()
    {
        pontos[1]+=1;
        p2.setText("Vitoriais: "+pontos[1]);
    }
    public void animateNext(android.view.View v)
    {
        quadro.clear();
        v.setVisibility(v.GONE);
    }
    public static class DoAnimation
    {
        private static void animate(android.view.View oque, String propriedade,
                                    int tempo, int inicio, int fim)
        {
            ObjectAnimator anim = ObjectAnimator.ofFloat(oque, propriedade, inicio, fim);
            anim.setDuration(tempo);
            anim.start();
        }
        public static void suaVezO()
        {
            animate(suaVez, "x", 500, pontoA, pontoB);
        }
        public static void suaVezX()
        {
            animate(suaVez, "x", 500, pontoB, pontoA);
        }
        public static void win()
        {
            next.setVisibility(android.view.View.VISIBLE);
            animate(next, "x", 400, pontoA, 250);
        }
    }
}

