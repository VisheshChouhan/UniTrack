package com.hfad.login;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EnrollCourseCustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    String uid;
    String studentName;

    public Activity c;
    public Dialog d;
    public Button join, cancel;
    public EditText courseCode;


    public EnrollCourseCustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        join = (Button) findViewById(R.id.join_button);
        cancel = (Button) findViewById(R.id.cancel_button);
        join.setOnClickListener(this);
        cancel.setOnClickListener(this);
        courseCode = findViewById(R.id.course_code);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseAuth currentUser = FirebaseAuth.getInstance();
        studentName = currentUser.getCurrentUser().getEmail();
        studentName = studentName.substring(0, studentName.indexOf("@"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_button:
                String CourseCode = courseCode.getText().toString().trim();
                FirebaseFirestore db = FirebaseFirestore.getInstance();






                DocumentReference docRef = db.collection("Courses").document(CourseCode);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                DocumentReference studentRef = db.collection("students").document(studentName).collection("groups").document(CourseCode);
                                studentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Toast toast = Toast.makeText(getContext(), "You are already registered in Course: "+CourseCode, Toast.LENGTH_LONG);
                                                toast.show();

                                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                dismiss();
                                            } else {
                                                Log.d(TAG, "No such document");

                                                Map<String, Object> Course = new HashMap<>();
                                                Course.put("coursecode",CourseCode);
                                                Course.put("totalClassAttended", 0);
                                                db.collection("students").document(studentName).collection("groups").document(CourseCode).set(Course);


                                                Map<String, Object> Enrolled = new HashMap<>();
                                                Enrolled.put("uid", uid);
                                                Enrolled.put("name", studentName);

                                                db.collection("Courses").document(CourseCode).collection("Enrolled").document(studentName).set(Enrolled);




                                                Toast toast = Toast.makeText(getContext(), "You have successfully joined Course : "+CourseCode, Toast.LENGTH_LONG);
                                                toast.show();
                                                dismiss();
                                            }
                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });



                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                                Toast toast = Toast.makeText(getContext(),"No Course exist with Code: "+CourseCode, Toast.LENGTH_LONG );
                                toast.show();
                                dismiss();
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });


                //c.finish();
                break;
            case R.id.cancel_button:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}