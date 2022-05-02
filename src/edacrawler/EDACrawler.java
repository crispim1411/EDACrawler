/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;

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
    
    public EDACrawler(String url, String searchKey, int level) {
        //cria crawler com texto de busca e limite de profundidade de pesquisa
        if (searchKey != null){
            this.searchKey = removeDiacriticalMarks(searchKey).toLowerCase();    
        }
        this.url = url;
        this.limitLevel = level;
    }
    
    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public Payload process(String url, String domain, boolean restrictive) throws IOException {
        //retorna as imagens e links de um site 
        //caso restrictive True entra apenas nos links que fazem parte do dominio
        Payload payload = new Payload();

        if (!url.endsWith("/")) {
            url += "/";
        }
        
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
                if (!restrictive || href.contains(domain)) {
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
    
    public Payload recursiveSearch(boolean ifDomain) {
        //Retorna Payload da url origem e das urls filho 
        //se setado entra apenas nas url de mesmo dominio
        try {
            if (this.url != null) {
                Payload pl = null;
                return recursiveSearch(pl, this.url, this.url, ifDomain, 1);
            }
        }
        catch (IOException e) {
            Logger.getLogger(Interface.class.getName()).log(Level.WARNING, null, e.getMessage());
        }
        
        return null;
    }
    
    public Payload recursiveSearch(Payload pl, String url, String domain, boolean ifDomain, int level) throws IOException {           
        //alterar esse trecho
        if (pl == null) { //Payload vazio               
            pl = this.process(url, domain, ifDomain); //carrega links level 1
            pl.addToStructure(pl, level); //links do nivel 1          
        }

        //se pl possui size >= level possui iteraveis, se level limite ainda não alcançado
        if (pl.structureLinks.size() >= level && level < this.limitLevel) {
            String nextUrl;
            Iterator<String> aux = pl.structureLinks.get(level-1).iterator(); //links do level iteravel

            while (aux.hasNext()) { //se há links a iterar
                nextUrl = aux.next(); //pega o proximo
                if (visited(pl.structureLinks, nextUrl, level) == false) { //se o link não foi visitado
                    Payload tmp = this.process(nextUrl, domain, ifDomain); //obtem payload
                    if (tmp != null){                        
                        pl.addToStructure(tmp, level+1);
                        recursiveSearch(pl, nextUrl, domain,ifDomain, level+1); //entra no proximo nivel
                    }
                }
            }
        }
        pl.insertionSort();
        return pl;
    }
    
    public boolean visited(ArrayList<ArrayList<String>> structure, String url, int level){
        level--;
        for (int i=0; i<structure.size(); i++){
            if (structure.get(i).contains(url) && i!=level) {
                return true;
            }
        } 
        return false;
    }

}
