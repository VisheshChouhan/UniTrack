package com.hfad.login;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;

public class ProgressCardAdapter extends RecyclerView.Adapter<ProgressCardAdapter.ViewHolder> {
    ArrayList<String> ExamName;
    ArrayList<String> marks;





    public ProgressCardAdapter(ArrayList<String> ExamName, ArrayList<String> marks) {
  ;
        this.ExamName = ExamName;
        this.marks = marks;



    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private CardView cardView;

        ViewHolder(CardView v)
        {
            super(v);
            cardView = v;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_cardview, parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CardView cv = holder.cardView;





        TextView subCodeView = (TextView) cv.findViewById(R.id.ExamName);
        subCodeView.setText(ExamName.get(position));

        TextView studentMarks = cv.findViewById(R.id.StudentMarks1);
        studentMarks.setText(marks.get(position)+"%");

        LinearProgressIndicator progressIndicator = cv.findViewById(R.id.marksProgressIndicator);
        progressIndicator.setProgress( (int) (Double.parseDouble(marks.get(position)) ) );
        //progressIndicator.setProgress(10);








    }

    @Override
    public int getItemCount() {
        return ExamName.size();
    }


}
