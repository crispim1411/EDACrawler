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

    public Payload() {
        links = new ArrayList<>();
        imgs = new ArrayList<>(); 
    }
    
    public void addToStructure(Payload pl, int level) {
        //o level é a camada de pesquisa, inicia em 1
        if (pl == null) return;
        
        level--; //level = index+1
        //pl links é adicionado a structureLinks 
        for (String link : pl.links) { 
            if (containsLink(link)) continue;
            
            ArrayList<String> aux = new ArrayList<>();
            if (level == this.structureLinks.size()){
                aux.add(link);
                this.structureLinks.add(level, aux);
            }
            else if (level > this.structureLinks.size()){
                aux.add(link);
                this.structureLinks.add(aux);
            }
            else {
                aux = this.structureLinks.get(level);
                aux.add(link);
            }
        }

        //pl links é adicionado a structureLinks
        for (ImageInfo imgInfo : pl.imgs) {
            if (containsImg(imgInfo)) continue; 
            
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
