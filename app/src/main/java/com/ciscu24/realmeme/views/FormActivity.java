package com.ciscu24.realmeme.views;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.ciscu24.realmeme.R;
import com.ciscu24.realmeme.interfaces.FormInterface;
import com.ciscu24.realmeme.models.MemeEntity;
import com.ciscu24.realmeme.presenters.FormPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Base64;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity implements FormInterface.View {

    private FormInterface.Presenter presenter;
    private Context myContext;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private String id;
    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private static final int REQUEST_SELECT_IMAGE = 201;
    private ConstraintLayout constraintLayoutFromActivity;
    private Uri uri;

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

    private Switch favSwitch;

    private Button cleanImageButton;
    private ImageView imageMeme;

    private Button deleteMemeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        presenter = new FormPresenter(this);
        myContext = this;
        constraintLayoutFromActivity = findViewById(R.id.constraintLayoutForm);

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

        deleteMemeButton = findViewById(R.id.DeleteFormButton);

        favSwitch = findViewById(R.id.FavSwitch);

        spinner = (Spinner) findViewById(R.id.CategorySpinner);
        ArrayList<String> items = presenter.getCategoriesRealm();

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

                if (!meme.setName(nameText.getText().toString())) {
                    nameInputLayout.setError(presenter.getError("name"));
                }
                if (!meme.setDescription(descriptionText.getText().toString())) {
                    descriptionInputLayout.setError(presenter.getError("description"));
                }
                if (!meme.setAuthor(authorText.getText().toString())) {
                    authorInputLayout.setError(presenter.getError("author"));
                }
                if (likeText.getText().toString().equals("") || !meme.setLike(likeText.getText().toString())) {
                    likeInputLayout.setError(presenter.getError("like"));
                }
                if (!meme.setDate(dateText.getText().toString())) {
                    dateInputLayout.setError(presenter.getError("date"));
                }
                if (spinner.getSelectedItemPosition() == 0){
                    TextView errorText = (TextView)spinner.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);
                    errorText.setText("");
                }


                if(meme.setName(nameText.getText().toString()) &&
                        meme.setDescription(descriptionText.getText().toString()) &&
                        meme.setAuthor(authorText.getText().toString()) &&
                        meme.setLike(likeText.getText().toString()) &&
                        meme.setDate(dateText.getText().toString()) &&
                        spinner.getSelectedItemPosition()!=0){

                    meme.setFav(favSwitch.isChecked());
                    meme.setCategory(spinner.getSelectedItem().toString());

                    if(imageMeme.getDrawable() == null || ((BitmapDrawable)imageMeme.getDrawable()).getBitmap() == null){
                        meme.setImage("");
                    }else{
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ((BitmapDrawable)imageMeme.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String imageInBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        meme.setImage(imageInBase64);
                    }

                    if(id!=null){
                        meme.setId(id);
                        presenter.onClickSaveButton(meme);
                    }else{
                        meme.setId("");
                        presenter.onClickSaveButton(meme);
                    }

                    System.out.println("true");
                }else{
                    showErrorWithToast(getString(R.string.filedsMissing));
                    System.out.println("false");
                }
            }
        });

        deleteMemeButton.setOnClickListener(new View.OnClickListener() {
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

        imageMeme = findViewById(R.id.imageMemeForm);

        imageMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickImageView();
            }
        });

        cleanImageButton = findViewById(R.id.CleanImageButton);

        cleanImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickCleanImage();
            }
        });

        id = getIntent().getStringExtra("id");

        if(id != null){
            deleteMemeButton.setBackgroundColor(getResources().getColor(R.color.background_delete_button));

            //Recuperar la info de esa entidad
            MemeEntity memeLoad = presenter.getMemeById(id);
            nameText.setText(memeLoad.getName());
            descriptionText.setText(memeLoad.getDescription());
            authorText.setText(memeLoad.getAuthor());
            likeText.setText(memeLoad.getLike()+"");
            favSwitch.setChecked(memeLoad.isFav());

            SimpleDateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");
            dateText.setText(newDate.format(memeLoad.getDate())+"");


            if(!memeLoad.getImage().equals("")){
                imageMeme.setBackground(null);
                byte[] decodedString = Base64.decode(memeLoad.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageMeme.setImageBitmap(decodedByte);
            }else{
                imageMeme.setImageDrawable(MyApplication.getContext().getDrawable(R.drawable.default_image));
            }

            spinner.setSelection(getIndex(spinner, memeLoad.getCategory()));

        }else{
            // Deshabilitar el boton eliminar
            deleteMemeButton.setEnabled(false);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) SaveButton.getLayoutParams();
            params.leftMargin = 0;
            SaveButton.setLayoutParams(params);
            deleteMemeButton.setVisibility(View.GONE);
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
            presenter.onClickHelpToolbar();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void SaveMeme() {
        finish();
    }

    @Override
    public void DeleteMeme() {
        Toast.makeText(FormActivity.this, getString(R.string.deleteMeme), Toast.LENGTH_LONG)
                .show();
        finish();
    }

    @Override
    public void selectImageFromGallery() {
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.choose_picture)),
                REQUEST_SELECT_IMAGE);
    }

    @Override
    public void cleanImage() {
        imageMeme.setImageBitmap(null);
        imageMeme.setBackground(getDrawable(R.drawable.default_image));
    }

    @Override
    public void showErrorPermissionDenied() {
        Snackbar.make(constraintLayoutFromActivity, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showRequestPermission() {
        // Permiso denegado
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(FormActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
            // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
            // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
        }else{
            Snackbar.make(constraintLayoutFromActivity, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void startHelpFormActivity() {
        Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
        intent.putExtra("help", "form");
        startActivity(intent);
    }

    @Override
    public void showErrorWithToast(String text) {
        Toast.makeText(myContext, text, Toast.LENGTH_LONG).show();
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
                presenter.onClickAcceptDeleteButton(id);
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

    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQUEST_SELECT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto Bitmap
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        // Se leen los bytes de la imagen
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Se transformam los bytes de la imagen a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                        Bitmap imageScaled = Bitmap.createScaledBitmap(bmp, 200, 200, false);

                        // Quitamos del ImagenView el background
                        imageMeme.setBackground(null);

                        // Se carga el Bitmap en el ImageView
                        imageMeme.setImageBitmap(bmp);
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            presenter.permissionGranted();
        } else {
            //Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            presenter.permissionDenied();
        }
    }
}