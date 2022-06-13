package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

TextView textviewName;
    Button btnRegisterDoctor, btnAddNewPatient, btnEditInformation, btnDocotrsList,btnLogout, btnPatientList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secretary);

        textviewName=findViewById(R.id.textviewName);
        Bundle mBundle=getIntent().getExtras();
        if(mBundle!=null) {
            String fullName = mBundle.getString("FullName");
            textviewName.setText("Welcome "+fullName);
            textviewName.setTextColor(getResources().getColor(R.color.btncolor));
        }



        btnRegisterDoctor = findViewById(R.id.btnRegisterDoctor);
        btnAddNewPatient = findViewById(R.id.btnAddNewPatient);
        btnEditInformation = findViewById(R.id.btnEditInformation);
        btnDocotrsList = findViewById(R.id.btnDocotrsList);
        btnPatientList = findViewById(R.id.btnPatientList);
        btnLogout=findViewById(R.id.btnLogout);

        btnPatientList.setOnClickListener(this);
        btnDocotrsList.setOnClickListener(this);
        btnEditInformation.setOnClickListener(this);
        btnAddNewPatient.setOnClickListener(this);
        btnRegisterDoctor.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnPatientList:
                startActivity(new Intent(getApplicationContext(),ListOfPatientsSec.class));
                break;
            case R.id.btnEditInformation:
                startActivity(new Intent(getApplicationContext(),EditPatientSecretary.class));
                break;
            case R.id.btnDocotrsList:
                startActivity(new Intent(getApplicationContext(),DoctorListActivity.class));
                break;
            case R.id.btnAddNewPatient:
                startActivity(new Intent(getApplicationContext(),AddNewPatientActivity.class));
                break;
            case R.id.btnRegisterDoctor:
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                break;
            case R.id.btnLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }

    }
}
