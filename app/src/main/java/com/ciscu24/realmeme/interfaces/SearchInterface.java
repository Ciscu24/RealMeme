package com.ciscu24.realmeme.interfaces;

import java.util.ArrayList;

public interface SearchInterface {

    public interface View{
        void SearchMeme();
        void startHelpSearchActivity();
    }

    public interface Presenter{
        void onClickSearchButton();
        ArrayList<String> getCategoriesRealm();
        void onClickHelpToolbar();
    }
}
