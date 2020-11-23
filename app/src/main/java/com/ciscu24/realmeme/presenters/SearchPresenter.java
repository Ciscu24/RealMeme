package com.ciscu24.realmeme.presenters;

import com.ciscu24.realmeme.interfaces.SearchInterface;

public class SearchPresenter implements SearchInterface.Presenter {

    private SearchInterface.View view;

    public SearchPresenter(SearchInterface.View view) {
        this.view = view;
    }

    @Override
    public void onClickSearchButton() {
        view.SearchMeme();
    }
}
