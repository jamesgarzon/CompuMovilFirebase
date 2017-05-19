package co.edu.udea.gr06_20171compumovil.lab4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by admin on 18/05/2017.
 */

public class AddEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private TextView nombre;
    private TextView descripcion;
    private TextView ubicacion;
    private RatingBar puntuacion;
    private TextView mDateDisplay;
    private TextView infoGeneral;
    private ImageButton imageSelect;
    private Button createButton;

    private int mYear;
    private int mMonth;
    private int mDay;

    private Uri mImageUri = null;

    private StorageReference mStorage;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevents);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Crear Evento");

        mStorage = FirebaseStorage.getInstance().getReference();

        nombre = (TextView) findViewById(R.id.etEventName);
        descripcion = (TextView) findViewById(R.id.etEventDescription);
        ubicacion = (TextView) findViewById(R.id.etEventUbicacion);
        puntuacion = (RatingBar) findViewById(R.id.rbEventPuntuacion);
        mDateDisplay = (TextView) findViewById(R.id.etEventDate);
        infoGeneral = (TextView) findViewById(R.id.etEventInfo);
        imageSelect = (ImageButton) findViewById(R.id.imageSelect);
        createButton = (Button) findViewById(R.id.btnCrearEvento);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
            }
        });

        imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

    }

    private void createEvent() {
        String nameEvent = nombre.getText().toString();
        String description = descripcion.getText().toString();
        String location = ubicacion.getText().toString();
        String date = mDateDisplay.getText().toString();
        String information = infoGeneral.getText().toString();
        Float punt = puntuacion.getRating();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            mImageUri= data.getData();

            imageSelect.setImageURI(mImageUri);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear;
        mDay = dayOfMonth;
        updateDisplay();
    }

    private void updateDisplay() {
        mDateDisplay.setText(new StringBuilder().append(mMonth + 1).append("/").append(mDay).append("/").append(mYear));
    }
}
