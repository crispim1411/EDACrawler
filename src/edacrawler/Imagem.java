/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author crispim
 */
public class Imagem {
    public String alt_text;
    public URL src;
    
    public Imagem(String text, String url) throws MalformedURLException {
        alt_text = text;
        src = new URL(url);
    }
    
}
