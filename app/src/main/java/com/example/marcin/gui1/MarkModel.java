package com.example.marcin.gui1;

import java.io.Serializable;

/**
 * Created by marcin on 22.03.18.
 */

public class MarkModel implements Serializable {
    private String name;
    private int mark;

    MarkModel(String name){
        this.name = name;
        this.mark = 2;
    }

    public String getName(){
        return name;
    }

    public int getMark(){
        return mark;
    }

    public void setMark(int mark){
        this.mark = mark;
    }
}
