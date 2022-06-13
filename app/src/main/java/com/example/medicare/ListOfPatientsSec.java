package com.example.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.medicare.api.model.Patient;
import com.example.medicare.api.service.PatientClient;
import com.example.medicare.model.NewPatient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListOfPatientsSec extends AppCompatActivity {

    RecyclerView recycleView;
    List<Patient> patientList;
    NewPatient newPatient;
    MyAdapter myAdapter;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    Button add_prescription;

    String BASE_URL = "http://10.0.2.2:8081";
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_patients_sec);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Items..");

        getSupportActionBar().setTitle("Patients List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        intiliaze();
    }

    private void intiliaze() {

        patientList = new ArrayList<>();
        recycleView=findViewById(R.id.recycleView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ListOfPatientsSec.this,1);
        recycleView.setLayoutManager(gridLayoutManager);



        myAdapter = new MyAdapter(ListOfPatientsSec.this,patientList);
        recycleView.setAdapter(myAdapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URLClass.DeviceAddress)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        PatientClient client = retrofit.create(PatientClient.class);
        Call<List<Patient>> call = client.patientsForUser();
        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    patientList.addAll(response.body());
                    myAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                t.printStackTrace();

            }
        });









        databaseReference = FirebaseDatabase.getInstance().getReference("MediCare Data").child("Patients Info");


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
