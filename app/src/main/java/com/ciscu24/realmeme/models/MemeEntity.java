package com.ciscu24.realmeme.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MemeEntity extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private String description;
    private String author;
    private int like;
    private Date date;
    private boolean fav;
    private String category;
    private String image;


    public MemeEntity() {}

    public MemeEntity(String name, String description, String author, int like, Date date, boolean fav, String category) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.like = like;
        this.date = date;
        this.fav = fav;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        boolean result = false;

        if(name.length()>5 && name.matches("[^0-9]+")) {
            this.name = name;
            result = true;
        }

        return result;
    }

    public String getDescription() {
        return description;
    }

    public boolean setDescription(String description) {
        boolean result = false;

        if(description.length()>10 && description.length()<50){
            this.description = description;
            result = true;
        }

        return result;
    }

    public String getAuthor() {
        return author;
    }

    public boolean setAuthor(String author) {
        boolean result = false;

        if(author.length()>5) {
            this.author = author;
            result = true;
        }

        return result;
    }

    public int getLike() {
        return like;
    }

    public boolean setLike(String like) {
        boolean result = false;

        if(like.matches("[0-9]*") && like.matches("[0-9]|10")) {
            this.like = Integer.parseInt(like);
            result = true;
        }

        return result;
    }

    public Date getDate() {
        return date;
    }

    public boolean setDate(String date) {
        boolean result = false;
        SimpleDateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");
        String RegEx = "^(?:(?:(?:0?[1-9]|1\\d|2[0-8])[/](?:0?[1-9]|1[0-2])|(?:29|30)[/](?:0?[13-9]|1[0-2])|31[/](?:0?[13578]|1[02]))[/](?:0{2,3}[1-9]|0{1,2}[1-9]\\d|0?[1-9]\\d{2}|[1-9]\\d{3})|29[/]0?2[/](?:\\d{1,2}(?:0[48]|[2468][048]|[13579][26])|(?:0?[48]|[13579][26]|[2468][048])00))$";

        if(date.matches(RegEx)){
            try {
                this.date = newDate.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            result = true;
        }

        return result;

    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
