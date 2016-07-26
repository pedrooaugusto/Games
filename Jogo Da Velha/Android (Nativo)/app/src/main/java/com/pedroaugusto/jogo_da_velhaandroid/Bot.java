package com.pedroaugusto.jogo_da_velhaandroid;
import android.widget.*;
import java.util.*;
import android.graphics.drawable.*;

public class Bot
{
	public static boolean playWithBot = false;
	static public int numJogadas = 0;
	static public List<String[]> historicoMovimento = new ArrayList<>();
	public static void vezDoBot()
	{
		if(!Quadro.noClicks)
		{
			if(!possivelVitoria())//Caso ninguém esteja prestes a ganhar
			{
				switch(numJogadas)
				{
					case 0://Primeira jogada do bot
						if(Quadro.allText[4].getText().equals("x"))//Se o centro estiver ocupado
							jogarEmLugarDisponivelAleatorio(new int[]{1, 3, 5, 7});//jogar na diagonal
						else//centro livre
						{
							Quadro.allText[4].setText("o");//jogar no centro
							addMovimento(Quadro.allText[4]);
						}
						break;
					case 1://Segunda jogada do bot (dicide-se o jogo)
						if(Quadro.main.getRodada() % 2 == 0)//Caso o bot tenha começado jogando
							if(Quadro.historicoMovimento.get(0)[0].equals("E"))//E o user tenha marcado uma esquina
								jogarEmLugarDisponivelAleatorio(new int[]{1, 3, 5, 7});//Não importa, sem chances de ganhar
							else//E o user marcou um dos cantos-centro
								jogarEmLugarDisponivelAleatorio(new int[]{1, 3, 5, 7});//Jogue um uma esquina e o jogo acabou
						else//Caso o humano tenha começado jogando
							if(Quadro.historicoMovimento.get(0)[0].equals("E") &&
									Quadro.historicoMovimento.get(1)[0].equals("E"))
								//E tenha marcado em uma esquina 2 vezes seguidas
								jogarEmLugarDisponivelAleatorio(new int[]{0, 2, 6, 8});//Jogar em um dos cantos-centro que é gg
							else if(Quadro.historicoMovimento.get(0)[0].equals("M"))//Caso tenha roubado nosso centro
								jogarEmLugarDisponivelAleatorio(new int[]{1, 3, 5, 7});//vamos para as esquinas
							else if(Quadro.historicoMovimento.get(0)[0].equals("E") &&
									Quadro.historicoMovimento.get(1)[0].equals("C"))
								jogarIntercecaoDeDoisWins(1);
							else if(!jogarIntercecaoDeDoisWins(0))
								jogarEmLugarDisponivelAleatorio(new int[]{1, 3, 5, 7});
						break;
					default://O jogo acaba na segunda vez em que o bot acaba por isso, não importa
						jogarEmLugarDisponivelAleatorio(new int[]{42, 42, 42});//não importa, vc não vai nãi vai ganhar
				}
			}
			numJogadas+=1;
		}
	}

	private static boolean possivelVitoria()
	{
		int onde = 42;
		TextView l[] = null;
		TextView[][] winAll = Quadro.winAllText;
		for(int i = 0; i < winAll.length; i++)
		{
			String texto = winAll[i][0].getText().toString() +
					winAll[i][1].getText().toString() + winAll[i][2].getText().toString();
			if(texto.equals("oo"))
			{
				quemTaVazio(winAll[i]);
				return true;
			}
			else if(texto.equals("xx"))
				l = winAll[i];
		}
		if (l != null) {
			quemTaVazio(l);
			return true;
		}
		return false;
	}

	public static void addMovimento(TextView lugar)
	{
		//E = esquinas(like 0), C = centro reta (like 1), M = meio
		switch(Quadro.indexOf(lugar))
		{
			case 0: case 2: case 6: case 8:
				historicoMovimento.add(new String[] {"E", Quadro.indexOf(lugar)+""});
			break;
			case 4:
				historicoMovimento.add(new String[] {"M", Quadro.indexOf(lugar)+""});
				break;
			default:
				historicoMovimento.add(new String[] {"C", Quadro.indexOf(lugar)+""});
		}
	}

	public static int indexOf(int key, int vect[])
	{
		for(int i = 0; i < vect.length; i++)
			if(vect[i] == key)
				return i;
		return -1;
	}

	public static int indexOf(TextView key, TextView vect[])
	{
		for(int i = 0; i < vect.length; i++)
			if(vect[i] == key)
				return i;
		return -1;
	}

	private static void quemTaVazio(TextView[] argument)
	{
		int index = 42;
		for(int i = 0; i < argument.length; i++)
		{
			if(argument[i].getText().equals(""))
			{
				argument[i].setText("o");
				addMovimento(argument[i]);
			}
		}
	}

	public static boolean jogarIntercecaoDeDoisWins(int n)
	{
		TextView winAll[][] = Quadro.winAllText;
		List<TextView[]> winsVazios = new ArrayList<>();
		TextView winUserMarcouPorUltimo[] = null;
		for (int i = 0; i < winAll.length; i++) {
			String texto = winAll[i][0].getText().toString()
					+ winAll[i][1].getText().toString() +
					winAll[i][2].getText().toString();
			if(texto.equals("x") && indexOf(Quadro.allText[Integer.parseInt
					(Quadro.historicoMovimento.get(n)[1])],
					winAll[i]) != -1)
			{
				winUserMarcouPorUltimo = winAll[i];
			}
			else if(texto.equals("x"))
			{
				winsVazios.add(winAll[i]);
			}
		}
		if(winUserMarcouPorUltimo != null)
			for (int i = 0; i < winUserMarcouPorUltimo.length; i++) {
				for (int j = 0; j < winsVazios.size(); j++) {
					for (int k = 0; k < winsVazios.get(j).length; k++) {
						if(indexOf(winsVazios.get(j)[k], winUserMarcouPorUltimo) != -1)
						{
							winsVazios.get(j)[k].setText("o");
							addMovimento(winsVazios.get(j)[k]);
							return true;
						}
					}
				}
			}
		return false;
	}

	public static void jogarEmLugarDisponivelAleatorio(int excluidos[])
	{
		List<Integer> possiveis = new ArrayList<>();
		for(int j = 0; j < Quadro.allText.length; j++)
			if(Quadro.allText[j].getText().length() == 0 && indexOf(j, excluidos) == -1)
				possiveis.add(j);
		int total = possiveis.size();
		int qual = (int) Math.floor(Math.random() * total);
		Quadro.allText[possiveis.get(qual)].setText("o");
		addMovimento(Quadro.allText[possiveis.get(qual)]);
	}
}
