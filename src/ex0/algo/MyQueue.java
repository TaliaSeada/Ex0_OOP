package ex0.algo;

import ex0.CallForElevator;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.*;

public class MyQueue {
    private ArrayList<Integer> queue;

    public MyQueue() {
        this.queue = new ArrayList<Integer>();
    }

    public void add(int num) {
        this.queue.add(num);
    }

    public void addAt(int num, int index){
        try{
            this.queue.add(index,num);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public int poll() {
        if (this.queue.isEmpty()) {
            return Integer.parseInt(null);
        }
        int num = queue.get(0);
        queue.remove(0);
        return num;
    }

    public int peek() {
        if (queue.isEmpty()) {
            return Integer.parseInt(null);
        }
        return queue.get(0);
    }

    public int get(int index) {
        return this.queue.get(index);
    }

    public int size() {
        return this.queue.size();
    }

    public void sort() {
        Collections.sort(queue);
    }
    public void sortUpToDown() {
        Collections.sort(queue);
        Collections.reverse(queue);
    }
    public void removeDuplicateAfter() {// to remove unnecessary calls in a row to the same floor
        for(int i =0; i <this.queue.size()-1;i++){
            if(this.queue.get(i) == this.queue.get(i+1)){
                this.queue.remove(i+1);
            }
        }
    }

    public String toString(){
        String queue = "[";
        for(int i =0; i < this.queue.size();i++){
            queue += this.queue.get(i) + ",";
        }
        queue += "]";
        return queue;
    }





}
