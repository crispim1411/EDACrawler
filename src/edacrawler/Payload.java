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
    public ArrayList<ArrayList<String>> structureImgs = new ArrayList<ArrayList<String>>();
    public ArrayList<ArrayList<String>> structureLinks = new ArrayList<ArrayList<String>>();
    public ArrayList<String> links;
    public ArrayList<String> imgs;
    public String html = "";

    public Payload() {
        links = new ArrayList<>();
        imgs = new ArrayList<>();
    }
    
    public void addToStructure(Payload pl, int level) {
        try {
            //o level é a camada de pesquisa, inicia em 1
            level--; //level = index+1
            if (pl != null){
                for (String string : pl.links) { //pl links é adicionado a structureLinks 
                    //System.out.println("verificar "+string+" nivel "+(level+1));
                    if (contains(this.structureLinks, string, level)==false) {
                        if (level == this.structureLinks.size()){
                            //System.out.println("index e size: "+level);
                            ArrayList<String> aux = new ArrayList<>();
                            aux.add(string);
                            this.structureLinks.add(level, aux);
                        }
                        else if (level > this.structureLinks.size()){
                            //System.out.println("index: "+level+" size: "+this.structureLinks.size());
                            ArrayList<String> aux = new ArrayList<>();
                            aux.add(string);
                            this.structureLinks.add(aux);
                        }
                        else {
                            //System.out.println("index: "+level+" size: "+this.structureLinks.size());
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

                for (String string : pl.imgs) { //pl links é adicionado a structureLinks
                    //System.out.println("verificar "+string+" nivel "+(level+1));
                    if (contains(this.structureImgs, string, level) == false) {
                        if (level == this.structureImgs.size()){
                            //System.out.println("index e size: "+level);
                            ArrayList<String> aux = new ArrayList<>();
                            aux.add(string);
                            this.structureImgs.add(level, aux);
                        }
                        else if (level > this.structureImgs.size()){
                            //System.out.println("index: "+level+" size: "+this.structureImgs.size());
                            ArrayList<String> aux = new ArrayList<>();
                            aux.add(string);
                            this.structureImgs.add(aux);
                        }
                        else {
                            //System.out.println("index: "+level+" size: "+this.structureImgs.size());
                            ArrayList<String> aux = new ArrayList<>();
                            aux = this.structureImgs.get(level);
                            aux.add(string);
                        }
                    }
                    //System.out.println("plimgs: "+pl.structureImgs.get(level));
                    //printStructure(pl.structureImgs);
                }
                //printStructure(pl.structureImgs);
                //System.out.println("\n");
            }
        } catch (Exception e) {
            System.out.println("Exception is: " + e);
        }
    }
    
    public boolean contains(ArrayList<ArrayList<String>> structure, String url, int level){
        //System.out.println("level: "+level+", size: "+structure.size());
        
        for (int i=0; i<structure.size(); i++){
            if (structure.get(i).contains(url)) {
                //System.out.println("existe no nivel "+(i+1));
                return true;
            }
        } 
        return false;
    }
    
    public static void printStructure(ArrayList<ArrayList<String>> structure) {
        if (structure != null) {
            //System.out.println("level: "+structure.size());
            int i = 0;
            for (ArrayList<String> array : structure) {
                System.out.println(""+array.size()+" itens");
                System.out.println("level "+(i+1)+": "+array);
                i++;
            }
        }
    }
    
}
