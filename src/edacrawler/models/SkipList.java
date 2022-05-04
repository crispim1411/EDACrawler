package edacrawler.models;


import java.util.ArrayList;
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author crispim
 */


public class SkipList {
    public class Node {
        public String key;
        public String value;
        public Node[] forward;

        Node() {
            this.key = null;
            this.value = null;
            this.forward = new Node[MAXLEVEL+1];
        }
        Node(String key, String value, int randomLevel) {
            this.key = key;
            this.value = value;
            this.forward = new Node[randomLevel+1];
        }
    }
    
    public Node head;
    public int level;
    private static final int MAXLEVEL = 2;

    public SkipList() {
        this.head = new Node();
        this.level = 0;
    }
    
    private int getRandomLevel() {
        Random rand = new Random();
        int level = 0;
        while (rand.nextFloat() < 0.5 && level < MAXLEVEL) {
            level += 1;
        }
        return level;
    }
    
    private Node[] FillUpdateVector(Node cursor, Node[] update, String key, int level) {
        if (cursor.forward[level] != null && cursor.forward[level].key.compareTo(key) < 0) {
            return FillUpdateVector(cursor.forward[level], update, key, level);
        }
        update[level] = cursor;
        
        if (level > 0) {
            return FillUpdateVector(cursor, update, key, level-1);
        }
        return update;
    }
    
    public void Insert(String key, String value) {
        int randomLevel = getRandomLevel();
        
        Node[] update = FillUpdateVector(this.head, new Node[randomLevel+1], key, randomLevel);
        
        if (update[0].forward[0] != null && 
                update[0].forward[0].key.equals(key)) {
            return;
        }
        
        Node newNode = new Node(key, value, randomLevel);
        
        for(int lvl = 0; lvl <= randomLevel; lvl++) {
            Node node = update[lvl];
            newNode.forward[lvl] = node.forward[lvl];
            node.forward[lvl] = newNode;
        }
        
        if (randomLevel > this.level) {
            this.level = randomLevel;
        }
    }
    
    public boolean IsEmpty() {
        return this.head.forward[0] == null;
    }
    
    public void Display() {
        if (this.IsEmpty()) {
            System.out.println("Empty SkipList");
            return;
        }
        Display(this.head, this.level);
    }
    
    private void Display(Node cursor, int level) {
        if (cursor.forward[level] != null) {
            System.out.print("["+cursor.forward[level].key+"] -> ");
            Display(cursor.forward[level], level);
            return;
        }
        System.out.println();
        if (level != 0) {
            Display(this.head, level-1);
        }
    }
    
    public ArrayList<Node> ToList() {
        ArrayList result = new ArrayList();
        Node cursor = this.head;
        
        while (cursor.forward[0] != null) {
            result.add(cursor.forward[0]);
            cursor = cursor.forward[0];
        }
        return result;
    }
    
    public boolean Contains(String key) {
        if (IsEmpty()) {
            return false;
        }
        return Contains(this.head, key, this.level);
    }
    
    private boolean Contains(Node cursor, String key, int level) {
        System.out.println("cursor: "+cursor.key+" -> ");
        if (cursor.forward[level] != null) {
            if (cursor.forward[level].key.compareTo(key) < 0) {
                return Contains(cursor.forward[level], key, level);
            } 
            else if (cursor.forward[level].key.compareTo(key) == 0) {
                return true;
            }
        }
                
        if (level > 0) {
            return Contains(cursor, key, level-1);
        }
        return false;
    }
}
