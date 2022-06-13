package com.example.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicare.api.service.PatientClient;
import com.example.medicare.model.Medication;
import com.example.medicare.model.Prescription;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog;
//    String presId;
    RecyclerView recyclerView;
    List<Prescription> Prescriptions;
    Button add_prescription,use_model;

    String BASE_URL = "http://10.0.2.2:8081";

    TextView textViewFileNumber,textViewFullName,textViewDate;

    EditText editTextSearch,editTextMedicineName,editTextFrequency,editTextDosage,editTextDays;
    Button btnSearch,btnAddMedicine;
    RadioGroup radioGroupMeal;
    RadioButton radioButtonBM,radioButtonAM;
    LinearLayout LinearLayoutSearch,LinearLayoutData;

    String SearchText;
    String FN,LN,Datee,medicineName,frequency,dosage,meal,endDate,days;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriptions);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Items..");
        getSupportActionBar().setTitle("Patients List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        add_prescription = findViewById(R.id.add_medicine0);
        use_model = findViewById(R.id.use_model);
        use_model.setOnClickListener(this);
        add_prescription.setOnClickListener(this);



        initiliaze();



    }



    private void initiliaze() {

        Prescriptions = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(PrescriptionActivity.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);



        PrescriptionAdapter prescriptionAdapter = new PrescriptionAdapter(PrescriptionActivity.this,Prescriptions);
        recyclerView.setAdapter(prescriptionAdapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        PatientClient client = retrofit.create(PatientClient.class);
        Call<List<Prescription>> call = client.findPrescriptionsByDoctorToPatient("abdoumin",getIntent().getStringExtra("username"));
        call.enqueue(new Callback<List<Prescription>>() {
            @Override
            public void onResponse(Call<List<Prescription>> call, Response<List<Prescription>> response) {
                if(response.isSuccessful() /*&& response.body()!=null*/)
                {
                    Prescriptions.addAll(response.body());
                    prescriptionAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Prescription>> call, Throwable t) {
                Log.d("PrescriAdapter","ERROR");
                t.printStackTrace();

            }
        });

        Datee = DateFormat.getDateInstance(DateFormat.DEFAULT).format(Calendar.getInstance().getTime());


    }

    @Override
    public void onClick(View v) {
        if(v==btnAddMedicine) {

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

        if(v==add_prescription)
        {
            Intent intent=new Intent(PrescriptionActivity.this,PrescriptionList.class);
            /*LocalDate date = LocalDate.now();*/
            Prescription prescription = new Prescription();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            PatientClient client = retrofit.create(PatientClient.class);
            String ptUsername = getIntent().getStringExtra("username");
            Call<Prescription> call = client.createPrescription("abdoumin",ptUsername,prescription);

            Response<Prescription> response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Prescription pres = response.body();
            String presID = String.valueOf(pres.getId());

            intent.putExtra("username",ptUsername);
            intent.putExtra("presID",presID);

            startActivity(intent);
            /*finish();*/



        }

        if(v==use_model)
        {
            Intent intent = new Intent(PrescriptionActivity.this, ModeleList.class);
            startActivity(intent);
        }

    }


    private void popup() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(PrescriptionActivity.this);
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