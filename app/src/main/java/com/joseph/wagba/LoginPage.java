package com.joseph.wagba;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity {
    EditText username;
    EditText password;
    TextView register;
    Button loginButton;
    Intent myintent;
    Intent regIntent;
    String regStringHTML =  "<u>Not yet registered? SignUp Now</u>" ;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(this, SplashScreen.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        username = findViewById(R.id.userEmail);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        register = findViewById(R.id.register);
        myintent= new  Intent(this,MainPage.class);
        regIntent = new Intent(this,RegisterPage.class);

        register.setText(Html.fromHtml(regStringHTML));
        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://wagba-b752c-default-rtdb.firebaseio.com/");





        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(regIntent);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateUser();
            }
        });

    }
    private void validateUser(){

       String user = username.getText().toString();
       String pass = password.getText().toString();

       if (user.isEmpty() || pass.isEmpty()){
           Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
       }
       else
       {
           loginUser(user,pass);
       }
    }


    private void loginUser(String user, String pass){

        firebaseAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginPage.this, SplashScreen.class));
                    finish();
                }
                else {
                    Toast.makeText(LoginPage.this, "Error : "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}