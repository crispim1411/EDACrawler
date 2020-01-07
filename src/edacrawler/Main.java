/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;

//import static edacrawler.Image_rename.display_image;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static edacrawler.ImageToDisplay.displayImages;
import java.util.ArrayList;
/**
 *
 * @author pedrodias & paulosantos
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String searchKey = null; //todas as imagens
        EDACrawler eda = new EDACrawler(searchKey, 1); //instancia de crawler
        Payload pl = eda.recursiveSearch("http://portal2.ipt.pt/", true); //links da url

        displayImages(pl);     
    }
}
