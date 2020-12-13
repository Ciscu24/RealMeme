package com.ciscu24.realmeme.interfaces;

public interface FormInterface {

    public interface View{
        void SaveMeme();
        void DeleteMeme();
        void permisions();
        void selectPicture();
        void cleanImage();
    }

    public interface Presenter{
        void onClickSaveButton();
        String getError(String error);
        void onClickAcceptDeleteButton();
        void onClickImageView();
        void onClickSelectImage();
        void onClickCleanImage();
    }

}
