package com.example.brm77.brm_my_lesson.data;

public class Subjects {

    private int id;
    private String name;

    public Subjects(String name){
        this.name = name;
    }

    public Subjects(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }
}
