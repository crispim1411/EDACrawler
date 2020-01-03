/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;

import static edacrawler.Image_rename.display_image;
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
 * @author pedrodias & paulosantos
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String searchKey = null; //todas as imagens
        EDACrawler eda = new EDACrawler(searchKey, 1); //instancia de crawler
        Payload pl = eda.recursiveSearch("http://portal2.ipt.pt/"); //links da url

        //System.out.println(pl.links);   //mostra os links contidos na página inicial do ipt
        //System.out.println(pl.imgs);    //mostra as imagens todas da página inicial do ipt

        //Payload pl = eda.recursiveSearch(url);
        //System.out.println("pl: "+pl.links);

        for (String string : pl.imgs) {
            display_image(string);      
        }

        //html bruto se quisermos fazer algo com ele nomeadamente pesquisar imagens por um texto
        //System.out.println(pl.html);

        //agora aqui começavam a mapear para a estruturas de dados deles, indexar, etc
        //utilizavam a estrutura de dados para ver se já visitaram ou não e visitivam só o que ainda não foi visitado
        //exemplo a visitar apenas os filhos da semente...
//        int count = 0;
//        for (String string : pl.links) {
//            try {
//                Payload pla = eda.process(string);
//                System.out.println(pla.links);
//                
//                //isto depois tem que ser recursivo
//                //retirar contador
//                if (count > 5) {
//                    break;  
//                }
//                else count++;
//                
//            } catch (Exception e) {
//                System.out.println(string + " invalid");
//            }
//            
//        }
          
//        for (String string : pl.imgs) {
//            display_image(string);
//        }      
         
    }
    
//    private static void display_image(String url) throws MalformedURLException, IOException {
//        try {
//            Image image = null;
//            URL image_url = new URL(url);
//            image = ImageIO.read(image_url);
//
//            JFrame frm = new JFrame();
//            frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            JLabel img = new JLabel();
//            ImageIcon icon = new ImageIcon(image);
//            img.setIcon(icon);
//            frm.getContentPane().add(img);
//            frm.setSize(200, 200);
//            frm.setVisible(true);
//        } catch (HeadlessException | IOException e) {
//            System.out.println(url + " invalid");
//        }
//    }

}
