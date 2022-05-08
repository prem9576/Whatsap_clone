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
import com.prem.whatsap_clone.databinding.ActivitySignInBinding;
import com.prem.whatsap_clone.databinding.ActivitySignUpBinding;

 public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
     ProgressDialog progressDialog;
     FirebaseAuth mAuth;
     FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInBinding.inflate(getLayoutInflater());



        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=firebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Login ");
        progressDialog.setMessage("Please wait");

        binding.btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.txtEmail.getText().toString().isEmpty()&& !binding.txtPassword.getText().toString().isEmpty())
                {
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(binding.txtEmail.getText().toString(),binding.txtPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()){
                                        Intent intent=new Intent(SignInActivity.this,MainActivity.class);
                                        startActivity(intent);

                                    }
                                    else {
                                        Toast.makeText(SignInActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
             else{
                    Toast.makeText(SignInActivity.this, "Enter credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
  if (mAuth.getCurrentUser()!=null){
      Intent i =new Intent(SignInActivity.this,MainActivity.class);
      startActivity(i);
  }
  binding.txtClickSignUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          Intent i =new Intent(SignInActivity.this,SignUpActivity.class);
          startActivity(i);
      }
  });
    }
}