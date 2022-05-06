/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;

import edacrawler.models.Payload;
import edacrawler.models.ImageInfo;
import edacrawler.models.SkipList;
import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author PedroMatias & RodrigoCrispim
 */
public class EDACrawler {
    public String searchKey;
    public String url;
    public int limitLevel;
    public boolean restrictive;
    
    public EDACrawler(String url, String searchKey, int level, boolean restrictive) {
        //cria crawler com texto de busca e limite de profundidade de pesquisa
        if (searchKey != null){
            this.searchKey = removeDiacriticalMarks(searchKey).toLowerCase();    
        }
        this.url = url;
        this.limitLevel = level;
        this.restrictive = restrictive;
    }
    
    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public Payload process(String url) throws IOException {
        Payload payload = new Payload();

        //Conexão
        Connection con = Jsoup.connect(url).ignoreContentType(true).timeout(10000);
        Connection.Response resp = con.execute();
        Document doc = null;

        if (resp.statusCode() == 200) {
            doc = con.get();
            Elements links = doc.select("a");
            Iterator<Element> aux = links.iterator();

            while (aux.hasNext()) {
                String href = aux.next().attr("abs:href");
                if (href.length() < 1) continue;
               
                //se flag de dominio so adiciona se for do mesmo dominio
                if (!restrictive || href.contains(this.url)) {
                    payload.links.add(href);
                }
            }

            Elements imgs = doc.select("img");
            aux = imgs.iterator();
            Element tmp;
            while (aux.hasNext()) {
                tmp = aux.next();
                String src = tmp.attr("abs:src");
                String alt_text = tmp.attr("alt");
                
                if (src.length() > 1) {
                    if (searchKey==null || removeDiacriticalMarks(alt_text).toLowerCase().contains(searchKey)) {

                        if (!"".equals(alt_text) && alt_text.length()>1) 
                            alt_text = alt_text.substring(0, 1).toUpperCase() + alt_text.substring(1);
                        else if (alt_text.length() == 1) 
                            alt_text = alt_text.toUpperCase();
                        else alt_text = "ZZ"; //forçar ir pro fim da lista
                        
                        ImageInfo imgInfo = new ImageInfo(src, alt_text);
                        if (!payload.imgs.contains(imgInfo)) 
                            payload.imgs.add(imgInfo);
                    }
                }
            }
        }
        else {
            Logger.getLogger(Interface.class.getName()).log(Level.WARNING, null, resp.statusCode());
        }

        return payload;
    }
    
    public ArrayList recursiveSearch() {
        try {
            if (this.url != null) {
                SkipList dataStructure = new SkipList(20);
                SkipList visitedLinks = new SkipList(10);
                
                Payload pl = this.process(url);
                visitedLinks.Insert(url, url);
                dataStructure.InsertMany(pl.imgs);
                recursiveSearch(dataStructure, visitedLinks, pl.links, 1);
                
                return dataStructure.ToList();
            }
        }
        catch (IOException e) {
            Logger.getLogger(Interface.class.getName()).log(Level.WARNING, null, e.getMessage());
        }
        
        return null;
    }
    
    public void recursiveSearch(
        SkipList dataStructure, 
        SkipList visitedLinks,
        ArrayList<String> linksToVisit,
        int level) throws IOException {
        
        if (level < this.limitLevel) {
            String nextUrl;
            Iterator<String> linkIter = linksToVisit.iterator();    
            ArrayList<String> nextLevelLinks = new ArrayList();
            
            while (linkIter.hasNext()) {
                nextUrl = linkIter.next();
                
                if (!visitedLinks.Contains(nextUrl)) {
                    Payload tmp = this.process(nextUrl); 
                    visitedLinks.Insert(nextUrl, nextUrl);
                
                    dataStructure.InsertMany(tmp.imgs);
                    
                    for (String link : tmp.links) { 
                        if (!nextLevelLinks.contains(link) && 
                            !visitedLinks.Contains(link))
                            nextLevelLinks.add(link);
                    }
                }
            }
            recursiveSearch(dataStructure, visitedLinks, nextLevelLinks, level+1); //entra no proximo nivel
            
        }
    }
}
