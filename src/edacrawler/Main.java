/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;

import static edacrawler.ImageCrawler.display_image;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author pedrodias & paulosantos
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String domain = "http://portal2.ipt.pt/";
        String searchKey = "facebook"; //todas as imagens
        EDACrawler eda = new EDACrawler(searchKey, 3); //instancia de crawler
        Payload pl = eda.recursiveSearch(domain, false);

        //System.out.println(pl.links);   //mostra os links contidos na página inicial do ipt
        //System.out.println(pl.imgs);    //mostra as imagens todas da página inicial do ipt

        JFrame frm = new JFrame();
        for (String string : pl.imgs) {
            //display_image(string, frm);      
        }
        
    }

}
