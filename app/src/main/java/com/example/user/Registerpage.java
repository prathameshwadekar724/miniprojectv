package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Registerpage extends AppCompatActivity {


    EditText eName,eContact,eUsername, ePassword;
    Button signUp;
    TextView signIn;


    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://user-4d03f-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        eName=findViewById(R.id.name);
        eContact=findViewById(R.id.contact);
        eUsername = findViewById(R.id.email);
        ePassword = findViewById(R.id.password);
        signIn = findViewById(R.id.sign_in);
        signUp =findViewById(R.id.sign_up);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registerpage.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editName=eName.getText().toString();
                String editContact=eContact.getText().toString();
                String editTextUsername=eUsername.getText().toString();
                String editTextPassword=ePassword.getText().toString();


                if (editName.isEmpty() || editContact.isEmpty() || editTextUsername.isEmpty() || editTextPassword.isEmpty())
                {
                    Toast.makeText(Registerpage.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(editTextUsername)){
                                Toast.makeText(Registerpage.this,"Username already exists",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                databaseReference.child("users").child(editTextUsername).child("editName").setValue(eName);
                                databaseReference.child("users").child(editTextUsername).child("editContact").setValue(eContact);

                                databaseReference.child("users").child(editTextUsername).child("editTextPassword").setValue(ePassword);



                                Helperclass helperclass = new Helperclass(editName,editContact,editTextUsername,editTextPassword);
                                databaseReference.child(editTextUsername).setValue(helperclass);

                                Toast.makeText(Registerpage.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Registerpage.this,MainActivity.class);
                                startActivity(intent);
                                finish();


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Registerpage.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });

    }
}