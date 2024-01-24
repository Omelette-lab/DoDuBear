package com.example.denglu;

import java.io.Serializable;

public class ToDoList implements Serializable {
    private String item_name;
    private String deadline;
    private int icon;
    public ToDoList(String name,String deadline,int icon){
        this.item_name=name;
        this.deadline=deadline;
        this.icon=icon;

    }
    public String getItem_name(){
        return this.item_name;
    }
    public String getDeadline(){
        return this.deadline;
    }
    public int getIcon(){
        return this.icon;
    }


    public void setItem_name(String item_name){
        this.item_name=item_name;
    }
    public void setDeadline(String deadline){
        this.deadline=deadline;
    }
    public void setIcon(int icon){
        this.icon=icon;
    }
}
