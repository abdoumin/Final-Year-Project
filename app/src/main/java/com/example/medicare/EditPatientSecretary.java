package com.example.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicare.model.NewPatient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPatientSecretary extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    TextView textViewFileNumber,textViewDate,textViewFullName,textViewAge,textViewGender,textViewBloodGroup;
    EditText editTextSearch,editTextAdress,editTextAptNumber,editTextCity,editTextZipCode,editTextPhoneNo,editTextEmergencyName
            ,editTextEmergencyNumber,editTextReason;
    Button btnSearch,btnEditPatientInfo;
    LinearLayout linearLayoutSearch,linearLayoutData;

    String SearchText;

    String Date;
    String FN ;
    String LN;
    String age;
    String gender;
    String address,newAdress;
    String aptno ,newAptNo;
    String city,newCity;
    String zip ,newZip;
    String phone,newPhone;
    String blood ;
    String EN ,newEN;
    String EC ,newEC;
    String reason ,newReason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_secretary);


        getSupportActionBar().setTitle("Edit Patient Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        initiliaze();
    }

    private void initiliaze() {

        textViewFileNumber=findViewById(R.id.textViewFileNumber);
        textViewFullName=findViewById(R.id.textViewFullName);
        textViewAge=findViewById(R.id.textViewAge);
        textViewGender=findViewById(R.id.textViewGender);
        textViewBloodGroup=findViewById(R.id.textViewBloodGroup);
        textViewDate=findViewById(R.id.textViewDate);


        editTextAdress=findViewById(R.id.editTextAdress);
        editTextAptNumber=findViewById(R.id.editTextAptNumber);
        editTextCity=findViewById(R.id.editTextCity);
        editTextZipCode=findViewById(R.id.editTextZipCode);
        editTextPhoneNo=findViewById(R.id.editTextPhoneNo);
        editTextEmergencyName=findViewById(R.id.editTextEmergencyName);
        editTextEmergencyNumber=findViewById(R.id.editTextEmergencyNumber);
        editTextReason=findViewById(R.id.editTextReason);

        linearLayoutSearch=findViewById(R.id.LinearLayoutSearch);
        linearLayoutData=findViewById(R.id.LinearLayoutData);


        editTextSearch=findViewById(R.id.editTextSearch);
        btnSearch=findViewById(R.id.btnSearch);

        btnEditPatientInfo=findViewById(R.id.btnEditPatientInfo);

        btnSearch.setOnClickListener(this);
        btnEditPatientInfo.setOnClickListener(this);



    }




    @Override
    public void onClick(View v) {
        if(v==btnSearch){
            SearchText = editTextSearch.getText().toString();

            if (SearchText.isEmpty()||SearchText.length()!=5) {
                editTextSearch.setError("Enter Valid File Number");
                btnSearch.requestFocus();
            }else{
                SearchPatient();
            }


        }else if(v==btnEditPatientInfo) {

            newAdress=editTextAdress.getText().toString();
            newAptNo=editTextAptNumber.getText().toString();
            newCity=editTextCity.getText().toString();
            newZip=editTextZipCode.getText().toString();
            newPhone=editTextPhoneNo.getText().toString();
            newEC=editTextEmergencyNumber.getText().toString();
            newEN=editTextEmergencyName.getText().toString();
            newReason=editTextReason.getText().toString();


            if (newAdress.isEmpty()) {
                editTextAdress.setError("Please Enter the Street Address");
                editTextAdress.requestFocus();

            } else if (newCity.isEmpty()) {
                editTextCity.setError("Please Enter the City");
                editTextCity.requestFocus();
            } else if (newZip.isEmpty() || newZip.length() != 6) {
                editTextZipCode.setError("Please Enter the ZipCode");
                editTextZipCode.requestFocus();
            } else if (newPhone.isEmpty() || newPhone.length() != 10) {
                editTextPhoneNo.setError("Please Enter the Phone Number");
                editTextPhoneNo.requestFocus();
            } else if (newEN.isEmpty()) {
                editTextEmergencyName.setError("Please Enter the Name");
                editTextEmergencyName.requestFocus();
            } else if (newEC.isEmpty() || newEC.length() != 10) {
                editTextEmergencyNumber.setError("Please Enter the Number");
                editTextEmergencyNumber.requestFocus();
            } else if (newReason.isEmpty()||editTextReason.getText().toString().equals("Not mentioned while Registration")) {
                newReason = "Not mentioned while Registration or data Updating";
                updatePatient();

            } else {
                updatePatient();
            }
        }

    }

    private void SearchPatient() {
        DatabaseReference FileNumber = FirebaseDatabase.getInstance().getReference().child("MediCare Data").
                child("Patients Info").child(String.valueOf(SearchText));

        FileNumber.addValueEventListener(this);


    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()){


            try {
                Date=dataSnapshot.child("Personal Info").child("date").getValue().toString();
                FN=dataSnapshot.child("Personal Info").child("firstName").getValue().toString();
                LN=dataSnapshot.child("Personal Info").child("lastName").getValue().toString();
                age=dataSnapshot.child("Personal Info").child("age").getValue().toString();
                gender=dataSnapshot.child("Personal Info").child("gender").getValue().toString();
                blood=dataSnapshot.child("Personal Info").child("bloodGroup").getValue().toString();

                address=dataSnapshot.child("Personal Info").child("streetAddress").getValue().toString();
                aptno=dataSnapshot.child("Personal Info").child("aptNumber").getValue().toString();
                city=dataSnapshot.child("Personal Info").child("city").getValue().toString();
                zip=dataSnapshot.child("Personal Info").child("zipCode").getValue().toString();
                phone=dataSnapshot.child("Personal Info").child("phoneNumber").getValue().toString();
                EN=dataSnapshot.child("Personal Info").child("emergencyContactName").getValue().toString();
                EC=dataSnapshot.child("Personal Info").child("emergencyContactNumber").getValue().toString();
                reason=dataSnapshot.child("Personal Info").child("reason").getValue().toString();


                textViewFileNumber.setText("File Number : "+SearchText);
                textViewDate.setText("Date of register : "+Date);
                textViewFullName.setText("Full Name : "+FN+" "+LN);
                textViewAge.setText("Age : "+age);
                textViewGender.setText("Gender : "+gender);
                textViewBloodGroup.setText("Blood Group : "+blood);
                editTextAdress.setText(address);
                editTextAptNumber.setText(aptno);
                editTextCity.setText(city);
                editTextZipCode.setText(zip);
                editTextPhoneNo.setText(phone);
                editTextEmergencyNumber.setText(EC);
                editTextEmergencyName.setText(EN);
                editTextReason.setText(reason);


                //Toast.makeText(this,"File Number exist",Toast.LENGTH_LONG).show();
                linearLayoutSearch.setVisibility(View.GONE);
                linearLayoutData.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else{
            editTextSearch.setError("File Number doesn't exist");

        }
    }





    private void updatePatient() {




        NewPatient newPatient = new NewPatient(FN,LN,age,gender,null,null);




        FirebaseDatabase.getInstance().getReference("MediCare Data").child("Patients Info")
                .child(String.valueOf(SearchText)).child("Personal Info").setValue(newPatient).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(EditPatientSecretary.this,"Details Updated",Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditPatientSecretary.this,"Failed to Update",Toast.LENGTH_LONG).show();

            }
        });;


    }





    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
