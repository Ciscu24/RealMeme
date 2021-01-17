package com.ciscu24.realmeme.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;
import io.realm.RealmResults;

public class MemeModel {

    public boolean insertMeme(MemeEntity meme){
        AtomicBoolean result = new AtomicBoolean(false);

        meme.setId(UUID.randomUUID().toString());

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(r -> {
            realm.copyToRealm(meme);
            result.set(true);
        });

        realm.close();

        return result.get();
    }

    public ArrayList<MemeEntity> getAllSummarize(){
        Realm realm = Realm.getDefaultInstance();

        RealmResults<MemeEntity> result = realm.where(MemeEntity.class).findAll();

        Log.d("Realm find items: ", "" + result.size());

        ArrayList<MemeEntity> memeList = new ArrayList<>();
        memeList.addAll(realm.copyFromRealm(result));

        realm.close();

        ArrayList<MemeEntity> memeListSummarize = new ArrayList<>();

        for(MemeEntity meme: memeList){
            MemeEntity newMeme = new MemeEntity();
            newMeme.setId(meme.getId());
            newMeme.setName(meme.getName());
            newMeme.setLike(meme.getLike()+"");
            newMeme.setImage(meme.getImage());
            memeListSummarize.add(newMeme);
        }

        return memeListSummarize;
    }
}
