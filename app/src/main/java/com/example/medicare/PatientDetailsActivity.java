package com.example.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class PatientDetailsActivity extends AppCompatActivity {

    TextView textViewFileNumber,textViewFullName,textViewDate,textViewAge,textViewGender,textViewBloodGroup,
    textViewAddress,textViewFullcity,textViewPhone,textViewEmergencyName,textViewEmergencyNumber,textViewReason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        getSupportActionBar().setTitle("Patient Detailed Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        textViewFileNumber=findViewById(R.id.textViewFileNumber);
        textViewFullName=findViewById(R.id.textViewFullName);
        textViewDate=findViewById(R.id.textViewDate);
        textViewAge=findViewById(R.id.textViewAge);
        textViewGender=findViewById(R.id.textViewGender);
        textViewBloodGroup=findViewById(R.id.textViewBloodGroup);
        textViewAddress=findViewById(R.id.textViewAddress);
        textViewFullcity=findViewById(R.id.textViewFullcity);
        textViewPhone=findViewById(R.id.textViewPhone);
        textViewEmergencyName=findViewById(R.id.textViewEmergencyName);
        textViewEmergencyNumber=findViewById(R.id.textViewEmergencyNumber);
        textViewReason=findViewById(R.id.textViewReason);


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            textViewFileNumber.setText("File Number : "+bundle.getString("file"));
            textViewFullName.setText("Full Name : "+bundle.getString("fname")+" "+bundle.getString("lname"));
            textViewDate.setText("Date Of Registration : "+bundle.getString("date"));
            textViewAge.setText("Age : "+bundle.getString("age"));
            textViewGender.setText("Gender : "+bundle.getString("gender"));
            textViewBloodGroup.setText("Blood Group : "+bundle.getString("blood"));
            textViewAddress.setText("Address : "+bundle.getString("address")+" #"+
                    bundle.getString("apt")+" ZipCode "+bundle.getString("zip"));
            textViewFullcity.setText("City : "+bundle.getString("city"));
            textViewPhone.setText("Registered Phone : "+bundle.getString("phone"));
            textViewEmergencyName.setText("Emergency Person Name : "+bundle.getString("emergencyName"));
            textViewEmergencyNumber.setText("Emergency Person Contact Number : "+bundle.getString("EmergencyNumber"));
            textViewReason.setText("Reason Mentioned while Registration : "+bundle.getString("reason"));



        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
