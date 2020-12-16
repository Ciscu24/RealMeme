package com.ciscu24.realmeme.interfaces;

public interface FormInterface {

    public interface View{
        void SaveMeme();
        void DeleteMeme();
        void selectImageFromGallery();
        void cleanImage();
        void showErrorPermissionDenied();
        void showRequestPermission();
    }

    public interface Presenter{
        void onClickSaveButton();
        String getError(String error);
        void onClickAcceptDeleteButton();
        void onClickImageView();
        void onClickCleanImage();
        void permissionGranted();
        void permissionDenied();
    }

}
