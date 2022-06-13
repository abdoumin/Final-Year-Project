package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textviewRegister,textviewContactUs;
    Button btnDoctor,btnSectrary,btnPatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDoctor=findViewById(R.id.btnDoctor);
        btnSectrary=findViewById(R.id.btnSectrary);




        btnDoctor.setOnClickListener(this);
        btnSectrary.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDoctor:

                Intent i =new Intent(MainActivity.this,LoginActivity.class);
                i.putExtra("type","Doctor");
                startActivity(i);


                //startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                break;
            case R.id.btnSectrary:

                Intent j =new Intent(MainActivity.this,LoginActivity.class);
                j.putExtra("type","Admin");
                startActivity(j);

                //startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                break;


        }
    }

   /* public void test(View view) {
        startActivity(new Intent(getApplicationContext(),DoctorListActivity.class));
    }*/


}
