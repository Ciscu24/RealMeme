package com.ciscu24.realmeme.presenters;

import com.ciscu24.realmeme.interfaces.FormInterface;

public class FormPresenter implements FormInterface.Presenter {

    private FormInterface.View view;

    public FormPresenter(FormInterface.View view) {
        this.view = view;
    }

    @Override
    public void onClickSaveButton() {
        //Log.d("");
        view.SaveMeme();
    }
}
