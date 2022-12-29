package com.joseph.wagba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {

    Button skipButton;
    Button registerButton;
    Intent skipIntent;
    FirebaseAuth firebaseAuth;
    EditText firstName, lastName, email, phoneNumber, password,confirmPassword;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://wagba-b752c-default-rtdb.firebaseio.com/");


    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(this, MainPage.class));
        }
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        skipButton = findViewById(R.id.skip);
        registerButton = findViewById(R.id.registerButton);

        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);



        skipIntent = new Intent(this, LoginPage.class);
        firebaseAuth = FirebaseAuth.getInstance();


        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();
            }
        });

    }
    private void validateUser(){

        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String password1 = password.getText().toString();
        String password2 = confirmPassword.getText().toString();
        String email1 = email.getText().toString();
        String phone = phoneNumber.getText().toString();

        if (fName.isEmpty() || lName.isEmpty() || password1.isEmpty() || password2.isEmpty()
        || email1.isEmpty() || phone.isEmpty() ){


            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            if (!password1.equals(password2)){
                Toast.makeText(this,"Password Fields don't match",Toast.LENGTH_SHORT).show();
                return;
            }
            registerUser(email1,password1,fName,lName,phone);
        }


    }


    private void registerUser(String email,String pass,String fname,String lname,String phone ){

        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterPage.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(RegisterPage.this,LoginPage.class));
//                    finish();
                    updateUser(email,fname,lname,phone);
                }
                else{
                    Toast.makeText(RegisterPage.this, "Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void updateUser(String name, String fname, String lname, String phone ){
        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();

        firebaseAuth.getCurrentUser().updateProfile(userProfileChangeRequest);
        String uid= firebaseAuth.getCurrentUser().getUid();

        database.getReference("users").child(uid).setValue(new User(fname,lname,phone,uid,name));
        firebaseAuth.signOut();
        openLogin();
    }

    private void openLogin(){
        startActivity(new Intent(this, LoginPage.class));
    }
}
