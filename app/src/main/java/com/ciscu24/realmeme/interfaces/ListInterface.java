package com.ciscu24.realmeme.interfaces;

public interface ListInterface {

    public interface View{
        void startFormActivity();
        void startFormActivity(String id);
        void startAboutActivity();
        void startSearchActivity();
    }

    public interface Presenter{
        void onClickAddMeme();
        void onClickAboutToolbar();
        void onClickSearchToolbar();
        void onClickRecyclerViewItem(String id);
    }
}
