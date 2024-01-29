package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

    TextView pname,pcontact,paddress,pusername,ppassword,pcity,pstate,postal,pdob,pocc,field;
    TextView titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

          pname=findViewById(R.id.nam_user);
          pcontact=findViewById(R.id.cont_user);
          paddress=findViewById(R.id.add_user);
          pusername=findViewById(R.id.username_user);
          ppassword=findViewById(R.id.pass_user);
          pcity=findViewById(R.id.city_user);
          pstate=findViewById(R.id.state_user);
          postal=findViewById(R.id.pos_user);
          pdob=findViewById(R.id.dob_user);
          pocc=findViewById(R.id.occupation_user);
          field=findViewById(R.id.field_user);
          titleName=findViewById(R.id.welcome);




        showUserData();


    }
    public void showUserData(){
        Intent intent=getIntent();
        String nameuser=intent.getStringExtra("Name");
        String contactuser=intent.getStringExtra("Contact");
        String addressuser=intent.getStringExtra("Address");
        String user=intent.getStringExtra("eUsername");
        String up=intent.getStringExtra("Password");
        String cityuser=intent.getStringExtra("City");
        String stateuser=intent.getStringExtra("State");
        String postaluser=intent.getStringExtra("Postal Code");
        String dobuser=intent.getStringExtra("Date of birth");
        String occupationuser=intent.getStringExtra("Occupation");
        String fieldu=intent.getStringExtra("Area of Interest");

        titleName.setText(nameuser);
        pname.setText(nameuser);
        pcontact.setText(contactuser);
        paddress.setText(addressuser);
        pusername.setText(user);
        ppassword.setText(up);
        pcity.setText(cityuser);
        pstate.setText(stateuser);
        postal.setText(postaluser);
        pdob.setText(dobuser);
        pocc.setText(occupationuser);
        field.setText(fieldu);
    }

}