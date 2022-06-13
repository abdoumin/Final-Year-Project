package com.example.medicare;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicare.api.service.PatientClient;
import com.example.medicare.model.Journal;
import com.example.medicare.model.Medication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JournalList extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Journal> journals;

    String presID;
    String date;

    String BASE_URL = "http://10.0.2.2:8081";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journals_list);



        presID = String.valueOf(getIntent().getStringExtra("idOfPatient"));
        date = getIntent().getStringExtra("DateElt");


        initiliaze(presID);


    }



    private void initiliaze(String presID) {

        journals = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(JournalList.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);



        JournalAdapter journalAdapter = new JournalAdapter(JournalList.this,journals);
        recyclerView.setAdapter(journalAdapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        PatientClient client = retrofit.create(PatientClient.class);
        Call<List<Journal>> call = client.findJournalByPatientAndDate(presID,date); // TO CHANGE
        call.enqueue(new Callback<List<Journal>>() {
            @Override
            public void onResponse(Call<List<Journal>> call, Response<List<Journal>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    journals.addAll(response.body());
                    journalAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Journal>> call, Throwable t) {
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