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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity  {

    TextView pname,pcontact,paddress,pusername,ppassword,pcity,pstate,postal,pdob,pocc,field;
    TextView titleName;
    ProgressBar progressBar;
    FirebaseAuth auth;

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
        progressBar=findViewById(R.id.progressbar);

        auth=FirebaseAuth.getInstance();

        FirebaseUser firebaseUser=auth.getCurrentUser();


        if (firebaseUser==null){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            showDetail(firebaseUser);
        }



    }

    private void showDetail(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDetail userDetail=snapshot.getValue(UserDetail.class);
                if (userDetail!=null){
                    String Name=userDetail.Name;
                    String Contact=userDetail.Contact;
                    String Address=userDetail.Address;
                    String Email=firebaseUser.getEmail();
                    String Password=userDetail.Password;
                    String City=userDetail.City;
                    String State=userDetail.State;
                    String Postal=userDetail.Postal;
                    String Dob=userDetail.Dob;
                    String Occupation=userDetail.Occupation;
                    String Field=userDetail.Field;


                    titleName.setText(Name);
                    pname.setText(Name);
                    pcontact.setText(Contact);
                    paddress.setText(Address);
                    pusername.setText(Email);
                    ppassword.setText(Password);
                    pcity.setText(City);
                    pstate.setText(State);
                    postal.setText(Postal);
                    pdob.setText(Dob);
                    pocc.setText(Occupation);
                    field.setText(Field);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

}