package com.hfad.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SignupActivity extends AppCompatActivity {

    EditText signupRollNo, signupName, signupEmail, signupPassword;
    ProgressBar progressbar;

    TextView loginRedirectText;
    Button signupButton;

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        signupEmail = findViewById(R.id.Signup_Email);
        signupRollNo = findViewById(R.id.Signup_RollNo);
        progressbar = findViewById(R.id.progressbar);


        signupPassword = findViewById(R.id.Signup_Password);

        signupButton = findViewById(R.id.SignUp);
        loginRedirectText = findViewById(R.id.redirectLogin);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignUpUser();






            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SignUpUser()
    {
        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password;
        email = signupEmail.getText().toString();
        password = signupPassword.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();



                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(SignupActivity.this,
                                    homePageActivity.class);
                            startActivity(intent);


                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}


    //This function update last logged in user
    /*public void updateLastLoggedUser(String user, String pass)
    {
        ContentValues cred = new ContentValues();
        cred.put("USERNAME", user);
        cred.put("PASSWORD", pass);

        SQLiteOpenHelper writeDatabaseHelper = new loginDatabaseHelper(this);
        try {
            SQLiteDatabase db = writeDatabaseHelper.getWritableDatabase();
            db.update("LASTCREDENTIAL",cred,"_id = ?",new String[] {Integer.toString(1)} );
            db.close();
        }catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();

        }

    }*/




