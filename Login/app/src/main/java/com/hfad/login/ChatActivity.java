package com.hfad.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.OnProgressListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 22;

    //Student student;

    String studentName;
    String courseCode;
    FirebaseFirestore db;
    ArrayList<ChatMessage> tempChat;
    ChatAdapter adapter;
    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //catching passed extras
        courseCode = (String) getIntent().getStringExtra("courseCode");
        //student = (Student) getIntent().getSerializableExtra("StudentClass");
        studentName = (String) getIntent().getStringExtra("studentName");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(courseCode);
        setSupportActionBar(toolbar);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ImageView addImage = findViewById(R.id.button_add_image);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*UploadImageDialogClass cdd = new UploadImageDialogClass(ChatActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();*/

                Intent intent = new Intent(ChatActivity.this, ImageUploadActivity.class );
                intent.putExtra("courseCode", courseCode);
                intent.putExtra("studentName", studentName);

                startActivity(intent);

            }
        });



        db = FirebaseFirestore.getInstance();

        /*FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);*/

        /*Log.d("Message", "Manually entered");
        ChatMessage sentMessage = new ChatMessage("Hello", student.getName(), new Date());
        db.collection(student.getBranch()).document("a").collection("posts").add(sentMessage);
        Log.d("Message", "Manually entered Successfully");*/



        tempChat = new ArrayList<>();
        adapter = new ChatAdapter(studentName, tempChat);

        //Getting data out from firestore
        Log.e("Student Branch", studentName);

        db.collection("Courses").document(courseCode).collection("Chats").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                tempChat.clear();

                for(QueryDocumentSnapshot doc: value)
                {
                    tempChat.add(doc.toObject(ChatMessage.class));
                    Log.d("value", doc.toObject(ChatMessage.class).getMessage());

                }
                tempChat.sort(new Comparator<ChatMessage>() {
                    @Override
                    public int compare(ChatMessage o1, ChatMessage o2) {
                        return o1.getTimeStamp().compareTo(o2.getTimeStamp());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });


        //tempChat.add(new ChatMessage("hi","me", new Date()));
        //tempChat.add(new ChatMessage("hello","me", new Date()));
        //tempChat.add(new ChatMessage("namaste","me", new Date()));




        RecyclerView chatRecycler = (RecyclerView) findViewById(R.id.list_chat);


        chatRecycler.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(ChatActivity.this, 1);
        chatRecycler.setLayoutManager(layoutManager);




    }

    public void sendMessage(View view)
    {
        EditText messageEditText = (EditText) findViewById(R.id.edittext_chat);
        String message = messageEditText.getText().toString().trim();
        if(!message.isEmpty())
        {
            messageEditText.setText(null);

            RecyclerView chats = (RecyclerView) findViewById(R.id.list_chat);



            ChatMessage sentMessage = new ChatMessage(message, studentName, new Date(), "text", "dummy Url");
            tempChat.add(sentMessage);
            //adapter.notifyDataSetChanged();

            db.collection("Courses").document(courseCode).collection("Chats").add(sentMessage);

        }




    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);

            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }







}