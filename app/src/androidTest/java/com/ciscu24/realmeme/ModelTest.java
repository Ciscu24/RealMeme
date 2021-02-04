package com.ciscu24.realmeme;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.ciscu24.realmeme.models.MemeModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ModelTest {

    private MemeModel memeModel;

    @Before
    public void setUp(){
        memeModel = new MemeModel();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.ciscu24.realmeme", appContext.getPackageName());
    }
}
