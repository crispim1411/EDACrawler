/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author crispim
 */
public class ImageCrawler {
    public String alt_text;
    public URL src;
    
    public ImageCrawler(String text, String url) throws MalformedURLException {
        alt_text = text;
        src = new URL(url);
    }
    
    public static void display_image(String url) throws MalformedURLException, IOException {
        try {
            Image image = null;
            URL image_url = new URL(url);
            image = ImageIO.read(image_url);

            JFrame frm = new JFrame();
            frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JLabel img = new JLabel();
            ImageIcon icon = new ImageIcon(image);
            img.setIcon(icon);
            frm.getContentPane().add(img);
            frm.setSize(200, 200);
            frm.setVisible(true);
            
        } catch (HeadlessException | IOException e) {
            System.out.println(url + " invalid");
        } catch (Exception e) {
            System.out.println("Exception: "+e);
        }
    }
}
