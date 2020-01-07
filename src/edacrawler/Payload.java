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
    public ArrayList<ArrayList<ArrayList<String>>> structureImgs = new ArrayList<>();
    public ArrayList<ArrayList<String>> structureLinks = new ArrayList<>();
    public ArrayList<String> links;
    public ArrayList<ArrayList<String>> imgs;
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
                for (String string : pl.links) { //pl links é adicionado a structureLinks 
                    if (containsLink(string)==false) {
                        if (level == this.structureLinks.size()){
                            ArrayList<String> aux = new ArrayList<>();
                            aux.add(string);
                            this.structureLinks.add(level, aux);
                        }
                        else if (level > this.structureLinks.size()){
                            ArrayList<String> aux = new ArrayList<>();
                            aux.add(string);
                            this.structureLinks.add(aux);
                        }
                        else {
                            ArrayList<String> aux = new ArrayList<>();
                            aux = this.structureLinks.get(level);
                            aux.add(string);
                        }
                    }
                }

                for (ArrayList<String> arrImage : pl.imgs) {//pl links é adicionado a structureLinks
                    if (containsImg(arrImage) == false) {
                        if (level == this.structureImgs.size()){ 
                            ArrayList<ArrayList<String>> aux = new ArrayList<>();
                            aux.add(arrImage);
                            this.structureImgs.add(level, aux);
                        }
                        else if (level > this.structureImgs.size()){
                            ArrayList<ArrayList<String>> aux = new ArrayList<>();
                            aux.add(arrImage);
                            this.structureImgs.add(aux);
                        }
                        else {
                            ArrayList<ArrayList<String>> aux = new ArrayList<>();
                            aux = this.structureImgs.get(level);
                            aux.add(arrImage);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void insertionSort() {
        try {
            int i,j;

            for (ArrayList<ArrayList<String>> arrLevel : this.structureImgs) {
                for (i = 1; i < arrLevel.size(); i++) {
                    ArrayList<String> arr= arrLevel.get(i);
                    String string = arr.get(1);
                    j = i;
                    while((j > 0) && (arrLevel.get(j - 1).get(1).compareTo(string))>0) {
                        arrLevel.set(j,arrLevel.get(j - 1));
                        j--;
                    }
                    arrLevel.set(j,arr);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
    
    public boolean containsImg(ArrayList<String> arrImage){
        try{
            for (int i=0; i<this.structureImgs.size(); i++){
                if (this.structureImgs.get(i).contains(arrImage)) {
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
