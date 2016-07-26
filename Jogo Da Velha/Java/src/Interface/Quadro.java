/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Negocio.Bot;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 *
 * @author Pedro
 */
public class Quadro extends JPanel
{
    boolean noClicks = false;
    public List<String[]> historicoMovimento = new ArrayList<>();
    JTextField all[] = new JTextField[9];
    JTextField winAll[][] = new JTextField[8][3];
    public final Game game;
    private final Bot bot;
    public Quadro(Game g)
    {
        this.game = g;
        this.bot = new Bot(this);
        GridLayout layout = new GridLayout(3, 4);
        layout.setHgap(10);
        layout.setVgap(10);
        setLayout(layout);
        setBorder(null);
        setOpaque(false);
        for(int i = 0; i < 9; i++)
        {
            JTextField botao = new JTextField();
            botao.setEditable(false);
            botao.setBackground(Color.WHITE);
            botao.setFont(new Font("Segoe UI Light", Font.BOLD, 70));
            botao.setHorizontalAlignment(JLabel.CENTER);
            botao.setBorder(
                    javax.swing.BorderFactory.
                    createLineBorder(new java.awt.Color(204, 204, 204), 2));
            botao.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent evt)
                {
                    marcar(evt);
                }
            });
            
            all[i] = botao;
            add(botao);
        }
        winAll[0][0] = all[0];      winAll[3][0] = all[0];
        winAll[0][1] = all[1];      winAll[3][1] = all[3];
        winAll[0][2] = all[2];      winAll[3][2] = all[6];

        winAll[1][0] = all[3];      winAll[4][0] = all[1];
        winAll[1][1] = all[4];      winAll[4][1] = all[4];
        winAll[1][2] = all[5];      winAll[4][2] = all[7];

        winAll[2][0] = all[6];      winAll[5][0] = all[2];
        winAll[2][1] = all[7];      winAll[5][1] = all[5];
        winAll[2][2] = all[8];      winAll[5][2] = all[8];

        winAll[6][0] = all[0];      winAll[7][0] = all[2];
        winAll[6][1] = all[4];      winAll[7][1] = all[4];
        winAll[6][2] = all[8];      winAll[7][2] = all[6];
        //clear();
    }
    
    public void addMovimento(JTextField lugar)
    {
	//E = esquinas(like 0), C = centro reta (like 1), M = meio
	switch(indexOf(lugar))
	{
            case 0: case 2: case 6: case 8:
                this.historicoMovimento.add(new String[] {"E", indexOf(lugar)+""});
            break;
            case 4:
		this.historicoMovimento.add(new String[] {"M", indexOf(lugar)+""});
            break;
            default:
		this.historicoMovimento.add(new String[] {"C", indexOf(lugar)+""});
	}	
    }
    public int indexOf(JTextField k)
    {
        for(int i = 0; i < all.length; i++)
            if(all[i] == k)
                return i;
        return -1;
    }
    public void contraBot(boolean a)
    {
        bot.setPlayWithBot(a);
    }
    public JTextField[][] getWinAll()
    {
        return winAll;
    }
    public JTextField[] getAll()
    {
        return all;
    }
    public boolean getNoClicks()
    {
        return noClicks;
    }
    public void  clear()
    {
        for(JTextField all1 : this.all)
        {
            all1.setText("");
            all1.setForeground(Color.BLACK);
            all1.setBackground(Color.WHITE);
        }
        bot.historicoMovimento.clear();
        this.historicoMovimento.clear();
        bot.numJogadas = 0;
        game.updateRodada();
        noClicks = false;
        if(bot.isPlayWithBot() && game.rodada % 2 == 0)
        {
            bot.vezDoBot();
            gameLoop();
            game.jogador = 88;
        }
    }
    public boolean isFull()
    {
        boolean cheio = true;
        for(JTextField all1 : this.all)
        {
            if(all1.getText().length() == 0)
            {
                cheio = false;
                break;
            }
        }
        return cheio;
    }
    public boolean anyVictory()
    {
        boolean oque = false;
        for(int i = 0; i < winAll.length; i++)
        {
            String texto = winAll[i][0].getText() + winAll[i][1].getText() + winAll[i][2].getText();
            if(texto.equals("ooo") || texto.equals("xxx"))
            {
                oque = true;
                noClicks = true;
                doAnimationFocus(i);
                break;
            }
        }
        return oque;
    }
    public void marcar(MouseEvent e)
    {
        JTextField tf = (JTextField) e.getComponent();
        if(tf.getText().length() == 0 && !noClicks)
        {
            if(game.jogador == 88)
            {
                tf.setText("x");
                game.jogador = 79;
                gameLoop();
                addMovimento(tf);
                if(bot.isPlayWithBot() == true)
                {
                    if(!isFull())
                    {
                        game.jogador = 88;
                        bot.vezDoBot();
                        gameLoop();
                    }
                }
                else
                {
                    game.doAnimationSuaVez(79);
                }
            }
            else
            {
                tf.setText("o");
                game.jogador = 88;
                gameLoop();
                game.doAnimationSuaVez(88);
            }
        }
    }
    private void gameLoop()
    {
        if(!noClicks && anyVictory())
        {
            if(game.jogador == 79)
            {
                game.updateVitoriaJogador1();
                game.doAnimationWin();
            }
            else
            {
                game.updateVitoriaJogador2();
                game.doAnimationWin();
            }
        }
        else
        {
            if(isFull())
            {
                game.doAnimationWin();
            }
        }
    }
    private void doAnimationFocus(int x)
    {
        for(JTextField[] winAll1 : winAll)
        {
            for(JTextField item : winAll1)
            {
                item.setBackground(new Color(226, 225, 225));
                item.setForeground(Color.LIGHT_GRAY);
            }
        }
        for(JTextField item : winAll[x])
        {
            item.setForeground(Color.RED);
            item.setBackground(Color.white);
        }
    }
}
