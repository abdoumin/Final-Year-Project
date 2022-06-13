package com.example.medicare;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AdapterDateJourn extends  RecyclerView.Adapter<DateViewHolder>{

    private Context mContext;
    private List<LocalDate> myPatientList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    String id;



    public AdapterDateJourn(Context mContext, List<LocalDate> myPatientList) {
        this.mContext = mContext;
        this.myPatientList = myPatientList;
        sharedPreferences = mContext.getSharedPreferences("PatientJournalAdapter", Context.MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        id = sharedPreferences.getString("file","");
    }


    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_list_item,viewGroup,false);
        return new DateViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DateViewHolder dateViewHolder, int i) {

        dateViewHolder.textViewFileNumber.setText(String.valueOf(myPatientList.get(i)));

        dateViewHolder.cardViewpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = "2022-06-07"; // Data for testing
                Intent intent=new Intent(mContext,JournalList.class);
                intent.putExtra("date",date/*String.valueOf(myPatientList.get(dateViewHolder.getAdapterPosition()))*/);
                intent.putExtra("idOfPatient",String.valueOf(id));
                intent.putExtra("DateElt",String.valueOf(myPatientList.get(dateViewHolder.getAdapterPosition())));
               mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myPatientList.size();
    }
}

class DateViewHolder extends RecyclerView.ViewHolder{

    TextView textViewFileNumber,textViewPatientName;
    CardView cardViewpatient;



    public DateViewHolder(View itemView) {
        super(itemView);

        cardViewpatient=itemView.findViewById(R.id.cardViewpatient);
        textViewFileNumber=itemView.findViewById(R.id.textViewFileNumber);
        textViewPatientName=itemView.findViewById(R.id.textViewPatientName);


    }
}

