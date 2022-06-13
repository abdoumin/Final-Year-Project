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

import com.example.medicare.model.Medication;
import com.example.medicare.model.Modele;

import java.util.List;

public class ModeleAdapter extends RecyclerView.Adapter<ModeleViewHolder> {

    private Context mContext;
    private List<Modele> myModeleList;

    public ModeleAdapter(Context mContext, List<Modele> myModeleList) {
        this.mContext = mContext;
        this.myModeleList = myModeleList;
    }

    @NonNull
    @Override
    public ModeleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list_item,parent,false);
        return new ModeleViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ModeleViewHolder holder, int position) {

        holder.textViewFileNumber.setText("Modele TAG : "+myModeleList.get(position).getTAG());
        holder.textViewPatientName.setText("Modele description : "+myModeleList.get(position).getDescription());

        holder.cardViewpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext,MedicationModelList.class);
                intent.putExtra("modeleId",myModeleList.get(holder.getAdapterPosition()).getId());
                intent.putExtra("username",myModeleList.get(holder.getAdapterPosition()).
                                                                    getPrescription().getPatient().getUsername());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myModeleList.size();
    }
}

class ModeleViewHolder extends RecyclerView.ViewHolder{

    TextView textViewFileNumber,textViewPatientName;
    CardView cardViewpatient;


    public ModeleViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewFileNumber=itemView.findViewById(R.id.textViewFileNumber);
        textViewPatientName=itemView.findViewById(R.id.textViewPatientName);
        cardViewpatient = itemView.findViewById(R.id.cardViewpatient);
    }
}
