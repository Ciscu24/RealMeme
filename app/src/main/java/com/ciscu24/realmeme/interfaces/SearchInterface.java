package com.ciscu24.realmeme.interfaces;

import java.util.ArrayList;

public interface SearchInterface {

    public interface View{
        void SearchMeme();
    }

    public interface Presenter{
        void onClickSearchButton();
        ArrayList<String> getCategoriesRealm();
    }
}
