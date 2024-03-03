package com.hfad.login;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.OnProgressListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UploadImageDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 22;
    String uid;
    String studentName;

    public Activity c;
    public Dialog d;
    public Button upload, select;


    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;



    public UploadImageDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog2);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        upload = (Button) findViewById(R.id.join_button2);
        select = (Button) findViewById(R.id.cancel_button2);
        upload.setOnClickListener(this);
        select.setOnClickListener(this);


        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseAuth currentUser = FirebaseAuth.getInstance();
        studentName = currentUser.getCurrentUser().getEmail();
        studentName = studentName.substring(0, studentName.indexOf("@"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_button2:

                uploadImage();
                //c.finish();
                break;
            case R.id.cancel_button2:
                SelectImage();

                break;
            default:
                break;
        }
        dismiss();
    }


    // Select Image method
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(getOwnerActivity(),Intent.createChooser(intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST, new Bundle());
    }






    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {



            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog

                                    Toast
                                            .makeText(getContext(),
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded

                            Toast
                                    .makeText(getContext(),
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
        }
    }
}