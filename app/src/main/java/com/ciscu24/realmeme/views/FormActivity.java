package com.ciscu24.realmeme.views;

import android.content.Intent;
import android.os.Bundle;

import com.ciscu24.realmeme.R;
import com.ciscu24.realmeme.interfaces.FormInterface;
import com.ciscu24.realmeme.interfaces.ListInterface;
import com.ciscu24.realmeme.presenters.FormPresenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class FormActivity extends AppCompatActivity implements FormInterface.View {

    private FormInterface.Presenter presenter;

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
        String[] letra = {"A","B","C","D","E"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, letra));

        Button SaveButton = findViewById(R.id.SaveButton);

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickSaveButton();
            }
        });
    }

    @Override
    public void SaveMeme() {
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);
        finish();
    }
}