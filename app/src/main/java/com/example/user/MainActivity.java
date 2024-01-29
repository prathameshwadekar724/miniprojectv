package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextInputEditText Username, editTextPassword;
    Button signIn;
    TextView signUp;

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://user-4d03f-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signIn = findViewById(R.id.sign_in);
        signUp =findViewById(R.id.sign_up);










        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registerpage.class);
                startActivity(intent);
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eUsername=Username.getText().toString();
                String ePassword=editTextPassword.getText().toString();

                if (eUsername.isEmpty() || ePassword.isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter username and password",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(eUsername)){

                                String getPassword=snapshot.child(eUsername).child("Password").getValue(String.class);


                                if(getPassword.equals(ePassword)){



                                    String Name=snapshot.child(eUsername).child("Name").getValue(String.class);
                                    String Contact=snapshot.child(eUsername).child("Contact").getValue(String.class);
                                    String Address=snapshot.child(eUsername).child("Address").getValue(String.class);
                                    String City=snapshot.child(eUsername).child("City").getValue(String.class);
                                    String State=snapshot.child(eUsername).child("State").getValue(String.class);
                                    String Postal=snapshot.child(eUsername).child("Postal Code").getValue(String.class);
                                    String Dob=snapshot.child(eUsername).child("Date of birth").getValue(String.class);
                                    String Occupation=snapshot.child(eUsername).child("Occupation").getValue(String.class);
                                    String Field=snapshot.child(eUsername).child("Area of Interest").getValue(String.class);

                                    Intent intent=new Intent(MainActivity.this, Homepage.class);
                                    intent.putExtra("Name",Name);
                                    intent.putExtra("Contact",Contact);
                                    intent.putExtra("Address",Address);
                                    intent.putExtra("eUsername",eUsername);
                                    intent.putExtra("Password",getPassword);
                                    intent.putExtra("City",City);
                                    intent.putExtra("State",State);
                                    intent.putExtra("Postal Code",Postal);
                                    intent.putExtra("Date of birth",Dob);
                                    intent.putExtra("Occupation",Occupation);
                                    intent.putExtra("Area of Interest",Field);



                                    startActivity(intent);
                                    Toast.makeText(MainActivity.this,"Login successfull",Toast.LENGTH_SHORT).show();


                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Wrong password",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Wrong password",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });



    }
    public  void onBackPressed(){
        Intent intent=new Intent(MainActivity.this,Start.class);
        startActivity(intent);
        finish();
    }
}