/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
public class ImageDisplay extends JPanel {
    
    public ImageDisplay(Payload pl) {
        try {
            int i=1;
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            for (ArrayList<ArrayList<String>> array : pl.structureImgs) {
                add(new JLabel("LEVEL "+i));
                add(Box.createVerticalStrut(10));
                for (ArrayList<String> arrImage : array) {
                    Image image = null;
                    URL image_url = new URL(arrImage.get(0)); 
                    image = ImageIO.read(image_url); 
                    ImageIcon icon = new ImageIcon(image);
                    String title = arrImage.get(1);
                    if (title == "ZZ") title = "Sem título";
                    JLabel img = new JLabel(title);
                    
                    img.setIcon(icon);
                    img.setHorizontalTextPosition(JLabel.CENTER);
                    img.setVerticalTextPosition(JLabel.BOTTOM);
                    
                    add(img);
                    add(Box.createVerticalStrut(10));
                }
                i++;
                add(Box.createVerticalStrut(200));
            }
        } catch (Exception e) {
            System.out.println("Exception: "+e);
        }
    }
    
    public static void displayImages(Payload pl) throws IOException {
        try {
            if (pl.structureImgs.isEmpty() == false) {
                //Panel e Frame
                ImageDisplay imagesJPanel = new ImageDisplay(pl); //imagens adicionadas em JPanel
                JFrame frm = new JFrame();
                //imagens
                frm.add(imagesJPanel); //adiciona JPanel ao JFrame
                frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
                frm.pack(); //tamanho do JFrame de acordo com o tamanho das imagens 

                //scroll
                JScrollPane scrPane = new JScrollPane(imagesJPanel); //Um JPanel com scroll das imagens
                scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                frm.getContentPane().add(scrPane); //adiciona elemento de scroll ao JFrame
                frm.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(null, "Nenhuma imagem com este tema encontrada","Sem resultados", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e){
            System.out.println("Exception: "+e);
        }
    }

}
