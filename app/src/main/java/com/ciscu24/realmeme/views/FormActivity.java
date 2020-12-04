package com.ciscu24.realmeme.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.ciscu24.realmeme.R;
import com.ciscu24.realmeme.interfaces.FormInterface;
import com.ciscu24.realmeme.models.MemeEntity;
import com.ciscu24.realmeme.presenters.FormPresenter;
import com.google.android.material.textfield.TextInputLayout;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity implements FormInterface.View {

    private FormInterface.Presenter presenter;
    private Context myContext;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private String id;

    private MemeEntity meme;

    private EditText nameText;
    private TextInputLayout nameInputLayout;

    private EditText descriptionText;
    private TextInputLayout descriptionInputLayout;

    private  EditText authorText;
    private  TextInputLayout authorInputLayout;

    private  EditText likeText;
    private  TextInputLayout likeInputLayout;

    private EditText dateText;
    private TextInputLayout dateInputLayout;
    private ImageView dateImageView;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        presenter = new FormPresenter(this);
        myContext = this;

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

        spinner = (Spinner) findViewById(R.id.CategorySpinner);
        ArrayList<String> items = new ArrayList<>();
        items.add(getString(R.string.spinner_info));
        items.add(getString(R.string.spinner_add));
        items.add(getString(R.string.spinner_data_01));
        items.add(getString(R.string.spinner_data_02));
        items.add(getString(R.string.spinner_data_03));
        items.add(getString(R.string.spinner_data_04));

        adapter = new ArrayAdapter<String>(myContext, R.layout.support_simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position==1){
                    addCategory();
                }
                if(position==0){
                    //TextView errorText = (TextView)spinner.getSelectedView();
                    //errorText.setError("");
                    //errorText.setTextColor(Color.RED);
                    //errorText.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        Button SaveButton = findViewById(R.id.SaveFormButton);

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickSaveButton();
            }
        });

        Button DeleteButton = findViewById(R.id.DeleteFormButton);

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog();
            }
        });

        meme = new MemeEntity();

        nameText = findViewById(R.id.NameTextForm);
        nameInputLayout = findViewById(R.id.NameInputForm);
        nameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    //Log.d("FormActivity", "Exit EditText");
                    if (!meme.setName(nameText.getText().toString())) {
                        nameInputLayout.setError(presenter.getError("name"));
                    } else {
                        nameInputLayout.setError("");
                    }
                }else{
                    //Log.d("FormActivity", "Input EditText");
                }
            }
        });

        descriptionText = findViewById(R.id.DescriptionTextForm);
        descriptionInputLayout = findViewById(R.id.DescriptionInputForm);
        descriptionText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if (!meme.setDescription(descriptionText.getText().toString())) {
                        descriptionInputLayout.setError(presenter.getError("description"));
                    } else {
                        descriptionInputLayout.setError("");
                    }
                }else{
                    //Log.d("FormActivity", "Input EditText");
                }
            }
        });

        authorText = findViewById(R.id.AuthorTextForm);
        authorInputLayout = findViewById(R.id.AuthorInputForm);
        authorText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if (!meme.setAuthor(authorText.getText().toString())) {
                        authorInputLayout.setError(presenter.getError("author"));

                    } else {
                        authorInputLayout.setError("");
                    }
                }else{
                    //Log.d("FormActivity", "Input EditText");
                }
            }
        });

        likeText = findViewById(R.id.LikeTextForm);
        likeInputLayout = findViewById(R.id.LikeInputForm);
        likeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if (likeText.getText().toString().equals("") || !meme.setLike(likeText.getText().toString())) {
                        likeInputLayout.setError(presenter.getError("like"));

                    } else {
                        likeInputLayout.setError("");
                    }
                }else{
                    //Log.d("FormActivity", "Input EditText");
                }
            }
        });

        dateText = findViewById(R.id.DateTextForm);
        dateInputLayout = findViewById(R.id.DateInputForm);
        dateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if (!meme.setDate(dateText.getText().toString())) {
                        dateInputLayout.setError(presenter.getError("date"));

                    } else {
                        dateInputLayout.setError("");
                    }
                }else{
                    //Log.d("FormActivity", "Input EditText");
                }
            }
        });

        dateImageView = findViewById(R.id.DateImage);
        dateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });

        id = getIntent().getStringExtra("id");

        if(id != null){
            //Recuperar la info de esa entidad
            nameText.setText(id);
        }else{
            // Deshabilitar el boton eliminar
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help_form) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void SaveMeme() {
        finish();
    }

    @Override
    public void DeleteMeme() {
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

    public void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setTitle(R.string.delete_meme_dialog_tittle);
        builder.setMessage(R.string.delete_meme_dialog_message);

        //Accept Button
        builder.setPositiveButton(R.string.form_button_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.onClickAcceptDeleteButton();
            }
        });

        //Cancel Button
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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