package com.ciscu24.realmeme;

import com.ciscu24.realmeme.models.MemeEntity;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MemeTest {
    private MemeEntity meme;

    @Before
    public void setUp(){
        this.meme = new MemeEntity();
    }

    @Test
    public void memeName(){
        assertEquals(true, this.meme.setName("Esto es un nombre"));
        assertEquals(false, this.meme.setName("Esto no es 007"));
    }

}
