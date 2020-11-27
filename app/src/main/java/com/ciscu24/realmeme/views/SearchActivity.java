package com.ciscu24.realmeme.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        ArrayList<String> items = new ArrayList<>();
        items.add(getString(R.string.spinner_info));
        items.add(getString(R.string.spinner_add));
        items.add(getString(R.string.spinner_data_01));
        items.add(getString(R.string.spinner_data_02));
        items.add(getString(R.string.spinner_data_03));
        items.add(getString(R.string.spinner_data_04));

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position==1){
                    addCategory();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void SearchMeme() {
        finish();
    }

    public void addCategory(){
        LayoutInflater layoutActivity = LayoutInflater.from(myContext);
        View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);

        // Definición del AlertDialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

        // Asignación del AlertDialog a su vista
        alertDialog.setView(viewAlertDialog);

        // Recuperación del EditText del AlertDialog
        final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);

        //Configuracion del AlertDialog
        alertDialog.setCancelable(false).setPositiveButton(getResources().getString(R.string.add),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!dialogInput.getText().toString().equals("")){
                            adapter.add(dialogInput.getText().toString());
                            spinner.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                        }else{
                            Toast.makeText(myContext, R.string.toast_nothing, Toast.LENGTH_LONG).show();
                            spinner.setSelection(adapter.getPosition(getString(R.string.spinner_info)));
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                spinner.setSelection(adapter.getPosition(getString(R.string.spinner_info)));
                                dialogInterface.cancel();
                            }
                        })
                .create()
                .show();
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