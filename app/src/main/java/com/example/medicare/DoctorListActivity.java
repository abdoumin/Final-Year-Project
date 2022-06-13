package com.example.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.medicare.api.model.Patient;
import com.example.medicare.api.service.PatientClient;
import com.example.medicare.model.Doctor;
import com.example.medicare.model.Register;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

public class DoctorListActivity extends AppCompatActivity {


    RecyclerView recycleView;
    List<Doctor> list;
    Register register;

    String BASE_URL = "http://10.0.2.2:8081";

    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        recycleView=findViewById(R.id.recycleView);

        getSupportActionBar().setTitle("List Of Doctors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        GridLayoutManager gridLayoutManager = new GridLayoutManager(DoctorListActivity.this,1);
        recycleView.setLayoutManager(gridLayoutManager);

        list = new ArrayList<>();

        final DoctorAdapter doctorAdapter = new DoctorAdapter(DoctorListActivity.this,list);
        recycleView.setAdapter(doctorAdapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        PatientClient client = retrofit.create(PatientClient.class);

        Call<List<Doctor>> call = client.findAllDoctors();
        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    list.addAll(response.body());
                    doctorAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
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
}
