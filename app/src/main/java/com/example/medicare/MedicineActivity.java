package com.example.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicare.api.model.Patient;
import com.example.medicare.api.service.PatientClient;
import com.example.medicare.model.Medication;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedicineActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    TextView textViewFileNumber,textViewFullName,textViewDate;

    EditText editTextSearch,editTextMedicineName,editTextFrequency,editTextDosage,editTextDays;
    Button btnSearch,btnAddMedicine;
    RadioGroup radioGroupMeal;
    RadioButton radioButtonBM,radioButtonAM;
    LinearLayout LinearLayoutSearch,LinearLayoutData;

    String BASE_URL = "http://10.0.2.2:8081";

    String SearchText;
    String FN,LN,Datee,medicineName,frequency,dosage,meal,endDate,days;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);


        getSupportActionBar().setTitle("Medicine");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);





        initiliaze();



    }

    private void initiliaze() {

        textViewFileNumber=findViewById(R.id.textViewFileNumber);
        textViewFullName=findViewById(R.id.textViewFullName);
        textViewDate=findViewById(R.id.textViewDate);

        editTextSearch=findViewById(R.id.editTextSearch);
        editTextMedicineName=findViewById(R.id.editTextMedicineName);
        editTextFrequency=findViewById(R.id.editTextFrequency);
        editTextDosage=findViewById(R.id.editTextDosage);
        editTextDays=findViewById(R.id.editTextDays);

        btnSearch=findViewById(R.id.btnSearch);
        btnAddMedicine=findViewById(R.id.btnAddHeure);
        btnSearch.setOnClickListener(this);
        btnAddMedicine.setOnClickListener(this);

        radioGroupMeal=findViewById(R.id.radioGroupMeal);
        radioButtonBM=findViewById(R.id.radioButtonBM);
        radioButtonAM=findViewById(R.id.radioButtonAM);

        LinearLayoutSearch=findViewById(R.id.LinearLayoutSearch);
        LinearLayoutData=findViewById(R.id.LinearLayoutData);

        Datee = DateFormat.getDateInstance(DateFormat.DEFAULT).format(Calendar.getInstance().getTime());


    }

    @Override
    public void onClick(View v) {
        if(v==btnSearch){

            SearchText = editTextSearch.getText().toString();

            if (SearchText.isEmpty()) {
                editTextSearch.setError("Enter Valid File Number");
                btnSearch.requestFocus();
            }else{
                SearchPatient();

            }

        }else if(v==btnAddMedicine) {

            int Meal = radioGroupMeal.getCheckedRadioButtonId();
            meal = null;
            try {
                switch (Meal) {
                    case R.id.radioButtonBM:
                        meal = "Before Meal";
                        break;
                    case R.id.radioButtonAM:
                        meal = "After Meal";
                        break;
                    default:
                        break;
                }
            }catch (NumberFormatException e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }


            medicineName = editTextMedicineName.getText().toString();
            frequency = editTextFrequency.getText().toString();
            dosage = editTextDosage.getText().toString();
            days = editTextDays.getText().toString();



            if (medicineName.isEmpty()) {
                editTextMedicineName.setError("Please Enter the Medicine Name");
                editTextMedicineName.requestFocus();
            }else if (frequency.isEmpty()||frequency.length()>10) {
                editTextFrequency.setError("Please Enter the frequency of tablets");
                editTextFrequency.requestFocus();

            }else if (dosage.isEmpty()||dosage.length()>5) {
                editTextDosage.setError("Please Enter the dosage per day");
                editTextDosage.requestFocus();

            }else if (radioGroupMeal.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this,"Choose a option for taking medicine",Toast.LENGTH_LONG).show();

            }else if (days.isEmpty()||days.length()>30) {
                editTextDosage.setError("Please Enter the days to continue");
                editTextDosage.requestFocus();
            }else{

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MMMM dd,yyyy");
                String reg_date = df.format(c.getTime());
                int day=Integer.valueOf(days);
                c.add(Calendar.DATE, day);
                endDate = df.format(c.getTime());



                popup();





            }

        }
    }


    private void SearchPatient() {
        /*DatabaseReference FileNumber = FirebaseDatabase.getInstance().getReference().child("MediCare Data").
                child("Patients Info").child(String.valueOf(SearchText));

        FileNumber.addValueEventListener(this);*/

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URLClass.DeviceAddress)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        PatientClient client = retrofit.create(PatientClient.class);
        Call<Patient> call = client.findPatientByid(Integer.parseInt(SearchText));
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    FN = response.body().getFirstName();
                    LN = response.body().getLastName();
                    textViewFileNumber.setText("File Number : " + SearchText);
                    textViewFullName.setText("Patient Name : " + FN + " " + LN);


                    textViewDate.setText("Date Of Assign : "+Datee);



                    LinearLayoutSearch.setVisibility(View.GONE);
                    LinearLayoutData.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                t.printStackTrace();
                editTextSearch.setError("File Number doesn't exist");

            }
        });


    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()) {

            FN = dataSnapshot.child("Personal Info").child("firstName").getValue().toString();
            LN = dataSnapshot.child("Personal Info").child("lastName").getValue().toString();
            textViewFileNumber.setText("File Number : " + SearchText);
            textViewFullName.setText("Patient Name : " + FN + " " + LN);


            textViewDate.setText("Date Of Assign : "+Datee);



            LinearLayoutSearch.setVisibility(View.GONE);
            LinearLayoutData.setVisibility(View.VISIBLE);
        }else {
            editTextSearch.setError("File Number doesn't exist");
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }



    private void popup() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(MedicineActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.medicine_dialoge,null);


        final TextView textViewMedicineNameDia=(TextView)mView.findViewById(R.id.textViewMedicineNameDia);
        final TextView textViewStartDate=(TextView)mView.findViewById(R.id.prescription_refilldata);
        final TextView textViewDosageDia=(TextView)mView.findViewById(R.id.textViewDosageDia);
        final TextView textViewMealDia=(TextView)mView.findViewById(R.id.textViewMealDia);
        final TextView textViewEndDateDia=(TextView)mView.findViewById(R.id.textViewEndDateDia);



        textViewMedicineNameDia.setText("Medicine Name : "+medicineName);
        textViewStartDate.setText("Start Medicine on : "+Datee);
        textViewDosageDia.setText("Take "+frequency+" tablet's "+dosage+" time's a day");
        textViewMealDia.setText("Take medicine "+meal);
        textViewEndDateDia.setText("Take medicine till : "+endDate);



        Button btnConfirm =(Button)mView.findViewById(R.id.btnAddDia);

        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);




        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Medication medicine = new Medication(Datee,medicineName,frequency,dosage,meal,days,endDate);
                //

                String myCurrentDateTime = DateFormat.getDateTimeInstance()
                        .format(Calendar.getInstance().getTime());

                /*FirebaseDatabase.getInstance().getReference("MediCare Data").child("Patients Info")
                        .child(String.valueOf(SearchText)).child("Medicine Details").child(myCurrentDateTime)
                        .setValue(medicine).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(MedicineActivity.this,"Medicine Added",Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MedicineActivity.this,"Failed !!",Toast.LENGTH_LONG).show();

                    }
                });*/




            }
        });
        alertDialog.show();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
