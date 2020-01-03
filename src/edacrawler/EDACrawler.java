/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author pedrodias & paulosantos
 */
public class EDACrawler {
    public String searchKey;
    public int limitLevel;
    
    public EDACrawler(String string, int limitLevel) throws IOException {
        if (string != null){
            this.searchKey = string.toLowerCase();    
        }
        this.limitLevel = limitLevel;
    }

    public Payload process(String url) throws IOException {
        Payload payload = new Payload();

        if (!url.endsWith("/")) {
            url += "/";
        }

        Document doc = Jsoup.connect(url).ignoreContentType(true).get();;

        Elements links = doc.select("a");
        Iterator<Element> aux = links.iterator();

        while (aux.hasNext()) {
            String href = aux.next().attr("abs:href");
            if (href.length() > 1) {
                payload.links.add(href);
            }
        }
        
        Elements imgs = doc.select("img");
        aux = imgs.iterator();
        Element tmp;
        while (aux.hasNext()) {
            tmp = aux.next();
            String src = tmp.attr("abs:src");
            String alt_text = tmp.attr("alt").toLowerCase();
            if (src.length() > 1) {
                if (searchKey==null || alt_text.contains(searchKey)) {
                    payload.imgs.add(src);
                    //Image_rename img = new Image_rename(alt_text, src);//armazenar imagens numa estrutura
                }
            }
        }

        payload.html = doc.html();

        return payload;
    }
    
    public Payload recursiveSearch(String url) throws IOException {
        if (url != null) {
            Payload pl = null;
            ArrayList<String> visited = new ArrayList<>();
            return recursiveSearch(pl,url,visited, 0);
        }
        return null;
    }
    
    public Payload recursiveSearch(Payload pl, String url, ArrayList visited, int level) throws IOException {
        if (level < limitLevel) {
            //level++;
            if (pl == null) { //Payload vazio
                //System.out.println("empty");
                //System.out.println(pl.structureLinks);
                //System.out.println("\n");
                
                pl = this.process(url); //carrega links level 0
                pl.addToStructure(pl, level); //links do nivel 0
                
               //System.out.println("obtido level 0");
               //printStructure(pl.structureLinks);
               //System.out.println("\n\n");
                
            }
            
            String nextUrl;
            Iterator<String> aux = pl.structureLinks.get(level).iterator(); //links do level iteravel
            //System.out.println("level: "+level+": estrutura obtida: "+pl.structureLinks.get(level));
            
            while (aux.hasNext()) { //se há links a iterar
                nextUrl = aux.next(); //pega o proximo
                //System.out.println(nextUrl);
                
                if (contains(pl.structureLinks, nextUrl, level) == false) { //se o link não foi visitado
                    Payload tmp = this.process(nextUrl); //obtem payload
                    pl.addToStructure(tmp, level+1);
                    //System.out.println("structure updated: "+pl.structureLinks);
                    
                    recursiveSearch(pl, nextUrl, visited, level+1); //entra no proximo nivel
                }
                //para cada link do level 
                //fazer payload com level+1
//                for (int i=0;i<pl.structureLinks.size();i++){
//                    System.out.println("Se já foi visitado no level: "+level);  
//                }
            }
            //level++;
            //adicionar a estrutura e percorrer
            //itens nivel 0 depois itens nivel 1 etc
//            for (String string : pl.links) { //para cada link do payload
//                if (visited.contains(string) == false) { //se o link não foi visitado
//                    
//                    Payload plToMerge = this.process(string); //carrega payload do link
//                    visited.add(string); //adiciona aos visitados
//                    //System.out.println(string+": "+plToMerge.links); 
//                    
//                    //pl.merge(plToMerge); //junta os payloads
//                    pl.addToStructure(pl, level);
//                    
//                    //System.out.println("level: "+level); 
//
//                    recursiveSearch(pl, string, visited, level); //entra no proximo nivel
//                }
//            }
        }
        
        return pl;
    }
    
    public boolean contains(ArrayList<ArrayList<String>> structure, String url, int level){
        //System.out.println("level: "+level+", size: "+structure.size());
        for (int i=level-1; i>0; i--){
            //System.out.println("Se já foi visitado no level: "+i);
            if (structure.get(i).contains(url)) {
                //System.out.println("Level: "+i+" contem "+url);
                return true;
            }
        } 
        return false;
    }
    
    public void printStructure(ArrayList<ArrayList<String>> structure) {
        if (structure != null) {
            System.out.println("level: "+structure.size());
            for (ArrayList<String> array : structure) {
                System.out.println("num itens: "+array.size());
                for (String string : array) {
                    System.out.println(string);
                }
            }
        }
    }

}
