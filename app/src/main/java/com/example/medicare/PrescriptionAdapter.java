package com.example.medicare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicare.model.Prescription;

import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionViewHolder> {

    private Context mContext;
    private List<Prescription> myPrescriptionList;

    public PrescriptionAdapter(Context mContext, List<Prescription> myPrescriptionList) {
        this.mContext = mContext;
        this.myPrescriptionList = myPrescriptionList;
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_item,parent,false);
        return new PrescriptionViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position) {

        holder.prescriptionDate.setText("Prescription Date : "+myPrescriptionList.get(position).getPrescriptionDate());
        holder.prescription_refilldata.setText("Presciption ID : "+myPrescriptionList.get(position).getId());

        holder.cardViewpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext,MedicationList.class);
                intent.putExtra("prescriptionID",myPrescriptionList.get(holder.getAdapterPosition()).getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myPrescriptionList.size();
    }
}
class PrescriptionViewHolder extends RecyclerView.ViewHolder{

    TextView prescription_refilldata,prescriptionDate;
    CardView cardViewpatient;

    public PrescriptionViewHolder(@NonNull View itemView) {
        super(itemView);


        cardViewpatient=itemView.findViewById(R.id.cardViewpatient);
        prescription_refilldata=itemView.findViewById(R.id.prescription_refilldata);
        prescriptionDate=itemView.findViewById(R.id.prescriptionDate);


    }
}
