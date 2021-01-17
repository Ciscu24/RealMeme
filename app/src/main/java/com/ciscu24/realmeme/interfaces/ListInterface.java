package com.ciscu24.realmeme.interfaces;

import androidx.recyclerview.widget.RecyclerView;

import com.ciscu24.realmeme.models.MemeEntity;

import java.util.ArrayList;

public interface ListInterface {

    public interface View{
        void startFormActivity();
        void startFormActivity(String id);
        void startAboutActivity();
        void startSearchActivity();
        void onSwipedRemove(RecyclerView.ViewHolder target);
    }

    public interface Presenter{
        void onClickAddMeme();
        void onClickAboutToolbar();
        void onClickSearchToolbar();
        void onClickRecyclerViewItem(String id);
        void onSwipeMeme(RecyclerView.ViewHolder target);
        ArrayList<MemeEntity> getAllItemsSummarize();
    }
}
