package com.example.medicare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicare.model.Doctor;
import com.example.medicare.model.NewPatient;
import com.example.medicare.model.Register;

import java.util.List;

public class DoctorAdapter extends  RecyclerView.Adapter<DoctorViewHolder>{

    private Context mContext;
    private List<Doctor> myDoctorList;

    public DoctorAdapter(Context mContext, List<Doctor> myDoctorList) {
        this.mContext = mContext;
        this.myDoctorList = myDoctorList;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item,parent,false);
        return new DoctorViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {

        holder.textViewFName.setText("Name : Dr. "+myDoctorList.get(position).getFirstName() + " " + myDoctorList.get(position).getLastName());
        holder.textViewGender.setText("Gender : "+myDoctorList.get(position).getGender());
        holder.textViewAge.setText("Age : " + myDoctorList.get(position).getAge());


    }

    @Override
    public int getItemCount() {
        return myDoctorList.size();
    }
}

class DoctorViewHolder extends RecyclerView.ViewHolder{

    TextView textViewFName,textViewGender,textViewAge;
    CardView cardViewDoctor;



    public DoctorViewHolder(View itemView) {
        super(itemView);

        textViewFName=itemView.findViewById(R.id.textViewFName);
        textViewGender=itemView.findViewById(R.id.textViewGender);
        textViewAge=itemView.findViewById(R.id.textViewAge);
        cardViewDoctor=itemView.findViewById(R.id.cardViewDoctor);


    }
}
