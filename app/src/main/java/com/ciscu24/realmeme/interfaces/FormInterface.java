package com.ciscu24.realmeme.interfaces;

import com.ciscu24.realmeme.models.MemeEntity;

import java.util.ArrayList;

public interface FormInterface {

    public interface View{
        void SaveMeme();
        void DeleteMeme();
        void selectImageFromGallery();
        void cleanImage();
        void showErrorPermissionDenied();
        void showRequestPermission();
        void startHelpFormActivity();
        void showErrorWithToast(String text);
    }

    public interface Presenter{
        void onClickSaveButton(MemeEntity meme);
        String getError(String error);
        void onClickAcceptDeleteButton(String id);
        void onClickHelpToolbar();
        void onClickImageView();
        void onClickCleanImage();
        void permissionGranted();
        void permissionDenied();
        MemeEntity getMemeById(String id);
        ArrayList<String> getCategoriesRealm();
    }

}
