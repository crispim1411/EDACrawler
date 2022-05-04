/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;

import java.io.IOException;
import static edacrawler.ImagePanel.displayImages;
import edacrawler.models.SkipList;
import edacrawler.models.SkipList.Node;
/**
 *
 * @author pedrodias & paulosantos
 */
public class Main {

    public static void main(String[] args) {
       /* String searchKey = null; //todas as imagens
        EDACrawler eda = new EDACrawler("http://portal2.ipt.pt/", searchKey, 1); //instancia de crawler
        Payload pl = eda.recursiveSearch(true); //links da url

        displayImages(pl);  */
        SkipList sl = new SkipList();
        sl.Display();
        sl.Insert("ca", "4");
        sl.Insert("ac", "9");
        sl.Insert("ba", "9");
        sl.Insert("bb", "9");
        sl.Insert("ca", "9");
        sl.Insert("aa", "9");
        sl.Insert("cc", "9");
        sl.Insert("aa", "9");
        sl.Insert("ad", "9");
        sl.Insert("da", "9");
        sl.Insert("dd", "9");
        sl.Insert("db", "9");
        sl.Insert("bd", "9");
        sl.Insert("cd", "9");
        sl.Insert("dc", "9");
        sl.Insert("aa", "9");
        sl.Display();
        System.out.println(sl.Contains("dd"));
    }
}
