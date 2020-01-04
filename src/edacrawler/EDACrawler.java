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
 * @author PedroMatias & RodrigoCrispim
 */
public class EDACrawler {
    public String searchKey;
    public int limitLevel;
    
    public EDACrawler(String string, int level) throws IOException {
        //cria crawler com texto de busca e limite de profundidade de pesquisa
        if (string != null){
            this.searchKey = string.toLowerCase();    
        }

        this.limitLevel = level;
    }

    public Payload process(String url, String domain, boolean ifDomain) throws IOException {
        //retorna as imagens e links de um link dado por parâmetro
        //caso setado retorna os links que fazem parte do dominio
        try {
            Payload payload = new Payload();

            if (!url.endsWith("/")) {
                url += "/";
            }

            Document doc = Jsoup.connect(url).ignoreContentType(true).get();

            Elements links = doc.select("a");
            Iterator<Element> aux = links.iterator();

            while (aux.hasNext()) {
                String href = aux.next().attr("abs:href");
                if (href.length() > 1) {
                    if (ifDomain) { //se flag de dominio so adiciona se for do mesmo dominio
                        if (href.contains(domain)) {
                            payload.links.add(href);  
                        }
                    }
                    else {
                        payload.links.add(href);
                    }
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
                        //armazenar imagens numa estrutura
                    }
                }
            }

            payload.html = doc.html();

            return payload;
            
        } catch (IOException e) {
            System.out.println("IOException Process: " + e);
          return null;
        } catch (Exception e) {
            System.out.println("Exception is: " + e);
          return null;
        }
    }
    
    public Payload recursiveSearch(String url, boolean ifDomain) throws IOException {
        //Retorna Payload da url origem e das urls filho 
        //se setado entra apenas nas url de mesmo dominio
        if (url != null) {
            Payload pl = null;
            return recursiveSearch(pl, url, url, ifDomain, 1);
        }
        return null;
    }
    
    public Payload recursiveSearch(Payload pl, String url, String domain, boolean ifDomain, int level) throws IOException {
        try {
            System.out.println("current level: "+level+" limit: "+limitLevel);
            if (pl == null) { //Payload vazio               
                pl = this.process(url, domain, ifDomain); //carrega links level 0
                pl.addToStructure(pl, level); //links do nivel 0          
            }

            //if (pl.structureLinks.size() > level) { 
            if (level < this.limitLevel) {

                String nextUrl;
                Iterator<String> aux = pl.structureLinks.get(level-1).iterator(); //links do level iteravel
                //System.out.println("level: "+level+": estrutura obtida: "+pl.structureLinks.get(level));

                while (aux.hasNext()) { //se há links a iterar
                    nextUrl = aux.next(); //pega o proximo
                    //System.out.println(nextUrl);

                    if (contains(pl.structureLinks, nextUrl, level) == false) { //se o link não foi visitado
                        Payload tmp = this.process(nextUrl, domain, ifDomain); //obtem payload
                        if (tmp != null){                        
                            pl.addToStructure(tmp, level+1);
                            //System.out.println("structure updated: "+pl.structureLinks);
                            recursiveSearch(pl, nextUrl, domain,ifDomain, level+1); //entra no proximo nivel
                        }
                    }
                }
            }

            return pl;
            
        } catch (IOException e) {
            System.out.println("IOException Payload: "+e);
            return null;
        }
        catch (Exception e) {
            System.out.println("Exception is: " + e);
          return null;
        }
    }
    
    public boolean contains(ArrayList<ArrayList<String>> structure, String url, int level){
        //System.out.println("level: "+level+", size: "+structure.size());
        for (int i=level-1; i>0; i--){
            if (structure.get(i).contains(url)) {
                return true;
            }
        } 
        return false;
    }

}
