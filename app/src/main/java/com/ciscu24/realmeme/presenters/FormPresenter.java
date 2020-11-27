package com.ciscu24.realmeme.presenters;

import com.ciscu24.realmeme.R;
import com.ciscu24.realmeme.interfaces.FormInterface;
import com.ciscu24.realmeme.views.MyApplication;

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

    @Override
    public String getError(String error) {
        String result = "";

        switch (error){
            case "name":
                result = MyApplication.getContext().getResources().getString(R.string.error_name);
                break;
            case "description":
                result = MyApplication.getContext().getResources().getString(R.string.error_description);
                break;
            case "author":
                result = MyApplication.getContext().getResources().getString(R.string.error_author);
                break;
            case "like":
                result = MyApplication.getContext().getResources().getString(R.string.error_like);
                break;
            case "date":
                result = MyApplication.getContext().getResources().getString(R.string.error_date);
                break;
        }

        return result;
    }

    @Override
    public void onClickAcceptDeleteButton() {
        view.DeleteMeme();
    }

}
