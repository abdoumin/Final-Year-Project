package com.example.medicare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicare.model.Journal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalViewHolder> {

    private Context mContext;
    private List<Journal> myJournalList;

    public JournalAdapter(Context mContext, List<Journal> myJournalList) {
        this.mContext = mContext;
        this.myJournalList = myJournalList;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_journal,parent,false);
        return new JournalViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        String dateDePrise = myJournalList.get(position).getDate();
        LocalDate now = LocalDate.parse(dateDePrise);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatDateTime = now.format(formatter);

        holder.textViewMedicineName.setText("Medical Name : "+myJournalList.get(position).getMedication().getName());
        holder.textViewDateDePrise.setText("Date : "+formatDateTime);
        holder.textViewHeureDePrise.setText("Heure  :"+myJournalList.get(position).getTime());
        holder.textViewHeureDePrise3.setText("Status : " + myJournalList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return myJournalList.size();
    }
}

class JournalViewHolder extends RecyclerView.ViewHolder{

    TextView textViewMedicineName,textViewDateDePrise,textViewHeureDePrise,textViewHeureDePrise3;


    public JournalViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewDateDePrise=itemView.findViewById(R.id.textViewDateDePrise);
        textViewMedicineName=itemView.findViewById(R.id.textViewMedicineName);
        textViewHeureDePrise = itemView.findViewById(R.id.textViewHeureDePrise);
        textViewHeureDePrise3 = itemView.findViewById(R.id.textViewHeureDePrise3);


    }
}
