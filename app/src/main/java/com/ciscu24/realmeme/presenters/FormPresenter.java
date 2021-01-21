package com.ciscu24.realmeme.presenters;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ciscu24.realmeme.R;
import com.ciscu24.realmeme.interfaces.FormInterface;
import com.ciscu24.realmeme.models.MemeEntity;
import com.ciscu24.realmeme.models.MemeModel;
import com.ciscu24.realmeme.views.FormActivity;
import com.ciscu24.realmeme.views.MyApplication;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FormPresenter implements FormInterface.Presenter {

    private FormInterface.View view;
    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private MemeModel memeModel;

    public FormPresenter(FormInterface.View view) {
        this.view = view;
        memeModel = new MemeModel();
    }

    @Override
    public void onClickSaveButton(MemeEntity meme) {
        if(meme.getId()!=""){
            memeModel.updateMeme(meme);
            view.SaveMeme();
        }else if(memeModel.insertMeme(meme)){
            view.SaveMeme();
        }else{
            //mostrar un error en el formulario
            view.showErrorWithToast(MyApplication.getContext().getResources().getString(R.string.memeNotSave));
        }
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

    @Override
    public void onClickImageView() {
        int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Log.d("FormPresenter", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);
        if(WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED){
            view.showRequestPermission();
        }else{
            // Permiso aceptado
            view.selectImageFromGallery();
        }
    }

    @Override
    public void onClickCleanImage() {
        view.cleanImage();
    }

    @Override
    public void permissionGranted() {
        view.selectImageFromGallery();
    }

    @Override
    public void permissionDenied() {
        view.showErrorPermissionDenied();
    }

    @Override
    public MemeEntity getMemeById(String id) {
        MemeEntity result = new MemeEntity();
        result = memeModel.getMemeById(id);
        return result;
    }

    @Override
    public ArrayList<String> getCategoriesRealm() {
        return memeModel.getAllCategories();
    }

}
