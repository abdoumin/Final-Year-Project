package com.example.medicare;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicare.api.model.Patient;

import java.util.List;

public class PatientsJournalAdapter extends  RecyclerView.Adapter<PatientJournalViewHolder>{

    private Context mContext;
    private List<Patient> myPatientList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;


    public PatientsJournalAdapter(Context mContext, List<Patient> myPatientList) {
        this.mContext = mContext;
        this.myPatientList = myPatientList;
        sharedPreferences = mContext.getSharedPreferences("PatientJournalAdapter", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
    }


    @Override
    public PatientJournalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_list_item,viewGroup,false);
        return new PatientJournalViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PatientJournalViewHolder patientViewHolder, int i) {

        patientViewHolder.textViewFileNumber.setText("File Number : "+myPatientList.get(i).getId());
        patientViewHolder.textViewPatientName.setText("Patient Name : "+myPatientList.get(i).getFirstName()+" "+myPatientList.get(i).getLastName());


        patientViewHolder.cardViewpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext,ListOfDates.class);
                intent.putExtra("file",Integer.toString(myPatientList.get(patientViewHolder.getAdapterPosition()).getId()));
                intent.putExtra("fname",myPatientList.get(patientViewHolder.getAdapterPosition()).getFirstName());
                intent.putExtra("lname",myPatientList.get(patientViewHolder.getAdapterPosition()).getLastName());
                intent.putExtra("username",myPatientList.get(patientViewHolder.getAdapterPosition()).getUsername());
                intent.putExtra("age",myPatientList.get(patientViewHolder.getAdapterPosition()).getAge());
                intent.putExtra("gender",myPatientList.get(patientViewHolder.getAdapterPosition()).getGender());
                myEdit.putString("file",Integer.toString(myPatientList.get(patientViewHolder.getAdapterPosition()).getId()));
                myEdit.commit();
               mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myPatientList.size();
    }
}

class PatientJournalViewHolder extends RecyclerView.ViewHolder{

    TextView textViewFileNumber,textViewPatientName;
    CardView cardViewpatient;



    public PatientJournalViewHolder(View itemView) {
        super(itemView);

        cardViewpatient=itemView.findViewById(R.id.cardViewpatient);
        textViewFileNumber=itemView.findViewById(R.id.textViewFileNumber);
        textViewPatientName=itemView.findViewById(R.id.textViewPatientName);


    }
}

