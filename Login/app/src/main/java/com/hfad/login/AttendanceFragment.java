package com.hfad.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttendanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttendanceFragment extends Fragment {

    DatabaseReference databaseReference;
    Student student;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AttendanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttendanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AttendanceFragment newInstance(String param1, String param2) {
        AttendanceFragment fragment = new AttendanceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        RecyclerView attendanceRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_attendance, container,false);



        ArrayList<String> total = new ArrayList<>();
        ArrayList<String> subNames = new ArrayList<>();
        ArrayList<String> codes = new ArrayList<>();
        ArrayList<Integer> att = new ArrayList<>();

        student = (Student) getArguments().getSerializable("StudentClass");
        HashMap<String, String> attendance =  student.getAttendance();
        for(Map.Entry<String, String> e: attendance.entrySet())
        {
            codes.add(e.getKey());
            att.add(Integer.parseInt(e.getValue()));
        }


        ArrayList<ArrayList<String>> result = new ArrayList<>();
        try {
            result = new ReadCourses().execute(codes).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        total = result.get(0);
        subNames = result.get(1);





        AttendanceCardAdapter adapter = new AttendanceCardAdapter(subNames, codes,att, total);



        /*DatabaseReference courseReference = FirebaseDatabase.getInstance().getReference("Courses");
        courseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(String st : codes)
                {

                    //Task<DataSnapshot> snapshot = courseReference.get();

                    if (snapshot.exists()) {

                        String ttl = snapshot.child(st).child("totalClassesTaken").getValue(String.class);
                        total.add(ttl);

                        String name = snapshot.child(st).child("course").getValue(String.class);
                        subNames.add(name);

                    }

                }
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */



        attendanceRecycler.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        attendanceRecycler.setLayoutManager(layoutManager);

        return attendanceRecycler;












    }


    private class ReadCourses extends AsyncTask<ArrayList<String>, Void, ArrayList<ArrayList<String>>>
    {
        DatabaseReference courseReference;
        ArrayList<String> courses;
        ArrayList<String> total;
        ArrayList<String> subNames;






        @Override
        protected void onPreExecute() {
            courseReference = FirebaseDatabase.getInstance().getReference("Courses");
            total = new ArrayList<>();
            subNames = new ArrayList<>();



        }

        @SafeVarargs
        @Override
        protected final ArrayList<ArrayList<String>> doInBackground(ArrayList<String>... arrayLists) {
            courses = arrayLists[0];

            for(String st : courses)
            {
                try {
                    DataSnapshot snapshot = Tasks.await(courseReference.get());

                    if (snapshot.exists()) {

                        String ttl = snapshot.child(st).child("totalClassesTaken").getValue(String.class);
                        total.add(ttl);

                        String name = snapshot.child(st).child("course").getValue(String.class);
                        subNames.add(name);




                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    // Handle execution exception
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // Handle interruption exception
                }

            }


            ArrayList<ArrayList<String>> result = new ArrayList<>();
            result.add(total);
            result.add(subNames);


            return result;

        }
    }
}