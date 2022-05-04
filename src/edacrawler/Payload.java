/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edacrawler;

import edacrawler.models.ImageInfo;
import edacrawler.models.SkipList;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PedroMatias & RodrigoCrispim
 */
public class Payload {
    public SkipList structureImgs = new SkipList();
    public SkipList structureLinks = new SkipList();
    public ArrayList visited = new ArrayList();
    // link payload
    public ArrayList<String> links;
    // imagens payload
    public ArrayList<ImageInfo> imgs;

    public Payload() {
        links = new ArrayList<>();
        imgs = new ArrayList<>(); 
    }
    
    public void addToStructure(Payload pl) {
        if (pl == null) return;

        for (String link : pl.links) { 
            if (link == null || link.isBlank()) continue;
            this.structureLinks.Insert(link, link);
        }

        for (ImageInfo imgInfo : pl.imgs) {
            if (imgInfo.url == null || imgInfo.url.isBlank()) continue;
            this.structureImgs.Insert(imgInfo.url, imgInfo.info);
        }
    }  
}
