package com.example.medicare;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.medicare.model.HeureDePrise;
import com.example.medicare.model.Medication;
import com.example.medicare.model.Modele;

import java.io.IOException;
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

public class PrescriptionList extends AppCompatActivity implements View.OnClickListener {
    String BASE_URL = "http://10.0.2.2:8081";
    RecyclerView recyclerView;
    List<Medication> medicines;
    MedicineAdapter medicineAdapter;
    Button add_medicine,add_modele;

    TextView textViewFileNumber, textViewFullName, textViewDate;

    EditText editTextMedicineName, editTextFrequency, editTextDosage, editTextDays, timetotake1, timetotake2, timetotake3,modeleTag,modeleDescription;
    Button  btnAddMedicine,btnAddModele;
    View mView;
    RadioGroup radioGroupMeal;
    RadioButton radioButtonBM, radioButtonAM;
    LinearLayout LinearLayoutRecycler, LinearLayoutData,LinearLayoutData2;

    String SearchText;
    String FN, LN, Datee, medicineName, frequency, dosage, meal, endDate, days;
    String heuredeprise1, heuredeprise2, heuredeprise3;
    Bundle extras;
    String presID;



    String medId;

    //Get the file number from the bundle


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list);

        add_medicine = findViewById(R.id.add_medicine);
        add_modele = findViewById(R.id.add_modele);
        btnAddModele = findViewById(R.id.btnAddModele);
        add_modele.setOnClickListener(this);
        add_medicine.setOnClickListener(this);
        btnAddModele.setOnClickListener(this);

        presID = getIntent().getStringExtra("presID");



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        initiliaze();


    }


    private void initiliaze() {

        medicines = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(PrescriptionList.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);


        medicineAdapter = new MedicineAdapter(PrescriptionList.this, medicines);
        recyclerView.setAdapter(medicineAdapter);

        textViewFileNumber = findViewById(R.id.textViewFileNumber);
        textViewFullName = findViewById(R.id.textViewFullName);
        textViewDate = findViewById(R.id.textViewDate);
        LinearLayoutRecycler = findViewById(R.id.LinearLayoutRecycler);
        LinearLayoutData2 = findViewById(R.id.LinearLayoutData2);
        mView = getLayoutInflater().inflate(R.layout.medicine_dialoge, null);


        editTextMedicineName = findViewById(R.id.editTextMedicineName);
        editTextFrequency = findViewById(R.id.editTextFrequency);
        editTextDosage = findViewById(R.id.editTextDosage);
        editTextDays = findViewById(R.id.editTextDays);
        modeleTag = findViewById(R.id.modeleTag);
        modeleDescription=findViewById(R.id.modeleDescription);

        timetotake1 = findViewById(R.id.timetotake1);
        timetotake2 = findViewById(R.id.timetotake2);
        timetotake3 = findViewById(R.id.timetotake3);

        btnAddMedicine = findViewById(R.id.btnAddMedicine);


        btnAddMedicine.setOnClickListener(this);

        radioGroupMeal = findViewById(R.id.radioGroupMeal);
        radioButtonBM = findViewById(R.id.radioButtonBM);
        radioButtonAM = findViewById(R.id.radioButtonAM);


        LinearLayoutData = findViewById(R.id.LinearLayoutData);


        Datee = String.valueOf(LocalDate.now());

    }

    @Override
    public void onClick(View v) {
        if (v == btnAddMedicine) {

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
            } catch (NumberFormatException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }


            medicineName = editTextMedicineName.getText().toString();
            frequency = editTextFrequency.getText().toString();
            dosage = editTextDosage.getText().toString();
            days = editTextDays.getText().toString();
            heuredeprise1 = timetotake1.getText().toString();
            heuredeprise2 = timetotake2.getText().toString();
            heuredeprise3= timetotake3.getText().toString();


            if (medicineName.isEmpty()) {
                editTextMedicineName.setError("Please Enter the Medicine Name");
                editTextMedicineName.requestFocus();
            } else if (frequency.isEmpty() || frequency.length() > 10) {
                editTextFrequency.setError("Please Enter the frequency of tablets");
                editTextFrequency.requestFocus();

            } else if (dosage.isEmpty() || dosage.length() > 5) {
                editTextDosage.setError("Please Enter the dosage per day");
                editTextDosage.requestFocus();

            } else if (radioGroupMeal.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Choose a option for taking medicine", Toast.LENGTH_LONG).show();

            } else if (days.isEmpty() || days.length() > 30) {
                editTextDosage.setError("Please Enter the days to continue");
                editTextDosage.requestFocus();
            }
            else {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MMMM dd,yyyy");
                String reg_date = df.format(c.getTime());
                int day = Integer.valueOf(days);
                c.add(Calendar.DATE, day);
                endDate = df.format(c.getTime());

                Log.d("FullMedicine", medicineName + " " + frequency + " " + dosage + " " + meal + " " + days);

                Log.d("btnConfirm", "Clicked");
                Medication medicine = new Medication(Datee, medicineName, frequency, dosage, meal, days, endDate);

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(URLClass.DeviceAddress)
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = builder.build();
                PatientClient client = retrofit.create(PatientClient.class);
                Call<Medication> call = client.createMedication("abdoumin", getIntent().getStringExtra("username"),
                        presID, medicine);

                Response<Medication> response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(PrescriptionList.this, "Medicine ID: " + response.body().getId(), Toast.LENGTH_SHORT).show();
                medicines.add(medicine);
                LinearLayoutRecycler.setVisibility(View.VISIBLE);
                LinearLayoutData.setVisibility(View.GONE);
                editTextMedicineName.setText("");
                editTextFrequency.setText("");
                editTextDosage.setText("");
                editTextDays.setText("");

                medId = String.valueOf(response.body().getId());

                if(!(heuredeprise1.isEmpty()))
                {
                    createTempsDePrise(medId,heuredeprise1);
                }
                if (!(heuredeprise2.isEmpty()))
                {
                    createTempsDePrise(medId,heuredeprise2);
                }
                if(!(heuredeprise3.isEmpty()))
                {
                    createTempsDePrise(medId,heuredeprise3);
                }

                timetotake1.setText("");
                timetotake2.setText("");
                timetotake3.setText("");

                medicineAdapter.notifyDataSetChanged();



            }
        }

        if (v == add_medicine) {
            LinearLayoutRecycler.setVisibility(View.GONE);
            LinearLayoutData.setVisibility(View.VISIBLE);
        }
        if(v==add_modele) {

            LinearLayoutRecycler.setVisibility(View.GONE);
            LinearLayoutData.setVisibility(View.GONE);
            LinearLayoutData2.setVisibility(View.VISIBLE);

        }
        if(v==btnAddModele)
        {
            String TAG, description;
            TAG = modeleTag.getText().toString();
            description = modeleDescription.getText().toString();
            if (!TAG.isEmpty() && !description.isEmpty()) {
                Modele modele = new Modele(TAG, description);
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(URLClass.DeviceAddress)
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = builder.build();
                PatientClient client = retrofit.create(PatientClient.class);
                Call<Modele> call = client.createModele(presID, modele);
                call.enqueue(new Callback<Modele>() {
                    @Override
                    public void onResponse(Call<Modele> call, Response<Modele> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(PrescriptionList.this, "Modele ID: " + response.body().getId(), Toast.LENGTH_SHORT).show();
                            LinearLayoutRecycler.setVisibility(View.VISIBLE);
                            LinearLayoutData2.setVisibility(View.GONE);


                        }
                    }

                    @Override
                    public void onFailure(Call<Modele> call, Throwable t) {
                        t.printStackTrace();

                    }
                });


            }
        }


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d("Back", "BackButton has been pressed");
            finish();

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.d("BackButton", "BackButton Has been pressed");
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void createTempsDePrise(String medId,String heuredeprise) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URLClass.DeviceAddress)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        PatientClient client = retrofit.create(PatientClient.class);
        HeureDePrise heureDePrise = new HeureDePrise(heuredeprise);
        Call<HeureDePrise> call = client.createHeureDePrise(medId,heureDePrise);
        call.enqueue(new Callback<HeureDePrise>() {
            @Override
            public void onResponse(Call<HeureDePrise> call, Response<HeureDePrise> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(PrescriptionList.this, "Heure ID: " + response.body().getId(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<HeureDePrise> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }
}