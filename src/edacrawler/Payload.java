/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edacrawler;

import java.util.ArrayList;

/**
 *
 * @author pedrodias & paulosantos
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
    
    public void merge (Payload pl) {    
        this.links.addAll(pl.links);
        //this.links.addAll(level, pl.links);
        this.imgs.addAll(pl.imgs);
        //this.imgs.addAll(level, pl.imgs);
    }
    
    public void addToStructure(Payload pl, int level) {
        this.structureImgs.add(level, pl.imgs);
        this.structureLinks.add(level, pl.links);
    }
    
    
    
}
