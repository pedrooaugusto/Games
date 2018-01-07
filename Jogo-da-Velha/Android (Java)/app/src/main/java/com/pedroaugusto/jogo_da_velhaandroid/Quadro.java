package com.pedroaugusto.jogo_da_velhaandroid;
import android.app.Activity;
import android.widget.TextView;
import android.view.View.*;
import android.view.*;
import javax.security.auth.*;
import android.view.animation.*;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Quadro
{
	public static MainActivity main;
	public static boolean noClicks = false;
	static public List<String[]> historicoMovimento = new ArrayList<>();
	private int jogador = 88;
	private final int[] all =
	{
		R.id.q1, R.id.q2, R.id.q3,
		R.id.q4, R.id.q5, R.id.q6,
		R.id.q7, R.id.q8, R.id.q9
	};
	private final int [][] winAll =
	{
		{ all[0], all[1], all[2] },
		{ all[3], all[4], all[5] },
		{ all[6], all[7], all[8] },
		{ all[0], all[3], all[6] },
		{ all[1], all[4], all[7] },
		{ all[2], all[5], all[8] },
		{ all[0], all[4], all[8] },
		{ all[2], all[4], all[6] }
	};
	private OnClickListener click = new OnClickListener()
	{
		@Override
		public void onClick(View p1)
		{
			TextView v=(TextView)p1;
			doClick(v);
		}
	};
	public static final TextView [][] winAllText = new TextView[8][3];
	public static final TextView [] allText = new TextView[9];
	public Quadro(MainActivity main)
	{
		this.main = main;
		carregarComps(winAll, all);
	}
	private void carregarComps(int [][] v, int h[])
	{
		for(int i = 0; i < v.length; i++)
		{
			for(int j = 0; j < v[i].length; j++)
			{
				winAllText[i][j] = (TextView)
					main.findViewById(v[i][j]);
				winAllText[i][j].setOnClickListener(click);
			}
		}
		for(int j = 0; j < h.length; j++)
		{
			allText[j] = (TextView) main.findViewById(h[j]);
		}
		ol();
	}
	public void ol (int... oi)
	{
	}
	private void doClick(TextView v)
	{
		if(v.getText().length() == 0 && !noClicks)
		{
			if(jogador == 88)
			{
				v.setText("x");
				jogador = 79;
				gameLoop();
				addMovimento(v);
				if(Bot.playWithBot == true)
				{
					if(!isFull())
					{
						jogador = 88;
						Bot.vezDoBot();
						gameLoop();
					}
				}
				else
				{
					MainActivity.DoAnimation.suaVezO();
				}
			}
			else
			{
				v.setText("o");
				jogador = 88;
				gameLoop();
				MainActivity.DoAnimation.suaVezX();
			}
		}
	}

	public static void addMovimento(TextView lugar)
	{
		//E = esquinas(like 0), C = centro reta (like 1), M = meio
		switch(indexOf(lugar))
		{
			case 0: case 2: case 6: case 8:
			historicoMovimento.add(new String[] {"E", indexOf(lugar)+""});
			break;
			case 4:
				historicoMovimento.add(new String[] {"M", indexOf(lugar)+""});
				break;
			default:
				historicoMovimento.add(new String[] {"C", indexOf(lugar)+""});
		}
	}

	public void clear()
	{
		for(int i = 0; i < all.length; i++)
		{
			allText[i].setText("");
			allText[i].setTextColor(Color.BLACK);
			allText[i].setBackgroundColor(Color.WHITE);
		}
		Bot.historicoMovimento.clear();
		historicoMovimento.clear();
		Bot.numJogadas = 0;
		main.updateRodada();
		noClicks = false;
		if(Bot.playWithBot && main.getRodada() % 2 == 0)
		{
			Bot.vezDoBot();
			gameLoop();
			jogador = 88;
		}
	}

	private boolean isFull()
	{
		boolean cheio = true;
		for(int i = 0; i < allText.length; i++)
		{
			if(allText[i].getText().toString().isEmpty())
			{
				cheio = false;
			}
		}
		return cheio;
	}
	public static int indexOf(TextView k)
	{
		for(int i = 0; i < allText.length; i++)
			if(allText[i] == k)
				return i;
		return -1;
	}

	private void shine(int which)
	{
		for(int i = 0; i < winAllText.length; i++) 
		{
			for (int j = 0; j < winAllText[i].length; j++) 
			{
				winAllText[i][j].setBackgroundColor((Color.argb(30, 128,128,128)));
				winAllText[i][j].setTextColor(Color.argb(50, 0, 0, 0));
			}
		}
		for(int k = 0; k < winAllText[which].length; k++) 
		{
			winAllText[which][k].setBackgroundColor((Color.rgb(255, 255, 255)));
			winAllText[which][k].setTextColor(Color.RED);
		}
	}

	private boolean anyVictory()
	{
		boolean vitoria = false;
		for (int i = 0; i < winAllText.length; i++) 
		{
			String texto = winAllText[i][0].getText().toString();
			texto += winAllText[i][1].getText().toString();
			texto += winAllText[i][2].getText().toString();
			if(texto.equals("ooo") || texto.equals("xxx"))
			{
				vitoria = true;
				noClicks = true;
				shine(i);
				break;
			}
		}
		return vitoria;
	}

	private void gameLoop()
	{
		if(!noClicks && anyVictory())
		{
			if(jogador == 79)
			{
				main.setPontos1();
			}
			else
			{
				main.setPontos2();
			}
			MainActivity.DoAnimation.win();
		}
		else
		{
			if(isFull())
			{
				MainActivity.DoAnimation.win();
			}
		}
	}

}
