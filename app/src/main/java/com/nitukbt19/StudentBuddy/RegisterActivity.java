package com.nitukbt19.StudentBuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.nitukbt19.StudentBuddy.Models.StudentList;
import com.nitukbt19.StudentBuddy.Models.TeacherList;
import com.nitukbt19.StudentBuddy.Models.Users;
import com.nitukbt19.StudentBuddy.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Welcome "+binding.inputUsername.getText().toString());
        binding.alreadyHaveAccount.setOnClickListener(view -> {
            Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        });
        //disabling Register button till all inputs are entered
        binding.btnRegister.setEnabled(false);
        binding.switchStudent.setChecked(true);
        binding.inputConfirmPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()>3){
                    binding.btnRegister.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        // Register button Logic
        binding.btnRegister.setOnClickListener(v -> {
            progressDialog.show();
            //matching confirm password
            if(binding.inputPasswordRegister2.getText().toString().equals(binding.inputConfirmPassword1.getText().toString())) {
                mAuth.createUserWithEmailAndPassword(binding.etEmailAddress.getText().toString(), binding.inputPasswordRegister2.getText().toString()).
                        addOnCompleteListener(task -> {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Users user=new Users(binding.inputUsername.getText().toString(),binding.etEmailAddress.getText().toString(),binding.inputPasswordRegister2.getText().toString(),binding.switchStudent.isChecked());
                                String id=task.getResult().getUser().getUid();
                                database.getReference().child("Users").child(id).setValue(user);

                                //addition to Student or Teacher List
                                if(binding.switchStudent.isChecked()) {
                                    StudentList student=new StudentList(id,binding.inputUsername.getText().toString());
                                    database.getReference().child("StudentList").child(id).setValue(student.getUserName());
                                }else{
                                    TeacherList teacher =new TeacherList(id,binding.inputUsername.getText().toString());
                                    database.getReference().child("TeacherList").child(id).setValue(teacher.getUserName());
                                }

                                Toast.makeText(RegisterActivity.this, "Successfully Registered in Student Buddy", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                // to add intent
            }else{
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this,"Passwords do not match!!", Toast.LENGTH_LONG).show();
                binding.inputPasswordRegister2.setText("");
                binding.inputConfirmPassword1.setText("");
                binding.inputPasswordRegister2.setHintTextColor(Color.RED);
                binding.inputConfirmPassword1.setHintTextColor(Color.RED);
            }

        });

    }
}