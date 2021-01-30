package com.ciscu24.realmeme.presenters;

import com.ciscu24.realmeme.interfaces.SearchInterface;
import com.ciscu24.realmeme.models.MemeModel;

import java.util.ArrayList;

public class SearchPresenter implements SearchInterface.Presenter {

    private SearchInterface.View view;
    private MemeModel memeModel;

    public SearchPresenter(SearchInterface.View view) {
        this.view = view;
        memeModel = new MemeModel();
    }

    @Override
    public void onClickSearchButton() {
        view.SearchMeme();
    }

    @Override
    public ArrayList<String> getCategoriesRealm() {
        return memeModel.getAllCategories();
    }

}
