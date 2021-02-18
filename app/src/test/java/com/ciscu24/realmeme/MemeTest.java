package com.ciscu24.realmeme;

import com.ciscu24.realmeme.models.MemeEntity;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

public class MemeTest {
    private MemeEntity meme;

    @Before
    public void setUp(){
        this.meme = new MemeEntity();
    }

    @Test
    public void memeId(){
        this.meme.setId("123");
        assertEquals("123", this.meme.getId());
        this.meme.setId("456");
        assertEquals("456", this.meme.getId());
    }

    @Test
    public void memeName(){
        assertEquals(true, this.meme.setName("This is a name"));
        assertEquals(false, this.meme.setName("This is a 123"));
        assertEquals(false, this.meme.setName("Name"));
        assertEquals("This is a name", this.meme.getName());
    }

    @Test
    public void memeDescription(){
        assertEquals(true, this.meme.setDescription("Description of more than 10 words"));
        assertEquals(false, this.meme.setDescription("This is a description of more than 50 words so that is why it is false"));
        assertEquals("Description of more than 10 words", this.meme.getDescription());
    }

    @Test
    public void memeAuthor(){
        assertEquals(true, this.meme.setAuthor("Ciscu24"));
        assertEquals(false, this.meme.setAuthor("Anto"));
        assertEquals("Ciscu24", this.meme.getAuthor());
    }

    @Test
    public void memeLike(){
        assertEquals(false, this.meme.setLike("-1"));
        assertEquals(true, this.meme.setLike("0"));
        assertEquals(0, this.meme.getLike());
        assertEquals(false, this.meme.setLike("12"));
        assertEquals(true, this.meme.setLike("6"));
        assertEquals(6, this.meme.getLike());
    }

    @Test
    public void memeDate(){
        assertEquals(true, this.meme.setDate("20/07/2020"));
        assertEquals(false, this.meme.setDate("34/08/2020"));
        assertEquals(true, this.meme.setDate("2/9/2020"));
        SimpleDateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("02/09/2020", newDate.format(this.meme.getDate()));
    }

    @Test
    public void memeFav(){
        this.meme.setFav(true);
        assertEquals(true, this.meme.isFav());
        this.meme.setFav(false);
        assertEquals(false, this.meme.isFav());
    }

    @Test
    public void memeCategory(){
        this.meme.setCategory("Family");
        assertEquals("Family", this.meme.getCategory());
        this.meme.setCategory("Anime");
        assertEquals("Anime", this.meme.getCategory());
    }

    @Test
    public void memeImage(){
        this.meme.setImage("1234556789");
        assertEquals("1234556789", this.meme.getImage());
        this.meme.setImage("987654321");
        assertEquals("987654321", this.meme.getImage());
    }
}
