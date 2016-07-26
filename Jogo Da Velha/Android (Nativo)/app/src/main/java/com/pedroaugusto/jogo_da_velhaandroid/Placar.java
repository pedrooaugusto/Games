package com.pedroaugusto.jogo_da_velhaandroid;
import android.widget.*;
import android.os.*;
import android.app.*;
public class Placar
{
	private TextView rodada,
		player1, player2, pPlayer1, pPlayer2;
	private int pontox = 0, pontoo = 0, rodadaInt = 0;
	public Placar(Activity v)
	{
		player1 = (TextView) v.findViewById(R.id.namePlayer1);
		player2 = (TextView) v.findViewById(R.id.namePlayer2);
		pPlayer1 = (TextView) v.findViewById(R.id.pontoo);
		pPlayer2 = (TextView) v.findViewById(R.id.pontox);
		rodada = (TextView) v.findViewById(R.id.rodada);
		pPlayer1.setText(""+pontoo);
		pPlayer2.setText(""+pontox);
		rodada.setText(""+rodadaInt);
	}
	public void setJogador1(String name)
	{
		player1.setText(name);
	}
	public void setJogador2(String name)
	{
		player2.setText(name);
	}
	public void addPontoo()
	{
		pPlayer1.setText(""+pontoo++);
	}
	public void addPontox()
	{
		pPlayer2.setText(""+pontox++);
	}
	public void addRodada()
	{
		rodada.setText(""+rodadaInt++);
	}
	
}
