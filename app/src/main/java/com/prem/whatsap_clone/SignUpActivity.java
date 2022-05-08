package com.prem.whatsap_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.prem.whatsap_clone.Models.Users;
import com.prem.whatsap_clone.databinding.ActivitySignUpBinding;

import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    protected FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivitySignUpBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());
       mAuth=FirebaseAuth.getInstance();
       database=FirebaseDatabase.getInstance();


        getSupportActionBar().hide();
        progressDialog=new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("creating account ");
        progressDialog.setMessage("we are creating your account");
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.txtUsername.getText().toString().isEmpty()&& !binding.txtEmail.getText().toString().isEmpty()&& !binding.txtPassword.getText().toString().isEmpty())
                {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(binding.txtEmail.getText().toString(),binding.txtPassword.getText().toString()).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                   if (task.isSuccessful())
                                   {
                                       Users user=new Users(binding.txtUsername.getText().toString(),binding.txtEmail.getText().toString(),binding.txtPassword.getText().toString());
                                       String id=task.getResult().getUser().getUid();
                                       database.getReference().child("Users").child(id).setValue(user);
                                       Toast.makeText(SignUpActivity.this, "Signup Sucessfull", Toast.LENGTH_SHORT).show();
                                   }
                                   else
                                   {
                                       Toast.makeText(SignUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                   }
                                }
                            });
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Enter creadtials", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.txtAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(i);
            }
        });
    }
}