/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;

import java.awt.Image;
import java.io.IOException;
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
        String searchKey = "instagram"; //hardcode
        EDACrawler eda = new EDACrawler(searchKey); //instancia de crawler
        Payload pl = eda.process("http://portal2.ipt.pt/"); //links da url

        System.out.println(pl.links);   //mostra os links contidos na página inicial do ipt
        System.out.println(pl.imgs);    //mostra as imagens todas da página inicial do ipt
        //html bruto se quisermos fazer algo com ele nomeadamente pesquisar imagens por um texto
        //System.out.println(pl.html);

        //agora aqui começavam a mapear para a estruturas de dados deles, indexar, etc
        //utilizavam a estrutura de dados para ver se já visitaram ou não e visitivam só o que ainda não foi visitado
        //exemplo a visitar apenas os filhos da semente...
        int count = 0;
        for (String string : pl.links) {
            try {
                Payload pla = eda.process(string);
                System.out.println(pla.links);
                
                //isto depois tem que ser recursivo
                //retirar contador
                if (count > 5) {
                    break;  
                }
                else count++;
                
            } catch (Exception e) {
                System.out.println(string + " invalid");
            }
            
        }
          
        for (String string : pl.imgs) {
            Image image = null;
            URL url = new URL(string);
            image = ImageIO.read(url);

            JFrame frm = new JFrame();
            frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JLabel img = new JLabel();
            ImageIcon icon = new ImageIcon(image);
            img.setIcon(icon);
            frm.getContentPane().add(img);
            frm.setSize(200, 200);
            frm.setVisible(true);
         }
        
        
        /* codigo exemplo para mostrar uma imagem a partir da sua localização */
        /* isto depois não é para ser feito tudo no main */
    //        Image image = null;
    //        URL url = new URL("http://portal2.ipt.pt/img/logo.png");
    //        image = ImageIO.read(url);
    //        
    //        JFrame frm = new JFrame();
    //        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    //        JLabel img = new JLabel();
    //        ImageIcon icon = new ImageIcon(image);
    //        img.setIcon(icon);
    //        frm.getContentPane().add(img);
    //        frm.setSize(200, 200);
    //        frm.setVisible(true);
        
         
    }

}
