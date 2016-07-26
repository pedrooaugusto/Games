
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Embaralhar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro
 */
public class ManipularPastas
{
    public List lerTodasImagensNaPasta()
    {
        File folderImages = new File("Imagens da Lily");
        List<String> imgNames = new ArrayList<>();
        for(String fileName : folderImages.list())
        {
            if(isImage(fileName))
                if(!fileName.contains("padrao"))
                    imgNames.add(fileName);
        }
        return imgNames;
    }
    private boolean isImage(String name)
    {
        boolean isImage = false;
        String suportFormats[] ={".jpg", ".png", ".gif"};
        for(String ext : suportFormats)
        {
            if(name.contains(ext))
            {
                isImage = true;
                break;
            }
        }
        return isImage;
    }
    private boolean pastaImagensExiste()
    {
        File f = new File("Imagens da Lily/");
        if(f.isDirectory())
        {
            for(String n : f.list())
            {
                if(n.equals("padrao.jpg"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void checarDiretorio()
    {
        String pastaNaoExiste = "Nos não achamaos uma pasta chamada ' Imagens da Lily '\n"
                              + "no seu computador. Então vamos criar uma. Isso pode demorar\n"
                              + "alguns segundos.";
        if(!pastaImagensExiste())
        {
            JOptionPane.showMessageDialog(null, pastaNaoExiste);
            try
            {
                criarPastas();
            }
            catch(Exception ex)
            {
                Logger.getLogger(ManipularPastas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String ExportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = getClass().getResourceAsStream("/Imagens/"+resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }
            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath();
            resStreamOut = new FileOutputStream(jarFolder + "\\Imagens da Lily\\"+resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }
        return jarFolder + resourceName;
    }
    private void criarPastas()
    {
        try
        {
            criarAmbiente();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Nao conseguimos criar ' Imagens da Lily '. Tente você mesmo!\n" + ex.getMessage());
            ex.printStackTrace();
        }
    }
    private void criarAmbiente() throws Exception
    {
        String nameImg[] =
        {
            "11 doctor.jpg", "11th doc and amy.jpg", "alluka.jpg",
            "answer.jpg", "dw4.png", "house i dont care.jpg",
            "i am the eggman.jpg", "just a fuck ball.jpg",
            "kill all and gon.jpg", "missy and doctor.jpg",
            "padrao.jpg", "romana and 4th doctor shada.jpg",
            "tardis interior.jpg", "romana and doctor.jpg" ,"the 2 best carry BR.jpg",
            "the best carry BR.jpg", "the doctor badass.jpg",
            "the doctor like abbey road.jpg", "you got it dude.jpg"
        };
        String ondeEstou = new File(getClass().getProtectionDomain().getCodeSource()
                .getLocation().toURI().getPath()).getParentFile().getPath();
        File novaPasta = new File(ondeEstou+"/Imagens da Lily");
        novaPasta.mkdir();
        for(String img : nameImg)
        {
            ExportResource(img);
        }
    }
    private void show(String a)
    {
        JOptionPane.showMessageDialog(null, a);
    }
}
