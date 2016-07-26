/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CardImagem;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Pedro
 */
public class Colisao {
    public static boolean colidiu (Rectangle objeto2, Rectangle objeto1,
            boolean margemErro, float valor)
    {
        float x1 = (objeto1.x);
        float y1 = (objeto1.y);
        float w1 = (objeto1.width);
        float h1 = (objeto1.height);

        float x2 = (objeto2.x);
        float y2 = (objeto2.y);
        float w2 = (objeto2.width);
        float h2 = (objeto2.height);
        if ((x2 > x1 + w1 || y2 > y1 + h1) || (x2 + w2 < x1 || y2 + h2 < y1)) {
            return false;
        }
        if (margemErro) {
            if ((x1 < x2 && (x1 + w1 * valor) < x2) || (y1 < y2 && (y1 + h1 * valor) < y2)) {
                return false;
            } else if ((x2 + w2) < (x1 + w1 * (1 - valor)) || (y2 + h2) < (y1 + h1 * (1 - valor))) {
                return false;
            }
        }
        return true;
    }
}
