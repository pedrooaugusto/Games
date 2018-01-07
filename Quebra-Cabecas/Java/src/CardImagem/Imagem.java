/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CardImagem;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Pedro
 */
public class Imagem {
    private final ImageIcon imagemOriginal;
    private final ImageIcon imagemSombreada;
    private final ImageIcon imagemPequena;

    public Imagem(ImageIcon imagemOriginal) {
        this.imagemOriginal = imagemOriginal;
        this.imagemSombreada = ManipularImagem.ManipularImagens.addSomeFilter(imagemOriginal);
        this.imagemPequena = toScale(imagemOriginal, 0.7f);
    }
    private ImageIcon toScale(ImageIcon inicial, float scale)
    {
        ImageIcon d = (ImageIcon) inicial;
        int width = (int)((d.getIconWidth()*scale));
        int height = (int)((d.getIconHeight()*scale));
        ImageIcon img2 = new ImageIcon(d.getImage().getScaledInstance(width, 
                height, Image.SCALE_DEFAULT));
        return img2;
    }

    public ImageIcon getImagemOriginal() {
        return imagemOriginal;
    }

    public ImageIcon getImagemSombreada() {
        return imagemSombreada;
    }

    public ImageIcon getImagemPequena() {
        return imagemPequena;
    }
}
