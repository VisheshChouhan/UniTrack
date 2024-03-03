package com.hfad.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CourseActivity extends AppCompatActivity {

    Courses course;

    Intent MarkAttendanceintent;
    Button markAttendanceButton;
    String enterStageOne = "0";
    String courseCode;
    String studentName;
    String studentUid;

    PieChart pieChart;

    int totalClasses=0;
    int currAttend=0;


    private DatabaseReference mDatabase;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Course_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //course = (Courses) getIntent().getSerializableExtra("CourseClass");
        courseCode = (String) getIntent().getStringExtra("courseCode");
        studentName = (String) getIntent().getStringExtra("studentName");
        studentUid = (String) getIntent().getStringExtra("studentUid");

        //Setting Toolbar heading
        //getSupportActionBar().setTitle(courseCode);


        //TextView teacherNameView = (TextView) findViewById(R.id.teacherNameView);
        //TextView teacherPostView = (TextView) findViewById(R.id.teacherPostView);
        TextView courseIdView = (TextView) findViewById(R.id.courseIdView);
        markAttendanceButton = findViewById(R.id.markAttendanceButton);
        //TextView courseNameView = (TextView) findViewById(R.id.courseNameView);

        //teacherNameView.setText(course.getFacultyName());
        //courseIdView.setText(course.getCourseId());
        courseIdView.setText(courseCode);
        //courseNameView.setText(course.getCourse());


        //Chat Activity
        CardView chatPageCard = findViewById(R.id.course_page_chatbox);
        chatPageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this, ChatActivity.class);
                intent.putExtra("courseCode", courseCode);
                intent.putExtra("studentName", studentName);



                startActivity(intent);
            }
        });

        ViewAttendanceMethod();
        checkAttendanceEntry();


        CardView markAttendanceCard = findViewById(R.id.markAttendaceCardView);
        markAttendanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterStageOne.equals("1")){
                    MarkAttendanceintent = new Intent(CourseActivity.this, StageTwoActivity.class);
                    MarkAttendanceintent.putExtra("courseCode", courseCode);
                    MarkAttendanceintent.putExtra("studentUid", studentUid);
                    MarkAttendanceintent.putExtra("currentAttendance", ""+currAttend);
                    MarkAttendanceintent.putExtra("totalClasses", ""+totalClasses);
                    startActivity(MarkAttendanceintent);
                }
                else{
                    Toast toast = Toast.makeText(CourseActivity.this, "Attendance is not started yet", Toast.LENGTH_SHORT);
                    toast.show();

                }



            }
        });



        //Setting marks view

        RecyclerView marksRecyclerView = findViewById(R.id.MarksRecyclerView);
        ArrayList<String> examName = new ArrayList<>();
        //examName.add("test");
        ArrayList<String> marks = new ArrayList<>();
        //marks.add("80");
        ProgressCardAdapter adapter = new ProgressCardAdapter(examName, marks);


        //Reading courses data from database
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        final CollectionReference  docRef = db.collection("students").document(studentName).collection("groups").document(courseCode). collection("marks");
        docRef
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }


                        for (QueryDocumentSnapshot document : value) {
                            Log.d("Student Retrived DAta", document.getId() + " => " + document.getData());
                            String tempExamName = document.getData().get("examName").toString();
                            examName.add(tempExamName);
                            String tempMarks = document.getData().get("marksObtained").toString();
                            marks.add(tempMarks);

                            adapter.notifyDataSetChanged();
                            Log.d("Code", tempExamName);

                        }

                    }
                });




        GridLayoutManager layoutManager = new GridLayoutManager(CourseActivity.this, 1, LinearLayoutManager.VERTICAL,false);
        marksRecyclerView.setLayoutManager(layoutManager);
        marksRecyclerView.setAdapter(adapter);



        //Implementing assignment card
        RecyclerView assignmentRecycler = findViewById(R.id.assignment_recycler);
        ArrayList<String> assignmentData = new ArrayList<>();
        ArrayList<String> assignmentdueDate = new ArrayList<>();
        ArrayList<String> assignmentUrlList = new ArrayList<>();

        AssignmentCardAdapter assignmentCardAdapter = new AssignmentCardAdapter(assignmentData, assignmentdueDate, assignmentUrlList);


        final CollectionReference  docRef2 = db.collection("Courses").document(courseCode).collection("Assignments");
        docRef2
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }


                        for (QueryDocumentSnapshot document : value) {
                            Log.d("Assignment Retrived DAta", document.getId() + " => " + document.getData());

                            String tempAssignmentName = document.getData().get("pdfName").toString();
                            assignmentData.add(tempAssignmentName);
                            String tempduedate = document.getData().get("dueDate").toString();
                            assignmentdueDate.add(tempduedate);
                            String tempUrl = document.getData().get("downloadUrl").toString();
                            assignmentUrlList.add(tempUrl);

                            assignmentCardAdapter.notifyDataSetChanged();
                            Log.d("Code", tempAssignmentName);

                        }

                    }
                });


        GridLayoutManager assignmentLayoutManager = new GridLayoutManager(CourseActivity.this, 1, LinearLayoutManager.VERTICAL,false);
        assignmentRecycler.setLayoutManager(assignmentLayoutManager);
        assignmentRecycler.setAdapter(assignmentCardAdapter);












    }

    public void checkAttendanceEntry() {

        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Courses").child(courseCode).child("enterStageTwo");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                enterStageOne = dataSnapshot.getValue(String.class);
                if(enterStageOne.equals("1")){
                    //Mark Attendance Activity





                }
                else{
                    markAttendanceButton = findViewById(R.id.markAttendanceButton);
                    markAttendanceButton.setBackgroundColor(ContextCompat.getColor(CourseActivity.this, R.color.grey_300));


                }
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(postListener);

    }


    public void ViewAttendanceMethod()
    {

        //Pie Chart
        pieChart = (PieChart) findViewById(R.id.attendance_pie_chart);

        ArrayList<PieEntry> entries = new ArrayList<>();



        //Reading total classes taken by this subject
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String[] tempTotalClasses = new String[1];
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                tempTotalClasses[0] = dataSnapshot.getValue(String.class)!=null?dataSnapshot.getValue(String.class):"0";

                totalClasses =Integer.parseInt(tempTotalClasses[0]);

                TextView totalClassesView = findViewById(R.id.totalClassesViewCoursePage);
                totalClassesView.setText("Total Classes: "+totalClasses);


                //
                //
                //Reading total classes attended by student
                db = FirebaseFirestore.getInstance();

                DocumentReference docRef = db.collection("students").document(studentUid).collection("groups").document(courseCode);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                long temp = (long) document.get("totalClassAttended");
                                currAttend = Math.toIntExact(temp) ;

                                TextView currAttendanceView = findViewById(R.id.currAttendanceView);
                                currAttendanceView.setText("Your Attendance: "+currAttend);







                                entries.add(new PieEntry(currAttend, "Attended"));
                                Log.e("attendedn", ""+currAttend);
                                entries.add(new PieEntry(totalClasses - currAttend, "Not Attended"));
                                Log.e("total Classes",""+totalClasses);

                                PieDataSet pieDataSet = new PieDataSet(entries, "");

                                ArrayList<Integer> colors = new ArrayList<>();
                                colors.add(Color.rgb(51, 214, 36));
                                colors.add(Color.rgb(194, 2, 12));

                                pieDataSet.setColors(colors);

                                PieData pieData = new PieData(pieDataSet);
                                pieDataSet.setSliceSpace(5f);
                                pieDataSet.setValueTextSize(10f);
                                pieDataSet.setValueTextColor(Color.rgb(255, 255, 255));


                                pieChart.setData(pieData);
                                pieChart.setUsePercentValues(true);
                                pieChart.getDescription().setEnabled(false);
                                pieChart.setDrawHoleEnabled(true);
                                pieChart.setTransparentCircleRadius(51f);
                                pieChart.setHoleRadius(41f);
                                pieChart.animateY(1000, Easing.EaseInOutCubic);
                                Log.e("curre After", ""+currAttend);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });



                Log.e("total after", ""+totalClasses);
                // ..
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("Courses").child(courseCode).child("totalClassesTaken").addValueEventListener(postListener);









    }
}