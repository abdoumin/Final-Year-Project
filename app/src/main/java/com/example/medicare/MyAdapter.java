package com.example.medicare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicare.api.model.Patient;
import com.example.medicare.model.MedicalExpense;
import com.example.medicare.model.NewPatient;

import java.util.List;

public class MyAdapter extends  RecyclerView.Adapter<PatientViewHolder>{

    private Context mContext;
    private List<Patient> myPatientList;


    public MyAdapter(Context mContext, List<Patient> myPatientList) {
        this.mContext = mContext;
        this.myPatientList = myPatientList;
    }


    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_list_item,viewGroup,false);
        return new PatientViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PatientViewHolder patientViewHolder, int i) {

        patientViewHolder.textViewFileNumber.setText("File Number : "+myPatientList.get(i).getId());
        patientViewHolder.textViewPatientName.setText("Patient Name : "+myPatientList.get(i).getFirstName()+" "+myPatientList.get(i).getLastName());


        patientViewHolder.cardViewpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext,PrescriptionActivity.class);
                intent.putExtra("file",Integer.toString(myPatientList.get(patientViewHolder.getAdapterPosition()).getId()));
                intent.putExtra("fname",myPatientList.get(patientViewHolder.getAdapterPosition()).getFirstName());
                intent.putExtra("lname",myPatientList.get(patientViewHolder.getAdapterPosition()).getLastName());
                intent.putExtra("username",myPatientList.get(patientViewHolder.getAdapterPosition()).getUsername());
                intent.putExtra("age",myPatientList.get(patientViewHolder.getAdapterPosition()).getAge());
                intent.putExtra("gender",myPatientList.get(patientViewHolder.getAdapterPosition()).getGender());

               mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myPatientList.size();
    }
}
class PatientViewHolder extends RecyclerView.ViewHolder{

    TextView textViewFileNumber,textViewPatientName;
    CardView cardViewpatient;



    public PatientViewHolder(View itemView) {
        super(itemView);

        cardViewpatient=itemView.findViewById(R.id.cardViewpatient);
        textViewFileNumber=itemView.findViewById(R.id.textViewFileNumber);
        textViewPatientName=itemView.findViewById(R.id.textViewPatientName);


    }
}

