package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start extends AppCompatActivity {

    Button Admin,Volunteers,Organisation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Admin=findViewById(R.id.admin);
        Volunteers=findViewById(R.id.volunteers);
        Organisation=findViewById(R.id.organisation);



        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Start.this, Admin.class);
                startActivity(intent);
                finish();
            }
        });
        Volunteers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Start.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Organisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Start.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}