package com.ciscu24.realmeme.interfaces;

public interface SearchInterface {

    public interface View{
        void SearchMeme();
    }

    public interface Presenter{
        void onClickSearchButton();
    }
}
