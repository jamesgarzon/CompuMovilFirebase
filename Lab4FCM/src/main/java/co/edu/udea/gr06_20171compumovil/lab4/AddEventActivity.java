package co.edu.udea.gr06_20171compumovil.lab4;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import co.edu.udea.gr06_20171compumovil.lab4.fragments.DateFragmentPicker;

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
    private TextView responsable;
    private ImageButton imageSelect;
    private Button createButton;
    private Button insertDate;

    private int mYear;
    private int mMonth;
    private int mDay;

    private Uri mImageUri = null;

    private StorageReference mStorage;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private static final int GALLERY_REQUEST = 1;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevents);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Crear Evento");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("events");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        nombre = (TextView) findViewById(R.id.etEventName);
        descripcion = (TextView) findViewById(R.id.etEventDescription);
        ubicacion = (TextView) findViewById(R.id.etEventUbicacion);
        puntuacion = (RatingBar) findViewById(R.id.rbEventPuntuacion);
        mDateDisplay = (TextView) findViewById(R.id.etEventDate);
        infoGeneral = (TextView) findViewById(R.id.etEventInfo);
        responsable = (TextView) findViewById(R.id.etResponsible);
        imageSelect = (ImageButton) findViewById(R.id.imageSelect);
        createButton = (Button) findViewById(R.id.btnCrearEvento);
        insertDate = (Button) findViewById(R.id.btnFecha);

        mProgressDialog = new ProgressDialog(this);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
            }
        });

        insertDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DateFragmentPicker();
                datePickerFragment.show(getFragmentManager(), "datePicker");
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
        mProgressDialog.setMessage(("creando evento..."));
        mProgressDialog.show();

        final String nameEvent = nombre.getText().toString();
        final String description = descripcion.getText().toString();
        final String location = ubicacion.getText().toString();
        final String date = mDateDisplay.getText().toString();
        final String information = infoGeneral.getText().toString();
        final String responsible = responsable.getText().toString();
       final Float punt = puntuacion.getRating();

        if (!TextUtils.isEmpty(nameEvent) && !TextUtils.isEmpty(description) && punt != 0 && mImageUri != null){
            StorageReference filePath = mStorage.child("Event_Images").child(mImageUri.getLastPathSegment());

            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    final Uri downloadUri = taskSnapshot.getDownloadUrl();
                    final DatabaseReference newEvent = mDatabaseReference.push();

                    mDatabaseUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            newEvent.child("name").setValue(nameEvent);
                            newEvent.child("description").setValue(description);
                            newEvent.child("location").setValue(location);
                            newEvent.child("date").setValue(date);
                            newEvent.child("info").setValue(information);
                            newEvent.child("responsible").setValue(responsible);
                            newEvent.child("score").setValue(punt);
                            newEvent.child("picture").setValue(downloadUri.toString());
                            newEvent.child("uid").setValue(mCurrentUser.getUid());

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Hubo un error", Toast.LENGTH_SHORT).show();

                        }
                    });
                    Intent intent = new Intent(AddEventActivity.this, EventsActivity.class);
                    startActivity(intent);
                    mProgressDialog.dismiss();

                }
            });
        }else {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
        }

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
