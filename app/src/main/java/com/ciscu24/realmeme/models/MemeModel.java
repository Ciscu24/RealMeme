package com.ciscu24.realmeme.models;

import android.util.Log;

import com.ciscu24.realmeme.R;
import com.ciscu24.realmeme.views.MyApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;
import io.realm.RealmQuery;
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

    public void updateMeme(MemeEntity meme){
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(r -> {
            realm.copyToRealmOrUpdate(meme);
        });
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

    public MemeEntity getMemeById(String id){
        MemeEntity result = null;
        Realm realm = Realm.getDefaultInstance();

        result = realm.where(MemeEntity.class)
                .equalTo("id", id)
                .findFirst();

        return result;
    }

    public ArrayList<String> getAllCategories(){
        ArrayList<String> categories = new ArrayList<>();

        categories.add(MyApplication.getContext().getString(R.string.spinner_info));
        categories.add(MyApplication.getContext().getString(R.string.spinner_add));

        Realm realm = Realm.getDefaultInstance();

        RealmResults<MemeEntity> result = realm.where(MemeEntity.class).distinct("category").findAll();

        ArrayList<MemeEntity> memeList = new ArrayList<>();

        memeList.addAll(realm.copyFromRealm(result));

        for(MemeEntity meme: memeList){
            categories.add(meme.getCategory());
        }

        return categories;

    }

    public boolean deleteMeme(String id){
        boolean result = false;
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(r -> {
            MemeEntity memeRealm = realm.where(MemeEntity.class)
                    .equalTo("id", id)
                    .findFirst();

            memeRealm.deleteFromRealm();
        });

        result = true;

        realm.close();

        return result;
    }

    public ArrayList<MemeEntity> getWithFilter(String name, Date date, String category){
        Realm realm = Realm.getDefaultInstance();

        RealmResults<MemeEntity> result;

        if(date==null){
            result = realm.where(MemeEntity.class).contains("name", name)
                    .contains("category", category)
                    .findAll();
        }else{
            result = realm.where(MemeEntity.class).contains("name", name)
                    .equalTo("date", date)
                    .contains("category", category)
                    .findAll();
        }

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
