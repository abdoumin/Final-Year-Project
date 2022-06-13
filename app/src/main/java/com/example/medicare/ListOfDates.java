package com.example.medicare;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicare.api.model.Patient;
import com.example.medicare.api.service.PatientClient;
import com.example.medicare.model.NewPatient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListOfDates extends AppCompatActivity {

    RecyclerView recycleView;
    List<Patient> patientList;
    NewPatient newPatient;
    MyAdapter myAdapter;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    Button add_prescription;
    List<LocalDate> dates;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dates_journ);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Items..");

        getSupportActionBar().setTitle("Patients List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dates = new ArrayList<>();

        for(int i =0; i<10;i++)
        {
            dates.add(LocalDate.now().minusDays(i));
        }


        intiliaze();
    }

    private void intiliaze() {

        recycleView=findViewById(R.id.recycleView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ListOfDates.this,1);
        recycleView.setLayoutManager(gridLayoutManager);



        AdapterDateJourn myAdapter = new AdapterDateJourn(ListOfDates.this,dates);
        recycleView.setAdapter(myAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("MediCare Data").child("Patients Info");


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
