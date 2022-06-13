package com.example.medicare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicare.model.Medication;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineViewHolder> {

    private Context mContext;
    private List<Medication> myMedicineList;

    public MedicineAdapter(Context mContext, List<Medication> myMedicineList) {
        this.mContext = mContext;
        this.myMedicineList = myMedicineList;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MedicineViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {

        holder.textViewMedicineName.setText("Medical Name : "+myMedicineList.get(position).getName());
        holder.textViewDosage.setText("Medical Dosage : "+myMedicineList.get(position).getDosage()+" tablet(s) "
                +myMedicineList.get(position).getFrequency()+" times a day");
        holder.textViewStartDate.setText("Start Medicine on : "+myMedicineList.get(position).getDate());
        holder.textViewEndDate.setText("Take Medicine till : "+myMedicineList.get(position).getEndDate());
        holder.textViewMeal.setText("Take medicine : "+myMedicineList.get(position).getMeal());
    }

    @Override
    public int getItemCount() {
        return myMedicineList.size();
    }
}
class MedicineViewHolder extends RecyclerView.ViewHolder{

    TextView textViewMedicineName,textViewDosage,textViewStartDate,textViewEndDate,textViewMeal;


    public MedicineViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewDosage=itemView.findViewById(R.id.textViewDateDePrise);
        textViewMedicineName=itemView.findViewById(R.id.textViewMedicineName);
        textViewStartDate=itemView.findViewById(R.id.prescription_refilldata);
        textViewEndDate=itemView.findViewById(R.id.prescriptionDate);
        textViewMeal=itemView.findViewById(R.id.textViewMeal);
    }
}
