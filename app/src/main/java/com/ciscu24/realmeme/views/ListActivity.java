package com.ciscu24.realmeme.views;

import android.content.Intent;
import android.os.Bundle;

import com.ciscu24.realmeme.R;
import com.ciscu24.realmeme.interfaces.ListInterface;
import com.ciscu24.realmeme.presenters.ListPresenter;
import com.ciscu24.realmeme.views.FormActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class ListActivity extends AppCompatActivity implements ListInterface.View {

    String TAG = "Demo2021/ListActivity";
    private ListInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new ListPresenter(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on Floating Button");
                presenter.onClickAddMeme();
            }
        });
    }

    //Funcion para añadir el menu que queramos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    //Funcion para asignar funciones a los botones del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_list) {
            return true;
        }

        if(id == R.id.action_search_list){
            presenter.onClickSearchToolbar();
        }

        if(id == R.id.action_about_list){
            presenter.onClickAboutToolbar();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startFormActivity() {
        Intent intent = new Intent(getApplicationContext(), FormActivity.class);
        startActivity(intent);
    }

    @Override
    public void startAboutActivity() {
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void startSearchActivity() {
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent);
    }


}