package com.example.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicare.api.model.Patient;
import com.example.medicare.api.service.PatientClient;
import com.example.medicare.model.Doctor;
import com.example.medicare.model.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    TextView TextViewAlreadyRegistered;
    EditText editTextFirstName,editTextlastName,editTextUsername,editTextEmail,editTextAge,editTextGender,editTextPassword,editTextPasswordConfirm;
    RadioGroup radioGroup_Register;
    RadioButton radioButtonDoctor, radioButtonSecretary;
    Button buttonRegister;
    FirebaseAuth fAuth;

    String BASE_URL = "http://10.0.2.2:8081";
    String userID,firstName,lastName,age,gender,email,phone,password,PasswordConfirm,Type="Doctor",Username;
    Register register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);





        TextViewAlreadyRegistered= findViewById(R.id.TextViewAlreadyRegistered);

        editTextFirstName= findViewById(R.id.editTextFirstName);
        editTextlastName= findViewById(R.id.editTextlastName);
        editTextUsername = findViewById(R.id.editUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAge = findViewById(R.id.editTextAge);
        editTextGender = findViewById(R.id.editTextGender);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);




        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
        TextViewAlreadyRegistered.setOnClickListener(this);

        fAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {

        if(v==buttonRegister) {

            firstName= editTextFirstName.getText().toString();
            lastName = editTextlastName.getText().toString();
            Username = editTextUsername.getText().toString();
            age= editTextAge.getText().toString();
            gender = editTextGender.getText().toString();
            email = editTextEmail.getText().toString();
            password = editTextPassword.getText().toString();
            PasswordConfirm=editTextPasswordConfirm.getText().toString();



            if (firstName.isEmpty()) {
                editTextFirstName.setError("Please Enter the First Name");
                editTextFirstName.requestFocus();
            }
            else if (lastName.isEmpty()) {
                editTextlastName.setError("Please Enter the First Name");
                editTextlastName.requestFocus();
            }
            else if (age.isEmpty()) {
                editTextAge.setError("Please Enter the First Name");
                editTextAge.requestFocus();
            }
            else if (gender.isEmpty()) {
                editTextGender.setError("Please Enter the First Name");
                editTextGender.requestFocus();
            }else if(email.isEmpty()){
                editTextEmail.setError("Please Enter the Email Adress");
                editTextEmail.requestFocus();}
            else if(Username.isEmpty()){
                    editTextUsername.setError("Please Enter the Username");
                    editTextUsername.requestFocus();
            }else if(password.isEmpty()||password.length()<6){
                editTextPassword.setError("Please Enter the valid Password");
                editTextPassword.requestFocus();
            }else if(PasswordConfirm.isEmpty()){
                editTextPasswordConfirm.setError("Please Enter the confirmed password");
                editTextPasswordConfirm.requestFocus();
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editTextEmail.setError("Please Enter the valid Email Adress");
                editTextEmail.requestFocus();
            }else if (!password.matches(PasswordConfirm)){
                editTextPasswordConfirm.setError("Passwords Doesn't Match");
                editTextPasswordConfirm.requestFocus();
            }else {


                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Doctor newDoctor = new Doctor(firstName,lastName,age,gender,
                                    Username,password,email);
                            Retrofit.Builder builder = new Retrofit.Builder()
                                    .baseUrl(URLClass.DeviceAddress)
                                    .addConverterFactory(GsonConverterFactory.create());

                            Retrofit retrofit = builder.build();
                            PatientClient client = retrofit.create(PatientClient.class);
                            Call<Doctor> call = client.createDoctor(newDoctor);
                            call.enqueue(new Callback<Doctor>() {
                                @Override
                                public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                                    if(response.isSuccessful() && response.body()!=null)
                                    {
                                        Toast.makeText(RegisterActivity.this,"User ID: "+response.body().getId(),Toast.LENGTH_SHORT).show();
                                        finish();

                                    }
                                }

                                @Override
                                public void onFailure(Call<Doctor> call, Throwable t) {
                                    t.printStackTrace();

                                }
                            });

                            userID = fAuth.getCurrentUser().getUid();
                            register=new Register(firstName,lastName,email,Type);
                            doctor();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        }else if(v==TextViewAlreadyRegistered){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
    }




    private void doctor() {
        FirebaseDatabase.getInstance().getReference("MediCare Data").child("Doctor Info")
                .child(userID).setValue(register).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){


                    FirebaseUser fuser = fAuth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();

                    Intent i =new Intent(RegisterActivity.this,DoctorActivity.class);
                    i.putExtra("FullName",firstName + " " + lastName);
                    startActivity(i);
                    finish();

                }else {
                    Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
