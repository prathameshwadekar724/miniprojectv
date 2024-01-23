package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity  {

    private EditText input1,input2,input3,input4;
    private Button signUp;

    private DatabaseReference rootDatabaseref1,rootDatabaseref2,rootDatabaseref3,rootDatabaseref4;
    private TextView textView,textView1,textView2,textView3;

    private Button Read;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setContentView(R.layout.activity_registeration);


        input1=findViewById(R.id.name);
        input2=findViewById(R.id.contact);
        input3=findViewById(R.id.email);
        input4=findViewById(R.id.password);

        signUp=findViewById(R.id.sign_up);
        Read=findViewById(R.id.show);

        textView=findViewById(R.id.textView);
        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);



        rootDatabaseref1= FirebaseDatabase.getInstance().getReference().child("Name");
        rootDatabaseref2= FirebaseDatabase.getInstance().getReference().child("Contact");
        rootDatabaseref3= FirebaseDatabase.getInstance().getReference().child("Email");
        rootDatabaseref4= FirebaseDatabase.getInstance().getReference().child("Password");


        Read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootDatabaseref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            String data1=snapshot.getValue().toString();
                            textView.setText(data1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Profile.this, "Errorr", Toast.LENGTH_SHORT).show();

                    }
                });
                rootDatabaseref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            String data2=snapshot.getValue().toString();
                            textView1.setText(data2);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Profile.this, "Errorr", Toast.LENGTH_SHORT).show();

                    }
                });
                rootDatabaseref3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            String data3=snapshot.getValue().toString();
                            textView2.setText(data3);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Profile.this, "Errorr", Toast.LENGTH_SHORT).show();

                    }
                });
                rootDatabaseref4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            String data4=snapshot.getValue().toString();
                            textView3.setText(data4);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Profile.this, "Errorr", Toast.LENGTH_SHORT).show();

                    }
                });
            }

        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data1=input1.getText().toString();
                String data2=input2.getText().toString();
                String data3=input3.getText().toString();
                String data4=input4.getText().toString();

                rootDatabaseref1.setValue(data1);
                rootDatabaseref2.setValue(data2);
                rootDatabaseref3.setValue(data3);
                rootDatabaseref4.setValue(data4);



            }
        });


    }
}