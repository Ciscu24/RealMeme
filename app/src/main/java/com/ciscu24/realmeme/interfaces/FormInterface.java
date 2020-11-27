package com.ciscu24.realmeme.interfaces;

public interface FormInterface {

    public interface View{
        void SaveMeme();
        void DeleteMeme();
    }

    public interface Presenter{
        void onClickSaveButton();
        String getError(String error);
        void onClickAcceptDeleteButton();
    }

}
