package com.example.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.medicare.model.Modele;

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

public class ModeleList extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Modele> models;
    String patUsername;

    String BASE_URL = "http://10.0.2.2:8081";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models_list);


        initiliaze();


    }



    private void initiliaze() {

        models = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ModeleList.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);



        ModeleAdapter modeleAdapter = new ModeleAdapter(ModeleList.this,models);
        recyclerView.setAdapter(modeleAdapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URLClass.DeviceAddress)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        PatientClient client = retrofit.create(PatientClient.class);
        Call<List<Modele>> call = client.findAllModels(); // TO CHANGE
        call.enqueue(new Callback<List<Modele>>() {
            @Override
            public void onResponse(Call<List<Modele>> call, Response<List<Modele>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    models.addAll(response.body());
                    modeleAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Modele>> call, Throwable t) {
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