package com.ciscu24.realmeme.views;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.ciscu24.realmeme.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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
        WebView helpWebView = findViewById(R.id.HelpWebView);
        helpWebView.getSettings().setJavaScriptEnabled(true);
        String help = "";

        help = getIntent().getStringExtra("help");

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Si hay conexión a Internet en este momento
            if(!help.equals("")){
                if(help.equals("list")){
                    helpWebView.loadUrl("https://ciscu24.github.io/RealMeme/list.html");
                }else if(help.equals("form")){
                    helpWebView.loadUrl("https://ciscu24.github.io/RealMeme/form.html");
                }else if(help.equals("search")){
                    helpWebView.loadUrl("https://ciscu24.github.io/RealMeme/search.html");
                }
            }else{
                finish();
            }
        } else {
            // No hay conexión a Internet en este momento
            Toast.makeText(this, getString(R.string.internet), Toast.LENGTH_LONG)
                    .show();
            finish();
        }
    }
}