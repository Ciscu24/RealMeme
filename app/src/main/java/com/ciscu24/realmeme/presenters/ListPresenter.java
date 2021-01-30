package com.ciscu24.realmeme.presenters;

import androidx.recyclerview.widget.RecyclerView;

import com.ciscu24.realmeme.interfaces.ListInterface;
import com.ciscu24.realmeme.models.MemeEntity;
import com.ciscu24.realmeme.models.MemeModel;

import java.util.ArrayList;
import java.util.Date;

public class ListPresenter implements ListInterface.Presenter {

    private ListInterface.View view;
    private MemeModel model;

    public ListPresenter(ListInterface.View view) {
        this.view = view;
        this.model = new MemeModel();
    }

    @Override
    public void onClickAddMeme() {
        //Log.d("");
        view.startFormActivity();
    }

    public ArrayList<MemeEntity> getAllItemsSummarize(){
        return model.getAllSummarize();
    }

    @Override
    public ArrayList<MemeEntity> getItemsFilter(String name, Date date, String category) {
        return model.getWithFilter(name, date, category);
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

    @Override
    public void onSwipeMeme(RecyclerView.ViewHolder target, String id) {
        model.deleteMeme(id);
        view.onSwipedRemove(target);
    }

    public void onSwipeRecyclerViewItem(String id) {
        //Decirle al modelo que borre id
        //... luego en la Unidad 5
        //Decirle al RecyclerView que lo elimino
        //view.removeRecyclerViewItem(id);
        //Decirle al view que muestre el Toast
        //view.showToast("error");
    }

    @Override
    public ArrayList<String> getCategoriesRealm() {
        return model.getAllCategories();
    }
}
