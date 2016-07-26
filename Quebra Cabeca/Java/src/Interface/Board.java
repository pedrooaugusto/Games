/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import CardImagem.Colisao;
import CardImagem.ImageCard;
import ManipularImagem.ManipularImagens;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro
 */
public class Board{
       
    private int colunas = 5;
    private int linhas = 5;
    private int widthTotal;
    private int heightTotal;
    private String imgPath = null;
    private int espaco = 1;
    private int[][] quadro;
    private List<ImageCard> listaDePecas = new ArrayList<>();
    private ManipularImagens imgManipulator;
    private List<Historico> historico = new ArrayList<>();
    public Board(int linhas, int colunas, int widthTotal, int heightTotal)
    {
        this.heightTotal = heightTotal;
        this.widthTotal = widthTotal;
        this.linhas = linhas;
        this.colunas = colunas;
        try
        {
            hardReset();
        }catch(Exception ex)
        {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            JOptionPane.showMessageDialog(null, errors.toString());
        }
    }
    
    private void inserirImagens()
    {
        int cont = 0;
        for (int j = 0; j < colunas; j++) 
        {
            for (int i = 0; i < linhas; i++) 
            {
                Rectangle dim = gerarCoordenada(i, j, true);
                int id = imgManipulator.getImages().get(cont).getId();
                ImageIcon img = new ImageIcon(imgManipulator.getImages().get(cont).getImage());
                ImageCard b = new ImageCard(img, dim, id);
                listaDePecas.add(b);
                quadro[i][j] = id;
                cont++;
            }
        }
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    
    public void reset()
    {
        listaDePecas.clear();
        historico.clear();
        this.quadro = new int[linhas][colunas];
        imgManipulator.reset();
        inserirImagens();
    }
    public void hardReset()
    {
        listaDePecas.clear();
        historico.clear();
        this.quadro = new int[linhas][colunas];
        int imgWidth = (widthTotal/linhas) - espaco*2;
        int imgHeight = (heightTotal/colunas) - espaco*2;
        this.imgManipulator = new ManipularImagens(imgPath, linhas, colunas, imgWidth, imgHeight);
        inserirImagens();
        mostrarMatriz();
    }
    private ImageCard pesquisarPeca(int id)
    {
        for (ImageCard a : listaDePecas) 
            if(a.getCard().getId() == id)
                return a;
        return null;
    }
    
    private Rectangle gerarCoordenada(int i, int j, boolean espaco)
    {
        if(espaco)
            this.espaco = 1;
        else
            this.espaco = 0;
        int x = this.espaco + (i * (this.widthTotal / this.linhas));//Coordenada x
        int y = this.espaco + (j * (this.heightTotal / this.colunas));//Coordenada y
        int w = (this.widthTotal / this.linhas) - this.espaco * 2;//Largura
        int h = (this.heightTotal / this.colunas) - this.espaco * 2;//Altura
        return new Rectangle(x, y, w, h);
    }
    public void solve()
    {
       this.espaco = 0;
       hardReset();
       int cont = 0;
       Rectangle r;
        for(int j = 0; j < colunas; j++)
        {
           for(int i = 0; i < linhas; i++)
           {
               pesquisarPeca(cont).getCard().setInicial(gerarCoordenada(i, j, false));
               pesquisarPeca(cont).setBounds(gerarCoordenada(i, j, false));
               quadro[i][j] = pesquisarPeca(cont).getCard().getId();
               cont++;
           }
       }
       mostrarMatriz();
    }
    
    public ImageCard pesquisa(Rectangle objeto2)
    {
        for(ImageCard b : listaDePecas)
                if(Colisao.colidiu(b.getCard().getInicial(), objeto2, true, 0.6f))
                    return b;
        return null;
    }
    private void mostrarMatriz()
    {
        String texto = "";
        for(int i = 0; i < linhas; i++)
        {
            for(int j = 0; j < colunas; j++)
            {
                if(quadro[i][j] > 9)
                    texto += quadro[i][j]+ " | ";
                else
                    texto += "0"+quadro[i][j]+ " | ";
            }
            texto+="\n";
        }
        System.out.println(texto);
    }
    public void changePosition(int a, int b, boolean system)
    {
        int positiona[] = getIndexByValue(a);
        int positionb[] = getIndexByValue(b);
        int buffer = quadro[positiona[0]][positiona[1]];
        quadro[positiona[0]][positiona[1]] = quadro[positionb[0]][positionb[1]];
        quadro[positionb[0]][positionb[1]] = buffer;
        ImageCard c1 = pesquisarPeca(a);
        ImageCard c2 = pesquisarPeca(b);
        Point aux = c1.getCard().getPointInicial();
        c1.getCard().setPointInicial(c2.getCard().getPointInicial());
        c2.getCard().setPointInicial(aux);
        c1.setBounds(c1.getCard().getInicial());
        c2.setBounds(c2.getCard().getInicial());
        if(!system)
        {
            int values[] = {c1.getCard().getId(), c2.getCard().getId(),
                c1.getCard().getPointInicial().x, c1.getCard().getPointInicial().y,
                c2.getCard().getPointInicial().x, c2.getCard().getPointInicial().y,
            };
            addToHistory(values);
            
        }
        if(verificarWin())
            (((JpnlBoard)c1.getParent())).solve();
        mostrarMatriz();
    }
    
    private void addToHistory(int ...c)
    {
        historico.add(new Historico(c));
    }
    public void undo()
    {
        try
        {
            int last = historico.size()-1;
            changePosition(historico.get(last).getA(), historico.get(last).getB(), true);
            historico.remove(last);
        }catch(Exception e)
        {
            
        }
    }
    private int[] getIndexByValue(int value)
    {
        for(int i = 0; i < linhas; i++)
        {
            for(int j = 0; j < colunas; j++)
            {
                if(quadro[i][j] == value)
                    return new int[]{i, j};
            }  
        }
        return new int[]{-1, -1};
    }
    
    private boolean verificarWin()
    {
        int cont = 0;
        for(int j = 0; j < colunas; j++)
        {
           for(int i = 0; i < linhas; i++)
           {
                if(quadro[i][j] != cont)
                    return false;
                cont+=1;
            }
        }
        return true;
    }

    public int getColunas() {
        return colunas;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getWidthTotal() {
        return widthTotal;
    }

    public int getHeightTotal() {
        return heightTotal;
    }

    public int[][] getQuadro() {
        return quadro;
    }
    
    public List<ImageCard> getListaDePecas() {
        return listaDePecas;
    }
    
    public ManipularImagens getImgManipulator() {
        return imgManipulator;
    }
    public Dimension getSizePeca()
    {
        return new Dimension(widthTotal/linhas, heightTotal/colunas);
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }
    
}
