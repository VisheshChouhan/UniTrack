package com.hfad.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class homePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //String UserName =  getIntent().getStringExtra("UserName");
    Student student;
    String studentUid;
    String studentName;

    FirebaseAuth currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        /*if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.home_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        NavigationView navigationView = (NavigationView) findViewById(R.id.home_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        currentUser = FirebaseAuth.getInstance();
        studentUid = currentUser.getUid();


        studentName = currentUser.getCurrentUser().getEmail();
        studentName = studentName.substring(0, studentName.indexOf("@"));

        //String UserName =  getIntent().getStringExtra("UserName");
        /*student = (Student) getIntent().getSerializableExtra("StudentClass");
        String UserName = (String) student.getName();*/

        //TextView usernameView = findViewById(R.id.userName);
        //usernameView.setText(studentName);




        //retrieving the courses student is enrolled in
        ArrayList<String> codes = new ArrayList<>();
        /*HashMap<String, String> attendance =  student.getAttendance();
        for(Map.Entry<String, String> e: attendance.entrySet())
        {
            codes.add(e.getKey());

        }*/

        RecyclerView courseRecyclerView = (RecyclerView) findViewById(R.id.courseRecyclerView);
        //ArrayList<Courses> coursesArrayList = new ArrayList<>();
        ArrayList<String> coursesArrayList = new ArrayList<>();
        //ArrayList<String> coursesNameList = new ArrayList<>();
        CourseCardAdapter adapter = new CourseCardAdapter(coursesArrayList,/*coursesNameList,*/ studentName, studentUid);





        //Reading courses data from database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("students").document(studentName).collection("groups")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Student Retrived DAta", document.getId() + " => " + document.getData());
                                String tempCode = document.getData().get("coursecode").toString();
                                coursesArrayList.add(tempCode);
                                //tempCode = document.getData().get("courseName").toString();
                                //coursesNameList.add(tempCode);
                                adapter.notifyDataSetChanged();
                                Log.d("Code", tempCode);


                            }
                        } else {
                            Log.d("Student Retrived Error",""+ task.getException());
                        }
                    }
                });
        /*DatabaseReference courseReference = FirebaseDatabase.getInstance().getReference("Courses");
        courseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(String st: codes)
                {

                    if (snapshot.exists()) {

                        coursesArrayList.add(snapshot.child(st).getValue(Courses.class));
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        GridLayoutManager layoutManager = new GridLayoutManager(homePageActivity.this, 1, LinearLayoutManager.VERTICAL,false);
        courseRecyclerView.setLayoutManager(layoutManager);
        courseRecyclerView.setAdapter(adapter);








        //Temporary writing data in firestore
        /*FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<ChatMessage> tempChat = new ArrayList<>();
        tempChat.add(new ChatMessage("hi","me", new Date()));
        tempChat.add(new ChatMessage("hello","me", new Date()));
        tempChat.add(new ChatMessage("namaste","me", new Date()));
        db.collection(student.getBranch()).document("a").collection("posts").document().set(new ChatMessage("hi",student.getName(), new Date()));
        db.collection(student.getBranch()).document("a").collection("posts").add(new ChatMessage("hello", student.getName(), new Date()));
        */









        // temp data feeding
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        String CourseName = "Artificial Intelligence";
        String facultyName = "Miss Himani Mishra";
        String courseId = "CO34298";
        String type = "Elective";
        Courses courses = new Courses(CourseName, facultyName, courseId, type);
        DatabaseReference ref = database.getReference("Courses");
        ref.child(courseId).setValue(courses);*/

       /* FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Scheme");
        String code = "CO24507";
        TempSub sub = new TempSub(code);
        ref.child("Computer Science and Engineering").child("3").child(code).setValue(sub);*/


    }



    public void openProfile(View view)
    {
        Intent intent = new Intent(homePageActivity.this, ProfilePage.class);
        intent.putExtra("StudentClass",student);
        startActivity(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.enroll:
                EnrollCourseCustomDialogClass cdd = new EnrollCourseCustomDialogClass(homePageActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();

        }

        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.home_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}