package com.example.brm77.brm_my_lesson.data;

public class Lessons {

    private int id;
    private int id_subject;
    private String nameSubject;

    public Lessons(int id_subject,String nameSubject){
        this.id_subject = id_subject;
        this.nameSubject = "Лекция по предмету "+nameSubject;
    }
    public Lessons(int id,int id_subject,String nameSubject){
        this.id = id;
        this.id_subject = id_subject;
        this.nameSubject = "Лекция по предмету "+nameSubject;
      }

    public String getName() {
        return nameSubject;
    }

    public Integer getID(){
        return id;
    }

    public  Integer getID_Subject(){
        return id_subject;
    }
}


