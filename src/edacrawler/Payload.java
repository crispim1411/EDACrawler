/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edacrawler;

import java.util.ArrayList;

/**
 *
 * @author PedroMatias & RodrigoCrispim
 */
public class Payload {
    public ArrayList<ArrayList<ArrayList<String>>> structureImgs = new ArrayList<ArrayList<ArrayList<String>>>();
    public ArrayList<ArrayList<String>> structureLinks = new ArrayList<ArrayList<String>>();
    public ArrayList<String> links;
    //public ArrayList<String> imgs;
    public ArrayList<ArrayList<String>> imgs;
    public String html = "";

    public Payload() {
        links = new ArrayList<>();
        imgs = new ArrayList<ArrayList<String>>(); //imagem: [[src1,alt1], [src2,alt2], ... ]
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
                    //System.out.println("pllinks: "+pl.structureLinks.get(level));
                    //printStructure(pl.structureLinks);
                }
                //printStructure(pl.structureLinks);
                //System.out.println("\n");

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
                    //System.out.println("plimgs: "+pl.structureImgs.get(level));
                    //printStructure(pl.structureImgs);
                }
                //printStructure(pl.structureImgs);
                //System.out.println("\n");
            }
        } catch (Exception e) {
            System.out.println("Exception addToStructure: " + e);
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
            System.out.println("Exception insertionSort: "+e);
        }
        
    }
    
    public boolean containsImg(ArrayList<String> arrImage){
        for (int i=0; i<this.structureImgs.size(); i++){
            if (this.structureImgs.get(i).contains(arrImage)) {
                return true;
            }
        } 
        return false;
    }
    
    public boolean containsLink(String url) {
        for (int i=0;i<this.structureLinks.size();i++) {
            if (this.structureLinks.get(i).contains(url)) {
                return true;
            }
        }
        return false;
    }
    
    public static void printStructure(Payload pl) {
        if (pl != null) {
            int i = 0;
            for (ArrayList<String> array : pl.structureLinks) {
                System.out.println(""+array.size()+" itens");
                System.out.println("level "+(i+1)+": "+array);
                i++;
            }
            i = 0;
            for (ArrayList<ArrayList<String>> array : pl.structureImgs) {
                System.out.println(""+array.size()+" itens");
                System.out.println("level "+(i+1)+": "+array);
                i++;
            }
        }
    }
    
}
