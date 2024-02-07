package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrgProfile extends AppCompatActivity {
    TextView name,license,address,contact,type,email,password;
    TextView titleName;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_profile);

        name=findViewById(R.id.nam_user);
        license=findViewById(R.id.license);
        address=findViewById(R.id.add_user);
        contact=findViewById(R.id.contact);
        type=findViewById(R.id.type);
        email=findViewById(R.id.username);
        password=findViewById(R.id.password);
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
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Organisation");
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrgUserDetail orgUserDetail=snapshot.getValue(OrgUserDetail.class);
                if (orgUserDetail!=null){
                    String Name=orgUserDetail.Name;
                    String License=orgUserDetail.License;
                    String Contact=orgUserDetail.Contact;
                    String Address=orgUserDetail.Address;
                    String Email=firebaseUser.getEmail();
                    String Password=orgUserDetail.Password;
                    String Type=orgUserDetail.Type;

                    titleName.setText(Name);
                    name.setText(Name);
                    license.setText(License);
                    contact.setText(Contact);
                    address.setText(Address);
                    type.setText(Type);
                    email.setText(Email);
                    password.setText(Password);

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrgProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}