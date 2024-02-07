package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registerpage2 extends AppCompatActivity {

    EditText orgname,lc,orgaddress,ocontact,otype,ouser,opass,copass;
    Button signUp;
    TextView signIn;
    Spinner spinner;

    ArrayAdapter arrayAdapter;
    ProgressBar progressBar;
    private static final String TAG="registerpage2";

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://user-4d03f-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage2);

        orgname=findViewById(R.id.name);
        lc=findViewById(R.id.license);
        orgaddress=findViewById(R.id.address);
        ocontact=findViewById(R.id.contact);
        otype=findViewById(R.id.interest);
        ouser=findViewById(R.id.email);
        opass=findViewById(R.id.password);
        copass=findViewById(R.id.c_password);
        signIn = findViewById(R.id.sign_in);
        signUp =findViewById(R.id.sign_up);
        progressBar=findViewById(R.id.progressbar);

        Spinner spinner = findViewById(R.id.spinner);

        String[] options = {"Select Type Of Organisation","Healthcare", "Education", "Animal Welfare", "Environment and Conservation","Social Service","Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = options[position];

                if (selectedOption!=options[0]){
                    otype.setText(selectedOption);
                    Toast.makeText(registerpage2.this, "type is selected", Toast.LENGTH_SHORT).show();
                    if (selectedOption.equals(options[6])){
                        otype.setText(null);
                        otype.setFocusable(true);
                        otype.setFocusableInTouchMode(true);

                    }
                }
                else {
                    otype.setText(null);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerpage2.this,orglogin.class);
                startActivity(intent);
                finish();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=orgname.getText().toString();
                String license=lc.getText().toString();
                String address=orgaddress.getText().toString();
                String contact=ocontact.getText().toString();
                String type=otype.getText().toString();
                String email=ouser.getText().toString();
                String password=opass.getText().toString();
                String cpassword=copass.getText().toString();

                String mobile="[6-9][0-9]{9}";
                Matcher mobilematcher;
                Pattern pattern=Pattern.compile(mobile);
                mobilematcher=pattern.matcher(contact);


                if(name.isEmpty() || contact.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(registerpage2.this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(registerpage2.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    ouser.setError("Email is required");
                    ouser.requestFocus();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(registerpage2.this, "Please re-Enter Your Email", Toast.LENGTH_SHORT).show();
                    ouser.setError("Valid Email is required");
                    ouser.requestFocus();

                } else if (contact.length() !=10) {
                    Toast.makeText(registerpage2.this, "Please re-Enter your mobile no.", Toast.LENGTH_SHORT).show();
                    ocontact.setError("Mobile no.should be 10 digits");
                    ocontact.requestFocus();
                } else if (!mobilematcher.find()) {
                    Toast.makeText(registerpage2.this, "Please re-enter your mobile no", Toast.LENGTH_SHORT).show();
                    ocontact.setError("Mobile no is not valid");
                    ocontact.requestFocus();

                } else if (password.length() < 6) {
                    Toast.makeText(registerpage2.this, "Password should be at least 6 digits", Toast.LENGTH_SHORT).show();
                    opass.setError("Password is to weak");
                    opass.requestFocus();
                } else if (!password.equals(cpassword)) {
                    Toast.makeText(registerpage2.this, "Password not matched", Toast.LENGTH_SHORT).show();
                    opass.setError("Password Confirmation is required");
                    opass.requestFocus();

                } else{
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseAuth auth=FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(registerpage2.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(registerpage2.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                FirebaseUser user=auth.getCurrentUser();
                                OrgUserDetail orgUserDetail=new OrgUserDetail(name,license,address,contact,email,password,type);
                                databaseReference.child("Organisation").child(user.getUid()).setValue(orgUserDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            user.sendEmailVerification();
                                            Toast.makeText(registerpage2.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(registerpage2.this,orglogin.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(registerpage2.this, "User Registered Failed", Toast.LENGTH_SHORT).show();

                                        }
                                        progressBar.setVisibility(View.GONE);

                                    }
                                });

                            }
                            else{
                                try{
                                    throw task.getException();
                                }catch (FirebaseAuthWeakPasswordException e){
                                    opass.setError("Your password is to weak.Kindly use a mix alphabets,numbers and special character");
                                    opass.requestFocus();
                                }catch (FirebaseAuthInvalidCredentialsException e){
                                    opass.setError("Email is invalid or already use");
                                    opass.requestFocus();
                                }catch (FirebaseAuthUserCollisionException e){
                                    opass.setError("Email is already use");
                                    opass.requestFocus();
                                }catch (Exception e){
                                    Log.e(TAG,e.getMessage());
                                    Toast.makeText(registerpage2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }

                        }
                    });

                }

            }
        });





    }
}