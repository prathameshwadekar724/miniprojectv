package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Details2 extends AppCompatActivity {

    TextView Name,Contact,Address,Email,Gender,City,State,Postal,Dob,Occ,Field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);
        Name=findViewById(R.id.rName);
        Contact=findViewById(R.id.rContact);
        Address=findViewById(R.id.rAddress);
        Gender=findViewById(R.id.rGender);
        Email=findViewById(R.id.rEmail);
        City=findViewById(R.id.rCity);
        State=findViewById(R.id.rState);
        Postal=findViewById(R.id.rCode);
        Dob=findViewById(R.id.rDob);
        Occ=findViewById(R.id.rOccupation);
        Field=findViewById(R.id.rType);


        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String contact=intent.getStringExtra("Contact");
        String gender=intent.getStringExtra("Gender");
        String email = intent.getStringExtra("Email");
        String password=intent.getStringExtra("Password");
        String address=intent.getStringExtra("Address");
        String city=intent.getStringExtra("City");
        String state=intent.getStringExtra("State");
        String postal=intent.getStringExtra("Postal");
        String dob=intent.getStringExtra("Dob");
        String occupation=intent.getStringExtra("Occupation");
        String type=intent.getStringExtra("Field");

        Name.setText(name);
        Contact.setText(contact);
        Gender.setText(gender);
        Email.setText(email);
        Address.setText(address);
        City.setText(city);
        State.setText(state);
        Postal.setText(postal);
        Dob.setText(dob);
        Occ.setText(occupation);
        Field.setText(type);

    }
}