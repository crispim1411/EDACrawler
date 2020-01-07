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
import static sun.jvm.hotspot.HelloWorld.e;

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
            this.searchKey = removeDiacriticalMarks(string).toLowerCase();    
        }
        this.limitLevel = level;
    }
    
    public static String removeDiacriticalMarks(String string) {
        try{
            return Normalizer.normalize(string, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        } catch (Exception e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public Payload process(String url, String domain, boolean ifDomain) throws IOException {
        //retorna as imagens e links de um link dado por parâmetro
        //caso setado retorna os links que fazem parte do dominio
        try {
            Payload payload = new Payload();

            if (!url.endsWith("/")) {
                url += "/";
            }

            //Document doc = Jsoup.connect(url).timeout(10000).ignoreContentType(true).get();
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
                    String alt_text = tmp.attr("alt");
                    if (src.length() > 1) {
                        if (searchKey==null || removeDiacriticalMarks(alt_text).toLowerCase().contains(searchKey)) {
                            ArrayList<String> arrImage = new ArrayList<>();
                            arrImage.add(src);

                            if (alt_text != "" && alt_text.length()>1) alt_text = alt_text.substring(0, 1).toUpperCase() + alt_text.substring(1);
                            else if (alt_text.length() == 1) alt_text = alt_text.toUpperCase();
                            else alt_text = "ZZ"; //forçar ir pro fim da lista

                            arrImage.add(alt_text);
                            payload.imgs.add(arrImage); //cada imagem é um array [url, alt]
                        }
                    }
                }
            }
            else {
                Logger.getLogger(Interface.class.getName()).log(Level.WARNING, null, resp.statusCode());
            }
            payload.html = doc.html();

            return payload;
            
        } catch (IOException e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
          return null;
        } catch (Exception e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
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
            if (pl == null) { //Payload vazio               
                pl = this.process(url, domain, ifDomain); //carrega links level 1
                pl.addToStructure(pl, level); //links do nivel 1          
            }

            //se pl possui size >= level possui iteraveis, se level limite ainda não alcançado
            if (pl.structureLinks.size() >= level && level < this.limitLevel) { 
            //if (level < this.limitLevel) {

                String nextUrl;
                Iterator<String> aux = pl.structureLinks.get(level-1).iterator(); //links do level iteravel
                //System.out.println("level: "+level+": estrutura obtida: "+pl.structureLinks.get(level));
                
                while (aux.hasNext()) { //se há links a iterar
                    nextUrl = aux.next(); //pega o proximo
                    //System.out.println(nextUrl);
                    //System.out.println("analisando: "+nextUrl);
                    if (visited(pl.structureLinks, nextUrl, level) == false) { //se o link não foi visitado
                        //System.out.println("visitando: "+nextUrl);
                        Payload tmp = this.process(nextUrl, domain, ifDomain); //obtem payload
                        //System.out.println("level "+(level+1)+ " pl obtido: "+tmp.links);
                        if (tmp != null){                        
                            pl.addToStructure(tmp, level+1);
                            //System.out.println("structure updated: "+pl.structureLinks);
                            recursiveSearch(pl, nextUrl, domain,ifDomain, level+1); //entra no proximo nivel
                        }
                    }
                    //else { System.out.println("\nja visitado: "+nextUrl);}
                }
                //System.out.println("\nsem mais iteraveis\n\n");
            }
            pl.insertionSort();
            return pl;
            
        } catch (IOException e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        catch (Exception e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
          return null;
        }
    }
    
    public boolean visited(ArrayList<ArrayList<String>> structure, String url, int level){
        //System.out.println("level: "+level+", size: "+structure.size());
        try{
            level--;
            for (int i=0; i<structure.size(); i++){
                //System.out.println("verificando se "+url+" lvl"+level+" existe no get "+i);
                if (structure.get(i).contains(url) && i!=level) {
                    //System.out.println("existe no lvl"+(i+1) +"get "+i);
                    return true;
                }
            } 
        } catch (Exception e){
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

}
