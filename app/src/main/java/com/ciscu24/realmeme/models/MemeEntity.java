package com.ciscu24.realmeme.models;

public class MemeEntity {
    private String name;
    private String description;
    private String author;
    //private int like;
    //private Date date;


    public MemeEntity() {}

    public MemeEntity(String name, String description, String author) {
        this.name = name;
        this.description = description;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        boolean result = false;

        if(name.toString().length()>5) {
            this.name = name;
            result = true;
        }



        return result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
