/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Negocio;

import Interface.Quadro;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;

/**
 *
 * @author Pedro
 */
public class Bot
{
    private boolean playWithBot = true;
    Quadro quadro;
    public int numJogadas = 0;
    public List<String[]> historicoMovimento = new ArrayList<>();
    public Bot(Quadro quadro)
    {
        this.quadro = quadro;
    }
    public void vezDoBot()
    {
        if(!quadro.getNoClicks())
        {
            if(!possivelVitoria())//Caso ninguém esteja prestes a ganhar
            {
                switch(numJogadas)
		{
                    case 0://Primeira jogada do bot
                        if(quadro.getAll()[4].getText().equals("x"))//Se o centro estiver ocupado
                            jogarEmLugarDisponivelAleatorio(new int[]{1, 3, 5, 7});//jogar na diagonal
                        else//centro livre
                        {
                            quadro.getAll()[4].setText("o");//jogar no centro
                            addMovimento(quadro.getAll()[4]);
                        }
                    break;
                    case 1://Segunda jogada do bot (dicide-se o jogo)
			if(quadro.game.rodada % 2 == 0)//Caso o bot tenha começado jogando
                            if(quadro.historicoMovimento.get(0)[0].equals("E"))//E o user tenha marcado uma esquina
				jogarEmLugarDisponivelAleatorio(new int[]{1, 3, 5, 7});//Não importa, sem chances de ganhar
                            else//E o user marcou um dos cantos-centro
				jogarEmLugarDisponivelAleatorio(new int[]{1, 3, 5, 7});//Jogue um uma esquina e o jogo acabou
                        else//Caso o humano tenha começado jogando
                            if(quadro.historicoMovimento.get(0)[0].equals("E") && 
                                    quadro.historicoMovimento.get(1)[0].equals("E"))
                            //E tenha marcado em uma esquina 2 vezes seguidas
                                jogarEmLugarDisponivelAleatorio(new int[]{0, 2, 6, 8});//Jogar em um dos cantos-centro que é gg
                            else if(quadro.historicoMovimento.get(0)[0].equals("M"))//Caso tenha roubado nosso centro
				jogarEmLugarDisponivelAleatorio(new int[]{1, 3, 5, 7});//vamos para as esquinas
                            else if(quadro.historicoMovimento.get(0)[0].equals("E") && 
                                    quadro.historicoMovimento.get(1)[0].equals("C"))
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
    public boolean isPlayWithBot()
    {
        return playWithBot;
    }
    public void setPlayWithBot(boolean b)
    {
        playWithBot = b;
    }
    
    private boolean possivelVitoria()
    {
        int onde = 42;
        JTextField l[] = null;
        JTextField[][] winAll = quadro.getWinAll();
        for(int i = 0; i < winAll.length; i++)
        {
            String texto = winAll[i][0].getText() + winAll[i][1].getText() + winAll[i][2].getText();
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
    
    private void quemTaVazio(JTextField[] argument)
    {
        int index = 42;
        for(int i = 0; i < argument.length; i++)
        {
            if(argument[i].getText().equals(""))
            {
		argument[i].setText("o");
		this.addMovimento(argument[i]);
            }
        }
    }
    
    public int indexOf(int key, int vect[])
    {
        for(int i = 0; i < vect.length; i++)
            if(vect[i] == key)
                return i;
        return -1;
    }
    
    public int indexOf(JTextField key, JTextField vect[])
    {
        for(int i = 0; i < vect.length; i++)
            if(vect[i] == key)
                return i;
        return -1;
    }
    
    public void jogarEmLugarDisponivelAleatorio(int excluidos[])
    {
	List<Integer> possiveis = new ArrayList<>();
	for(int j = 0; j < quadro.getAll().length; j++)
		if(quadro.getAll()[j].getText().length() == 0 && indexOf(j, excluidos) == -1)
			possiveis.add(j);
	int total = possiveis.size();
	int qual = (int) Math.floor(Math.random() * total);
	quadro.getAll()[possiveis.get(qual)].setText("o");
	this.addMovimento(quadro.getAll()[possiveis.get(qual)]);
    }
    
    public void addMovimento(JTextField lugar)
    {
	//E = esquinas(like 0), C = centro reta (like 1), M = meio
	switch(quadro.indexOf(lugar))
	{
            case 0: case 2: case 6: case 8:
                this.historicoMovimento.add(new String[] {"E", quadro.indexOf(lugar)+""});
            break;
            case 4:
		this.historicoMovimento.add(new String[] {"M", quadro.indexOf(lugar)+""});
            break;
            default:
		this.historicoMovimento.add(new String[] {"C", quadro.indexOf(lugar)+""});
	}	
    }
    
    public boolean jogarIntercecaoDeDoisWins(int n)
    {
        JTextField winAll[][] = quadro.getWinAll();
	List<JTextField[]> winsVazios = new ArrayList<>();
	JTextField winUserMarcouPorUltimo[] = null;
	for (int i = 0; i < winAll.length; i++) {
		String texto = winAll[i][0].getText() + winAll[i][1].getText() + winAll[i][2].getText();
		if(texto.equals("x") && indexOf(quadro.getAll()[Integer.parseInt(quadro.historicoMovimento.get(n)[1])],
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
                            this.addMovimento(winsVazios.get(j)[k]);
                            return true;
                        }
                    }
                }
            }
	return false;
    }
}
