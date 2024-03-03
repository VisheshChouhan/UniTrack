package com.hfad.login;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class pdfViewActivity extends AppCompatActivity {

    // creating a variable for our Firebase Database.
    FirebaseDatabase firebaseDatabase;
    String pdfUrl;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for our pdfview
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        //catching passed intent data
        pdfUrl = (String) getIntent().getStringExtra("pdfUrl");

        // initializing variable for pdf view.
        pdfView = findViewById(R.id.pdfView);

        //pdfView.fromUri(Uri.parse(pdfUrl));



        // below line is used to get the instance
        // of our Firebase database.
        //firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        //databaseReference = firebaseDatabase.getReference("url");

        // calling method to initialize
        // our PDF view.
        initializePDFView();
    }

    private void initializePDFView() {
        Log.d("message", "message1");

        new RetrievedPdffromFirebase().execute(pdfUrl);

    }


    class RetrievedPdffromFirebase extends AsyncTask<String, Void, InputStream> {
        // we are calling async task and performing
        // this task to load pdf in background.
        @Override
        protected InputStream doInBackground(String... strings) {
            // below line is for declaring
            // our input stream.
            InputStream pdfStream = null;
            try {
                // creating a new URL and passing
                // our string in it.
                URL url = new URL(strings[0]);

                // creating a new http url connection and calling open
                // connection method to open http url connection.
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() == 200) {
                    // if the connection is successful then
                    // we are getting response code as 200.
                    // after the connection is successful
                    // we are passing our pdf file from url
                    // in our pdfstream.
                    pdfStream = new BufferedInputStream(httpURLConnection.getInputStream());
                }

            } catch (IOException e) {
                // this method is
                // called to handle errors.
                return null;
            }
            // returning our stream
            // of PDF file.
            return pdfStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after loading stream we are setting
            // the pdf in your pdf view.
            pdfView.fromStream(inputStream).load();
        }
    }
}
