package com.hfad.login;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AssignmentCardAdapter extends RecyclerView.Adapter<AssignmentCardAdapter.ViewHolder> {

    ArrayList<String> assignmentData;
    ArrayList<String> assignmentdueDate;
    ArrayList<String> assignmentUrl;

    public AssignmentCardAdapter(ArrayList<String> assignData, ArrayList<String> dueDateList, ArrayList<String> urlList){
        assignmentData = assignData;
        assignmentdueDate = dueDateList;
        assignmentUrl = urlList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @NonNull
    @Override
    public AssignmentCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_card_view, parent,false);
        return new AssignmentCardAdapter.ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentCardAdapter.ViewHolder holder, int position) {
        CardView cv = holder.cardView;

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cv.getContext(), pdfViewActivity.class);
                intent.putExtra("pdfUrl", assignmentUrl.get(position));
                cv.getContext().startActivity(intent);
            }
        });

        TextView assignmentname = cv.findViewById(R.id.assignment_name);
        assignmentname.setText(assignmentData.get(position));

        TextView assignmentUrlView = cv.findViewById(R.id.assignment_dueDate);
        assignmentUrlView.setText("Due date: "+assignmentdueDate.get(position));

    }

    @Override
    public int getItemCount() {
        return assignmentData.size();
    }
}
