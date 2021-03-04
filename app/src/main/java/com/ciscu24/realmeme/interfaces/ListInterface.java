package com.ciscu24.realmeme.interfaces;

import androidx.recyclerview.widget.RecyclerView;

import com.ciscu24.realmeme.models.MemeEntity;

import java.util.ArrayList;
import java.util.Date;

public interface ListInterface {

    public interface View{
        void startFormActivity();
        void startFormActivity(String id);
        void startAboutActivity();
        void startSearchActivity();
        void startHelpListActivity();
        void onSwipedRemove(RecyclerView.ViewHolder target);
    }

    public interface Presenter{
        void onClickAddMeme();
        void onClickAboutToolbar();
        void onClickSearchToolbar();
        void onClickHelpToolbar();
        void onClickRecyclerViewItem(String id);
        void onSwipeMeme(RecyclerView.ViewHolder target, String id);
        ArrayList<MemeEntity> getAllItemsSummarize();
        ArrayList<MemeEntity> getItemsFilter(String name, Date date, String category);
        ArrayList<String> getCategoriesRealm();
    }
}
