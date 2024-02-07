package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Admin extends AppCompatActivity {
    TextInputEditText adminname, adminpassword;
    Button adminIn;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminname=findViewById(R.id.uname);
        adminpassword=findViewById(R.id.upass);
        adminIn=findViewById(R.id.admin_in);


        adminIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aUsername=adminname.getText().toString();
                String aPassword=adminpassword.getText().toString();

                if (aUsername.equals("admin") && aPassword.equals("admin123") ){
                    Intent intent=new Intent(Admin.this,adminHompage.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Admin.this,"Welcome To Admin Login",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Admin.this,"Wrong username & password",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    public  void onBackPressed(){
        Intent intent=new Intent(Admin.this,Start.class);
        startActivity(intent);
        finish();
    }
}