/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edacrawler;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PedroMatias & RodrigoCrispim
 */
public class Payload {
    public ArrayList<ArrayList<ImageInfo>> structureImgs = new ArrayList<>();
    public ArrayList<ArrayList<String>> structureLinks = new ArrayList<>();
    public ArrayList<String> links;
    public ArrayList<ImageInfo> imgs;
    public String html = "";

    public Payload() {
        links = new ArrayList<>();
        imgs = new ArrayList<>(); //imagem: [[src1,alt1], [src2,alt2], ... ]
    }
    
    public void addToStructure(Payload pl, int level) {
        try {
            //o level é a camada de pesquisa, inicia em 1
            level--; //level = index+1
            if (pl != null){
                for (String link : pl.links) { //pl links é adicionado a structureLinks 
                    if (containsLink(link)==false) {
                        if (level == this.structureLinks.size()){
                            ArrayList<String> aux = new ArrayList<>();
                            aux.add(link);
                            this.structureLinks.add(level, aux);
                        }
                        else if (level > this.structureLinks.size()){
                            ArrayList<String> aux = new ArrayList<>();
                            aux.add(link);
                            this.structureLinks.add(aux);
                        }
                        else {
                            ArrayList<String> aux = new ArrayList<>();
                            aux = this.structureLinks.get(level);
                            aux.add(link);
                        }
                    }
                }

                for (ImageInfo imgInfo : pl.imgs) {//pl links é adicionado a structureLinks
                    if (containsImg(imgInfo) == false) {
                        ArrayList<ImageInfo> aux = new ArrayList<>();
                        if (level == this.structureImgs.size()){
                            aux.add(imgInfo);
                            this.structureImgs.add(level, aux);
                        }
                        else if (level > this.structureImgs.size()){
                            aux.add(imgInfo);
                            this.structureImgs.add(aux);
                        }
                        else {
                            aux = this.structureImgs.get(level);
                            aux.add(imgInfo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    // trocar por uma arvore?
    public void insertionSort() {
        try {
            int i,j;

            for (ArrayList<ImageInfo> arrLevel : this.structureImgs) {
                for (i = 1; i < arrLevel.size(); i++) {
                    ImageInfo imgInfo= arrLevel.get(i);
                    String string = imgInfo.info;
                    j = i;
                    while((j > 0) && (arrLevel.get(j - 1).info.compareTo(string))>0) {
                        arrLevel.set(j,arrLevel.get(j - 1));
                        j--;
                    }
                    arrLevel.set(j,imgInfo);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
    
    public boolean containsImg(ImageInfo imgInfo){
        try{
            for (int i=0; i<this.structureImgs.size(); i++){
                if (this.structureImgs.get(i).contains(imgInfo)) {
                    return true;
                }
            } 
        } catch (Exception e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    public boolean containsLink(String url) {
        try{
            for (int i=0;i<this.structureLinks.size();i++) {
                if (this.structureLinks.get(i).contains(url)) {
                    return true;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }   
}
