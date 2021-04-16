package com.nitukbt19.StudentBuddy.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitukbt19.StudentBuddy.Adapter.UserAdapter;
import com.nitukbt19.StudentBuddy.Models.Users;
import com.nitukbt19.StudentBuddy.R;
import com.nitukbt19.StudentBuddy.databinding.FragmentStudentBinding;

import java.util.ArrayList;

public class StudentFragment extends Fragment {
    public StudentFragment() {
        // Required empty public constructor
    }

    FragmentStudentBinding binding;
    ArrayList<Users> list =new ArrayList<>();
    FirebaseDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding=FragmentStudentBinding.inflate(inflater, container, false);

       UserAdapter adapter=new UserAdapter(list,getContext());
       binding.studentRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.studentRecyclerView.setLayoutManager(layoutManager);
        //Firebase Instance
        db=FirebaseDatabase.getInstance();
        db.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            //For each Loop
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users user=dataSnapshot.getValue(Users.class);
                    user.getUserId();// Reverify
                    list.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       return binding.getRoot();
    }
}