package com.example.medicare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicare.model.Medication;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class ModelMedicineAdapter extends RecyclerView.Adapter<ModelMedicineViewHolder> {

    private Context mContext;
    private List<Medication> myMedicineList;
    ModelMedicineAdapter modelMedicineAdapter = this;

    public ModelMedicineAdapter(Context mContext, List<Medication> myMedicineList) {
        this.mContext = mContext;
        this.myMedicineList = myMedicineList;
    }

    @NonNull
    @Override
    public ModelMedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_medicine_list_item,parent,false);
        return new ModelMedicineViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelMedicineViewHolder holder, int position) {
        int pos = position;
        holder.linearLayoutData_model.setVisibility(View.INVISIBLE);
        holder.textViewMedicineName.setText("Medical Name : "+myMedicineList.get(position).getName());
        holder.textViewDosage.setText("Medical Dosage : "+myMedicineList.get(position).getDosage()+" tablet(s) "
                +myMedicineList.get(position).getFrequency()+" times a day");
        holder.textViewStartDate.setText("Start Medicine on : "+myMedicineList.get(position).getDate());
        holder.textViewEndDate.setText("Take Medicine till : "+myMedicineList.get(position).getEndDate());
        holder.textViewMeal.setText("Take medicine : "+myMedicineList.get(position).getMeal());
        holder.cardViewMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*holder.model_medicine_card.setVisibility(View.INVISIBLE);*/
                holder.linearLayoutData_model.setVisibility(View.VISIBLE);
                holder.editTextMedicineName.setText(myMedicineList.get(pos).getName());
                holder.editTextFrequency.setText(myMedicineList.get(pos).getFrequency());
                holder.editTextDosage.setText(myMedicineList.get(pos).getDosage());
                holder.editTextDays.setText("");

            }
        });

        holder.btn_confirmation.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
            String  Datee, medicineName, frequency, dosage, meal, endDate, days;
                String heuredeprise1, heuredeprise2, heuredeprise3;
                int Meal = holder.radioGroupMeal.getCheckedRadioButtonId();
                meal = null;
                try {
                    switch (Meal) {
                        case R.id.radioButtonBM:
                            meal = "Before Meal";
                            break;
                        case R.id.radioButtonAM:
                            meal = "After Meal";
                            break;
                        default:
                            break;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


                medicineName = holder.editTextMedicineName.getText().toString();
                frequency = holder.editTextFrequency.getText().toString();
                dosage = holder.editTextDosage.getText().toString();
                days = holder.editTextDays.getText().toString();
                heuredeprise1 = holder.timetotake1.getText().toString();
                heuredeprise2 = holder.timetotake2.getText().toString();
                heuredeprise3= holder.timetotake3.getText().toString();


                if (medicineName.isEmpty()) {
                    holder.editTextMedicineName.setError("Please Enter the Medicine Name");
                    holder.editTextMedicineName.requestFocus();
                } else if (frequency.isEmpty() || frequency.length() > 10) {
                    holder.editTextFrequency.setError("Please Enter the frequency of tablets");
                    holder.editTextFrequency.requestFocus();

                } else if (dosage.isEmpty() || dosage.length() > 5) {
                    holder.editTextDosage.setError("Please Enter the dosage per day");
                    holder.editTextDosage.requestFocus();

                } else if (holder.radioGroupMeal.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(mContext, "Choose a option for taking medicine", Toast.LENGTH_LONG).show();

                } else if (days.isEmpty() || days.length() > 30) {
                    holder.editTextDosage.setError("Please Enter the days to continue");
                    holder.editTextDosage.requestFocus();
                }
                else {

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("MMMM dd,yyyy");
                    String reg_date = df.format(c.getTime());
                    int day = Integer.valueOf(days);
                    c.add(Calendar.DATE, day);
                    endDate = df.format(c.getTime());

                    Datee = String.valueOf(LocalDate.now());

                    Log.d("FullMedicine", medicineName + " " + frequency + " " + dosage + " " + meal + " " + days);

                    Log.d("btnConfirm", "Clicked");
                    Medication medicine = new Medication(Datee, medicineName, frequency, dosage, meal, days, endDate);

                    myMedicineList.set(pos,medicine);
                    modelMedicineAdapter.notifyDataSetChanged();
                    holder.linearLayoutData_model.setVisibility(View.INVISIBLE);
                    /*holder.model_medicine_card.setVisibility(View.VISIBLE);*/

            }
        }



    });
    }

    @Override
    public int getItemCount() {
        return myMedicineList.size();
    }

}

class ModelMedicineViewHolder extends RecyclerView.ViewHolder{

    TextView textViewMedicineName,textViewDosage,textViewStartDate,textViewEndDate,textViewMeal;
    CardView cardViewMedicine;
    LinearLayout model_medicine_card, linearLayoutData_model;
    EditText editTextMedicineName,editTextFrequency,editTextDosage,editTextDays,timetotake1, timetotake2, timetotake3;
    Button btn_confirmation;
    RadioGroup radioGroupMeal;
    RadioButton radioButtonBM, radioButtonAM;



    public ModelMedicineViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewDosage=itemView.findViewById(R.id.textViewDateDePrise2);
        textViewMedicineName=itemView.findViewById(R.id.textViewMedicineName2);
        textViewStartDate=itemView.findViewById(R.id.prescription_refilldata2);
        textViewEndDate=itemView.findViewById(R.id.prescriptionDate2);
        textViewMeal=itemView.findViewById(R.id.textViewMeal2);
        cardViewMedicine= itemView.findViewById(R.id.cardViewpatient);
        model_medicine_card = itemView.findViewById(R.id.model_medicine_card);
        linearLayoutData_model = itemView.findViewById(R.id.linear_layout_model_data);
        editTextMedicineName = itemView.findViewById(R.id.editTextMedicineName2);
        editTextFrequency = itemView.findViewById(R.id.editTextFrequency2);
        editTextDosage = itemView.findViewById(R.id.editTextDosage2);
        editTextDays = itemView.findViewById(R.id.editTextDays2);
        btn_confirmation = itemView.findViewById(R.id.btnAddMedicine);
        radioGroupMeal = itemView.findViewById(R.id.radioGroupMeal);
        radioButtonBM = itemView.findViewById(R.id.radioButtonBM);
        radioButtonAM = itemView.findViewById(R.id.radioButtonAM);

        timetotake1 = itemView.findViewById(R.id.timetotake1);
        timetotake2 = itemView.findViewById(R.id.timetotake2);
        timetotake3 = itemView.findViewById(R.id.timetotake3);
    }
}

