package com.example.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicare.api.model.Patient;
import com.example.medicare.api.service.PatientClient;
import com.example.medicare.model.NewPatient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNewPatientActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    TextView textViewFileNumber, textViewDate;
    EditText editTextFirstName, editTextLastName, editTextAge, editTextUsername, editTextAptNumber, editTextCity, editTextZipCode, editTextPhoneNo, editTextEmergencyName, editTextEmergencyNumber, editTextReason;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale, radioButtonUnspecified;
    Button btnAddNewPatient;

    String BASE_URL = "http://10.0.2.2:8081";

    Spinner spinnerBloodGroup;

    ProgressDialog progressDialog;


    final Random random = new Random();

    String fileNumber;
    String Username;
    String Date;
    String FN;
    String LN;
    String age;
    String Gender;
    String address;
    String aptno;
    String city;
    String zip;
    String phone;
    String blood;
    String EN;
    String EC;
    String reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_patient);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Patient info...");


        getSupportActionBar().setTitle("New Patient Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        initiliaze();
    }


    private void initiliaze() {

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextUsername = findViewById(R.id.editTextUsername);




        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioButtonUnspecified = findViewById(R.id.radioButtonUnspecified);

        btnAddNewPatient = findViewById(R.id.btnAddNewPatient);
        btnAddNewPatient.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == btnAddNewPatient) {

            progressDialog.show();


            FN = editTextFirstName.getText().toString();
            LN = editTextLastName.getText().toString();
            age = editTextAge.getText().toString();
            Username = editTextUsername.getText().toString();



            int gender = radioGroupGender.getCheckedRadioButtonId();
            Gender = null;
            try {
                switch (gender) {
                    case R.id.radioButtonMale:
                        Gender = "Male";
                        break;
                    case R.id.radioButtonFemale:
                        Gender = "Female";
                        break;
                    case R.id.radioButtonUnspecified:
                        Gender = "UnSpecified";
                        break;
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }


            if (FN.isEmpty()) {
                editTextFirstName.setError("Please Enter the First Name");
                editTextFirstName.requestFocus();
                progressDialog.dismiss();
            } else if (LN.isEmpty()) {
                editTextLastName.setError("Please Enter the Last Name");
                editTextLastName.requestFocus();
                progressDialog.dismiss();
            }
            else if (Username.isEmpty()) {
                editTextUsername.setError("Please Enter the Username");
                editTextUsername.requestFocus();
                progressDialog.dismiss();
            } else if (age.isEmpty() || Integer.valueOf(age) >= 100 || Integer.valueOf(age) <= 0) {
                editTextAge.setError("Please Enter the Age");
                editTextAge.requestFocus();
                progressDialog.dismiss();
            } else if (radioGroupGender.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Choose a Gender", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            } else {

                AddingNewPatient();
                progressDialog.dismiss();


            }
        }


    }

    private void AddingNewPatient() {
        Patient newPatient = new Patient(FN,
                LN,
                Username,
                null,
                age,
                Gender
                );

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URLClass.DeviceAddress)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        PatientClient client = retrofit.create(PatientClient.class);
        Call<Patient> call = client.createPatient(newPatient);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    Toast.makeText(AddNewPatientActivity.this,"User ID: "+response.body().getId(),Toast.LENGTH_SHORT).show();
                    finish();

                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.d("ErrorAddingPatient","Error");
                t.printStackTrace();

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed() {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("All info will be lost");
        alertdialog.setMessage("");
        alertdialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddNewPatientActivity.super.onBackPressed();

            }
        });
        alertdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });
        AlertDialog alert = alertdialog.create();
        alertdialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
