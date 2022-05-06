/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;
import static edacrawler.EDACrawler.removeDiacriticalMarks;
import edacrawler.models.SkipList.Node;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author PedroMatias & RodrigoCrispim
 */
public class ImagePanel extends JPanel {
    
    public ImagePanel(ArrayList<Node> arr) {
        // carrega as imagens no painel
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        for (Node imgInfo : arr) {
            Image image = LoadImageFromUrl(imgInfo.value);
            if (image == null) continue;
            
            JLabel img = addToPanel(image, imgInfo.key);
            if (img != null) {
                add(img);
                add(Box.createVerticalStrut(10));
            }
        }
    }
    
    private static Image LoadImageFromUrl(String url) {
        try {
            URL image_url = new URL(url); 
            return ImageIO.read(image_url);
        }
        catch (IOException e){
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, "Error reading URL: {0}", url);
        }
        return null;
    }
    
    private static Image LoadImageFromFile(File imgFile) {
        try {
            return ImageIO.read(imgFile);
        }
        catch (IOException e){
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, "Error reading file: {0}", imgFile.toString());
        }
        return null;
    }
    
    private static JLabel addToPanel(Image image, String title) {
        if (image != null) {
            if ("ZZ".equals(title)) title = "untitled";
            JLabel img = new JLabel(title);
            
            ImageIcon icon = new ImageIcon(image);
            img.setIcon(icon);
            img.setHorizontalTextPosition(JLabel.CENTER);
            img.setVerticalTextPosition(JLabel.BOTTOM);
            return img;
        }
        return null;
    }
                    
    
    public static void displayImages(ArrayList data) {
        if (data != null && data.isEmpty() == false) {

            //Panel e Frame
            ImagePanel imgsJPanel = new ImagePanel(data); //imagens adicionadas em JPanel
            JFrame frm = new JFrame();
            //imagens
            frm.add(imgsJPanel); //adiciona JPanel ao JFrame
            frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
            frm.pack(); //tamanho do JFrame de acordo com o tamanho das imagens 

            //scroll
            JScrollPane scrPane = new JScrollPane(imgsJPanel); //Um JPanel com scroll das imagens
            scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            frm.getContentPane().add(scrPane); //adiciona elemento de scroll ao JFrame
            frm.setVisible(true);
        }
        else {
            JOptionPane.showMessageDialog(null, "Nenhuma imagem com este tema encontrada","Sem resultados", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    public static void displayImagesByFiles(File[] files){
        JFrame frm = new JFrame();
        JPanel panel = new JPanel();
        //lê a imagem do ficheiro escolhido
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        for (File getFileImg : files){

            //File getFileImg = openFileChooser.getSelectedFile();
            String imgTitle = getFileImg.getName();
            Image image = LoadImageFromFile(getFileImg);
            if (image == null) continue;

            JLabel lbl = addToPanel(image, imgTitle);
            panel.add(lbl);
            panel.add(Box.createHorizontalStrut(10));
        }
        frm.add(panel);
        frm.pack();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

        //scroll
        JScrollPane scrPane = new JScrollPane(panel); //Um JPanel com scroll das imagens
        scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frm.getContentPane().add(scrPane); //adiciona elemento de scroll ao JFrame
        frm.setVisible(true);
    }
    
    public static void imagesToSave(ArrayList<Node> arr, File dir){
        try{
            int i = 1;
            
            for(Node imgInfo : arr){
                URL image_url = null;
                //vai buscar o URL das imagens contidas no payload pl
                image_url = new URL(imgInfo.key); 
                //converte o URL em imagem
                BufferedImage img = ImageIO.read(image_url);

                //escreve as imagens 
                //Salva as imagens automaticamente usando texto Alt como nome do arquivo
                String imageName;
                if ("ZZ".equals(imgInfo.value))imageName = "untitled".concat(Integer.toString(i++));
                else imageName = removeDiacriticalMarks(
                    imgInfo.value
                            .replace(" ", "_")
                            .replace("/","_")
                            .replace(",","_")
                            .replace(".","_")
                            .replace(":","_")
                            .replace(";","_")
                            .replace("__","_")
                            .concat(".png")
                    );

                String imagePath = dir.getAbsolutePath().concat("/").concat(imageName);
                ImageIO.write(img, "png", new File(imagePath));
            }
        } catch (Exception e){
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, "Saving images", e);
        }
    }

}
