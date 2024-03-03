package com.hfad.login;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CourseCardAdapter extends RecyclerView.Adapter<CourseCardAdapter.ViewHolder>
{

    ArrayList<String> coursesNameList;
    ArrayList<String> coursesArrayList2;

    String studentName;
    String studentUid;

    public CourseCardAdapter(ArrayList<String> coursesArrayList/*,ArrayList<String> coursesNameList*/, String studName, String studUid)  {

        this.coursesArrayList2 = coursesArrayList;
        /*this.coursesNameList = coursesNameList;*/
        this.studentName = studName;
        this.studentUid = studUid;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;

        public ViewHolder(CardView cv) {
            super(cv);
            cardView = cv;
        }
    }
    @NonNull
    @Override
    public CourseCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.course_cardview, parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseCardAdapter.ViewHolder holder, int position)
    {
        CardView cardView = holder.cardView;

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cardView.getContext(), CourseActivity.class);
                intent.putExtra("courseCode", coursesArrayList2.get(position));
                intent.putExtra("studentName", studentName);
                intent.putExtra("studentUid", studentUid);
                cardView.getContext().startActivity(intent);
            }
        });


        TextView courseId = (TextView) cardView.findViewById(R.id.CourseId);
        TextView courseName = (TextView) cardView.findViewById(R.id.CourseName);
        /*TextView teacherName = cardView.findViewById(R.id.TeacherName);
        TextView courseType = cardView.findViewById(R.id.CourseType);*/

        String courseCode = coursesArrayList2.get(position);
        courseId.setText(courseCode);
        //courseName.setText(coursesNameList.get(position));
        /*
        courseType.setText(coursesArrayList.get(position).getType());
        teacherName.setText(coursesArrayList.get(position).getFacultyName());*/




        //Reading course Name
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Courses").document(courseCode);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String tempcourseName = document.getData().get("courseName").toString();
                        courseName.setText(tempcourseName);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return coursesArrayList2.size();
    }



}
