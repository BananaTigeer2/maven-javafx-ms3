package org.bananatigeer.custom_counter;

public class CustomCounter {
    private int count;

    public CustomCounter(){
        this.count = 0;
    }

    public void increment(){
        this.count++;
    }

    public int getCount(){
        return this.count;
    }

}
