package com.example.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView textviewName;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    String username ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        sharedPreferences = getApplicationContext().getSharedPreferences("PatientUsername",Context.MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        username = sharedPreferences.getString("username","");

        textviewName=findViewById(R.id.textviewName);
        Bundle mBundle=getIntent().getExtras();
        if(mBundle!=null) {
            String fullName = mBundle.getString("FullName");
            textviewName.setText("Doctor "+fullName);
            textviewName.setTextColor(getResources().getColor(R.color.btncolor));
        }




        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);


    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
        super.onBackPressed();
    }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.PatientDetails){
            startActivity(new Intent(getApplicationContext(),ListOfPatientsSecJournal.class));
        }
        else if(id==R.id.listOfPatients){
            startActivity(new Intent(getApplicationContext(),ListOfPatientsSec.class));
        }
        else if(id==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            finish();
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
