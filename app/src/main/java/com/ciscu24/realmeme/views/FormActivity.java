package com.ciscu24.realmeme.views;

import android.graphics.Color;
import android.os.Bundle;

import com.ciscu24.realmeme.R;
import com.ciscu24.realmeme.interfaces.FormInterface;
import com.ciscu24.realmeme.models.MemeEntity;
import com.ciscu24.realmeme.presenters.FormPresenter;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FormActivity extends AppCompatActivity implements FormInterface.View {

    private FormInterface.Presenter presenter;
    EditText nameText;
    TextInputLayout nameInputLayout;
    MemeEntity meme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        presenter = new FormPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Asignar la acción necesaria. En este caso "volver atrás"
                onBackPressed();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.CategorySpinner);
        String[] letra = {"Seleccione categoria","Anime","Animales","Peliculas","Comics"};

        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, letra));

        Button SaveButton = findViewById(R.id.SaveButton);

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickSaveButton();
            }
        });

        nameText = findViewById(R.id.NameTextForm);
        nameInputLayout = findViewById(R.id.NameInputForm);
        meme = new MemeEntity();

        nameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    //Log.d("FormActivity", "Exit EditText");
                    if (!meme.setName(nameText.getText().toString())) {
                        nameText.setError(presenter.getError("name"));
                    } else {
                        nameText.setError("");
                    }
                }else{
                    //Log.d("FormActivity", "Input EditText");
                }
            }
        });
    }

    @Override
    public void SaveMeme() {
        finish();
    }
}