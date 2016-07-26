/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManipularImagem;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro
 */
public class ManipularImagens {
    private int quantidadeImagem;
    private final Image imagem;
    private int linhas, colunas, widthInicial, heightInicial,
            widthFinal, heightFinal;
    private List<Container> subImagens = new ArrayList<>();
    public ManipularImagens(String imgPath, int linhas, int colunas, int width, int height)
    {
        this.quantidadeImagem = linhas * colunas;
        this.heightFinal = height;
        this.widthFinal = width;
        this.linhas = linhas;
        this.colunas = colunas;
        ImageIcon img = null;
        if(imgPath == null)
            img = new ImageIcon(getClass().getResource("/ICONS/alluka.jpg"));
        else
            img = new ImageIcon(imgPath);
        widthInicial = img.getIconWidth();
        heightInicial = img.getIconHeight();
        System.out.println(widthInicial+" and "+heightInicial);
        imagem = img.getImage();
        createSubImages();
    }
    public Image getOriginalImg()
    {
        return imagem;
    }
    public static ImageIcon addSomeFilter(ImageIcon i)
    {
        BufferedImage img = toBufferedImage(i.getImage());       
        int width = img.getWidth(), height = img.getHeight();
        int marginy = (int)(height*0.07);
        int marginx = (int)(width*0.03);
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = img.getRGB(x,y);
                
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                r = (int)(r*0.4);
                r = (r < 10 ? 10 : r);
                g = (int)(g*0.4);
                g = (g < 10 ? 10 : g);
                
                b = (int)(b*0.4);
                b = (b < 10 ? 10 : b);
                //set new RGB
                p = (a<<24) | (r<<16) | (g<<8) | b;
                if(x < marginx || x > width - marginx || y < marginy || y > height - marginy)
                    img.setRGB(x, y, p);
            }
        }
        Image imagem = img;
        return new ImageIcon(imagem);
    }
    public void createSubImages()
    {
        BufferedImage img = toBufferedImage(imagem);
        int cont = 0;
        int width = img.getWidth(), height = img.getHeight();   
        for(int j = 0; j < colunas; j++)
        {
           for(int i = 0; i < linhas; i++)
           {
                int x = (i*(widthInicial/linhas));//Coordenada x
                int y = (j*(heightInicial/colunas));//Coordenada y
                int w = (this.widthInicial/linhas);//Largura
                int h = (this.heightInicial/colunas);//Altura
                subImagens.add(new Container(gerarIMG(img.getSubimage(x, y, w, h)), cont));
                cont++;
           }
        }
        Collections.shuffle(subImagens);
    }
    public void reset()
    {
        Collections.shuffle(subImagens);
    }
    private Image gerarIMG(Image inicial)
    {
        int width = (widthFinal);
        int height = (heightFinal);
        Image img2 = inicial.getScaledInstance(width, 
                height, Image.SCALE_DEFAULT);
        return img2;
    }
    public List<Container> getImages()
    {
        return subImagens;
    }
    
    /*
        Método legal copiado do stackoverflow
        não da pra pegar uam subimage de um ImageIcon ou de um
        Image, somente de um BufferedImage. E converter um dos
        dois primeiros para BufferedImage não é tão simples, usando o cast,
        por isso esse método.
    */
    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    public class Container
    {
        Image image;
        int id;
        public Container(Image image, int id) {
            this.image = image;
            this.id = id;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
        
    }
    
}
