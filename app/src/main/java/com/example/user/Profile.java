package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity  {

    TextView textName,textContact,textUsername,textPassword;

    TextView titleV;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textName = findViewById(R.id.nam_user);
        textContact = findViewById(R.id.cont_user);
        textUsername = findViewById(R.id.username_user);
        textPassword = findViewById(R.id.pass_user);
        titleV = findViewById(R.id.welcome);

        showUserData();
        }
    public void showUserData(){

        Intent intent =getIntent();

        String Name = intent.getStringExtra("editName");
        String Contact = intent.getStringExtra("editContact");
        String Username = intent.getStringExtra("editTextUsername");
        String Password = intent.getStringExtra("editTextPassword");

        titleV.setText(Username);
        textName.setText(Name);
        textContact.setText(Contact);
        textUsername.setText(Username);
        textPassword.setText(Password);




    }
}