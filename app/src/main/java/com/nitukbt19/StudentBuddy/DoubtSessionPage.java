package com.nitukbt19.StudentBuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nitukbt19.StudentBuddy.databinding.ActivityDoubtSessionPageBinding;

public class DoubtSessionPage extends AppCompatActivity {
ActivityDoubtSessionPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoubtSessionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}