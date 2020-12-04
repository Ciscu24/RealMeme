package com.ciscu24.realmeme.presenters;

import com.ciscu24.realmeme.interfaces.ListInterface;

public class ListPresenter implements ListInterface.Presenter {

    private ListInterface.View view;

    public ListPresenter(ListInterface.View view) {
        this.view = view;
    }

    @Override
    public void onClickAddMeme() {
        //Log.d("");
        view.startFormActivity();
    }

    @Override
    public void onClickAboutToolbar() {
        view.startAboutActivity();
    }

    @Override
    public void onClickSearchToolbar() {
        view.startSearchActivity();
    }

    @Override
    public void onClickRecyclerViewItem(String id) {
        view.startFormActivity(id);
    }

    public void onSwipeRecyclerViewItem(String id) {
        //Decirle al modelo que borre id
        //... luego en la Unidad 5
        //Decirle al RecyclerView que lo elimino
        //view.removeRecyclerViewItem(id);
        //Decirle al view que muestre el Toast
        //view.showToast("error");
    }
}
