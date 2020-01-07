/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;
import static edacrawler.EDACrawler.removeDiacriticalMarks;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javax.swing.JSeparator;

/**
 *
 * @author PedroMatias & RodrigoCrispim
 */
public class ImageToDisplay extends JPanel {
    
    public ImageToDisplay(Payload pl) {
        try {

            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            for (ArrayList<ArrayList<String>> array : pl.structureImgs) {
                //add(new JLabel("LEVEL "+i));
                //add(Box.createVerticalStrut(10));
                for (ArrayList<String> arrImage : array) {
                    //String url, String title -> JLabel img
                    
                    JLabel img = addToPanel(arrImage.get(0),null, arrImage.get(1));
                    add(img);
                    add(Box.createVerticalStrut(10));
                }

            }
        } catch (Exception e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private static JLabel addToPanel(String url,File imgFile, String title) {
       
        try {
            Image image = null;
            
            
            if (imgFile != null) {
                image = ImageIO.read(imgFile); //imagem por arquivo
            }
            else if (url != null){
                URL image_url = new URL(url); //imagem por url
                image = ImageIO.read(image_url);
            }
            else throw new Exception("Image without source");
            
            ImageIcon icon = new ImageIcon(image);
            if ("ZZ".equals(title)) title = "untitled";
            JLabel img = new JLabel(title);

            img.setIcon(icon);
            img.setHorizontalTextPosition(JLabel.CENTER);
            img.setVerticalTextPosition(JLabel.BOTTOM);
            
            return img;
        }catch (MalformedURLException e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
            return null;
        } catch (Exception e){
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        
    }
                    
    
    public static void displayImages(Payload pl) throws IOException {
        try {
            if (pl.structureImgs.isEmpty() == false) {
   
                //Panel e Frame
                ImageToDisplay imgsJPanel = new ImageToDisplay(pl); //imagens adicionadas em JPanel
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
            
        } catch (Exception e){
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public static void displayImagesByFiles(File[] files){
        try {
            JFrame frm = new JFrame();
            JPanel panel = new JPanel();
            //lê a imagem do ficheiro escolhido
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            for (File getFileImg : files){

                //File getFileImg = openFileChooser.getSelectedFile();
                String imgTitle = getFileImg.getName();
                JLabel lbl = addToPanel(null, getFileImg, imgTitle);
                
                panel.add(lbl);
                panel.add(Box.createHorizontalStrut(10));
                //frm.getContentPane().add(lbl);

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
            
        } catch (Exception e){
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public static void imagesToSave(Payload pl, File dir){
        try{
            int i = 1;
            for(ArrayList<ArrayList<String>> array : pl.structureImgs){
                for(ArrayList<String> arrImage : array){
                    //abre a janela para podermos guardar uma imagem
                    //int returnValue = saveFileChooser.showSaveDialog(this);
                    //condição para ver se o JFileChooser aprova o returnValue
                    //if(returnValue == JFileChooser.APPROVE_OPTION){
                    if(true){
                        //System.out.println("return value: "+returnValue);
                        URL image_url = null;
                        //vai buscar o URL das imagens contidas no payload pl
                        image_url = new URL (arrImage.get(0)); 
                        //converte o URL em imagem
                        BufferedImage img = ImageIO.read(image_url);

                        //ImageIO.write((RenderedImage) img, "png", saveFileChooser.getSelectedFile());

                        //escreve as imagens 
                        //Salva as imagens automaticamente usando texto Alt como nome do arquivo
                        String imageName;
                        if ("ZZ".equals(arrImage.get(1)))imageName = "untitled".concat(Integer.toString(i++));
                        else imageName = removeDiacriticalMarks(arrImage.get(1).replace(" ", "_").replace("/","_").concat(".png"));

                        String imagePath = dir.getAbsolutePath().concat("/").concat(imageName);
                        ImageIO.write(img, "png", new File(imagePath));
                    }  
                    //ao clicar em cancel termina o ciclo for
                    else break;                     
                }
            }
        } catch (Exception e){
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
