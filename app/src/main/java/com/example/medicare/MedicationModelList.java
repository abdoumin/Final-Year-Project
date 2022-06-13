package com.example.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MedicationModelList extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    List<Medication> medicines;
    Button create_pres;
    int presID = 0;

    String BASE_URL = "http://10.0.2.2:8081";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelsmedications_list);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        initiliaze();


    }



    private void initiliaze() {

        medicines = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleView);
        create_pres = findViewById(R.id.create_pres_model);
        create_pres.setOnClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MedicationModelList.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);



        ModelMedicineAdapter medicineAdapter = new ModelMedicineAdapter(MedicationModelList.this,medicines);
        recyclerView.setAdapter(medicineAdapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URLClass.DeviceAddress)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        PatientClient client = retrofit.create(PatientClient.class);
        Call<List<Medication>> call = client.medicationByModele(getIntent().getIntExtra("modeleId",1)); // TO CHANGE
        call.enqueue(new Callback<List<Medication>>() {
            @Override
            public void onResponse(Call<List<Medication>> call, Response<List<Medication>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    medicines.addAll(response.body());
                    medicineAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Medication>> call, Throwable t) {
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

    @Override
    public void onClick(View view) {
        if(view == create_pres)
        {
            Prescription prescription = new Prescription();
            try {
                presID = createPrescription(prescription);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addMedicationsToPrescription(presID);
            finish();

        }
    }

    public Integer createPrescription(Prescription prescription) throws IOException {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URLClass.DeviceAddress)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        PatientClient client = retrofit.create(PatientClient.class);
        String ptUsername = getIntent().getStringExtra("username");
        Call<Prescription> call = client.createPrescription("abdoumin",ptUsername,prescription);

        Response<Prescription> response = call.execute();
        Prescription pres = response.body();
        presID = pres.getId();

        return presID;

    }

    public void addMedicationsToPrescription(int presID)
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URLClass.DeviceAddress)
                .addConverterFactory(GsonConverterFactory.create());


        Retrofit retrofit = builder.build();
        PatientClient client = retrofit.create(PatientClient.class);

        for(int i =0 ; i<medicines.size(); i++ )
        {
            Medication medication = new Medication(medicines.get(i).getDate() ,medicines.get(i).getName(), medicines.get(i).getFrequency(),
                    medicines.get(i).getDosage(),medicines.get(i).getMeal(),medicines.get(i).getDays(),
                    medicines.get(i).getEndDate());
            Call<Medication> call = client.createMedication("abdoumin", getIntent().getStringExtra("username"),
                    String.valueOf(presID), medication);
            call.enqueue(new Callback<Medication>() {
                @Override
                public void onResponse(Call<Medication> call, Response<Medication> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(MedicationModelList.this, "Medicine ID: " + response.body().getId(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Medication> call, Throwable t) {
                    t.printStackTrace();

                }
            });
        }

    }
}