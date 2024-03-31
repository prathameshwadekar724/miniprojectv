package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Details extends AppCompatActivity {

    TextView Name,License,Address,Contact,Type,Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String contact=intent.getStringExtra("Contact");
        String email = intent.getStringExtra("Email");

        String address=intent.getStringExtra("Address");
        String license=intent.getStringExtra("License");
        String type=intent.getStringExtra("Type");

        Name = findViewById(R.id.rName);
        Contact=findViewById(R.id.rContact);
        Email = findViewById(R.id.rEmail);
        Address=findViewById(R.id.rAddress);
        License=findViewById(R.id.rLicense);
        Type=findViewById(R.id.rType);


        Name.setText(name);
        Contact.setText(contact);
        Email.setText(email);

        Address.setText(address);
        License.setText(license);
        Type.setText(type);
    }
}