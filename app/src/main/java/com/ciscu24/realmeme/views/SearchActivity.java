package com.ciscu24.realmeme.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.ciscu24.realmeme.interfaces.SearchInterface;
import com.ciscu24.realmeme.presenters.SearchPresenter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ciscu24.realmeme.R;

import java.util.ArrayList;
import java.util.Calendar;

public class SearchActivity extends AppCompatActivity implements SearchInterface.View {

    private SearchInterface.Presenter presenter;
    private Context myContext;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    EditText nameText;
    EditText dateText;

    ImageView dateImageView;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new SearchPresenter(this);
        myContext = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinner = (Spinner) findViewById(R.id.CategorySpinnerSearch);
        ArrayList<String> items = presenter.getCategoriesRealm();

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        nameText = findViewById(R.id.NameTextSearch);

        dateText = findViewById(R.id.DateTextSearch);
        dateImageView = findViewById(R.id.DateImageSearch);
        dateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });

        Button SearchButton = findViewById(R.id.SearchButton);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickSearchButton();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help_search) {
            presenter.onClickHelpToolbar();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void SearchMeme() {
        Intent i = getIntent();
        i.putExtra("name", nameText.getText().toString());
        i.putExtra("date", dateText.getText().toString());
        i.putExtra("spinner", spinner.getSelectedItemId());
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void startHelpSearchActivity() {
        Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
        intent.putExtra("help", "search");
        startActivity(intent);
    }

    public void datePicker(){
        // Obtener la fecha actual
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        // Definir el calendario con la fecha seleccionada por defecto
        datePickerDialog = new DatePickerDialog(myContext, new DatePickerDialog.OnDateSetListener() {
            // Definir la acción al pulsar OK en el calendario
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                // Asignar la fecha a un campo de texto
                dateText.setText(String.valueOf(day) + "/" + String.valueOf(month+1) + "/" + String.valueOf(year));
            }
        },Year, Month, Day);
        // Mostrar el calendario
        datePickerDialog.show();
    }
}