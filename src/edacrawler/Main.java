/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;

import java.io.IOException;
import static edacrawler.ImageToDisplay.displayImages;
/**
 *
 * @author pedrodias & paulosantos
 */
public class Main {

    public static void main(String[] args) {
        String searchKey = null; //todas as imagens
        EDACrawler eda = new EDACrawler("http://portal2.ipt.pt/", searchKey, 1); //instancia de crawler
        Payload pl = eda.recursiveSearch(true); //links da url

        displayImages(pl);  
    }
}
