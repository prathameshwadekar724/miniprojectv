package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
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

import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registerpage extends AppCompatActivity {


    EditText eName,eContact,Gender,eEmail,ePassword,ecpassword,eaddress,ecity,estate,pcode,edob,eoccupation,efield;
    Button signUp;
    TextView signIn;

    private ProgressBar progressBar;
    private DatePickerDialog picker;

    private static final String TAG="Registerpage";

    ArrayAdapter arrayAdapter;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://user-4d03f-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        eName=findViewById(R.id.name);
        eContact=findViewById(R.id.contact);
        eaddress=findViewById(R.id.address);
        Gender=findViewById(R.id.gender);
        eEmail = findViewById(R.id.Email);
        ePassword = findViewById(R.id.password);
        ecpassword = findViewById(R.id.c_password);
        ecity=findViewById(R.id.city);
        estate=findViewById(R.id.state);
        pcode=findViewById(R.id.postalcode);
        edob=findViewById(R.id.dob);
        eoccupation=findViewById(R.id.occupation);
        efield = findViewById(R.id.interest);
        signIn = findViewById(R.id.sign_in);
        signUp =findViewById(R.id.sign_up);
        progressBar=findViewById(R.id.progressbar);

        edob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar= Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);



                picker=new DatePickerDialog(Registerpage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar birthDate = Calendar.getInstance();
                        birthDate.set(year, month, dayOfMonth);
                        Calendar currentDate = Calendar.getInstance();
                        int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
                        if (currentDate.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
                            age--;
                        }
                        edob.setText(String.valueOf(age+ " Years"));
                    }
                },year,month,day);
                picker.show();
            }
        });


        Spinner spinner = findViewById(R.id.spinner);
        Spinner spinner1 = findViewById(R.id.spinner1);

        String[] options = {"Select Your Occupation","12th", "Diploma", "UG", "PG", "Others"};
        String[] options1 = {"Select Your Interest","Healthcare", "Education", "Animal Welfare", "Environment and Conservation","Social Service","Others"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedOption = options[position];

                if (selectedOption!=options[0]){

                    Toast.makeText(Registerpage.this,"Occupation is selected",Toast.LENGTH_SHORT).show();
                    if (selectedOption.equals(options[5])){

                        eoccupation.setFocusable(true);

                        Toast.makeText(Registerpage.this, "Enter Your Occupation", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    eoccupation.setText(null);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption1 = options1[position];

                if (selectedOption1!=options1[0]){
                    Toast.makeText(Registerpage.this,"area of interest is selected",Toast.LENGTH_SHORT).show();
                    if (selectedOption1.equals(options1[6])){
                        efield.setText(null);
                        efield.setFocusable(true);
                        Toast.makeText(Registerpage.this, "Enter Your Interest", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    efield.setText(null);

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });






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
                String name=eName.getText().toString();
                String contact=eContact.getText().toString();
                String address=eaddress.getText().toString();
                String gender=Gender.getText().toString();
                String email=eEmail.getText().toString();
                String password=ePassword.getText().toString();
                String cpassword=ecpassword.getText().toString();
                String city=ecity.getText().toString();
                String state=estate.getText().toString();
                String postal=pcode.getText().toString();
                String dob=edob.getText().toString();
                String occupation=spinner.getSelectedItem().toString();
                String field =spinner1.getSelectedItem().toString();


                String mobile="[6-9][0-9]{9}";
                Matcher mobilematcher;
                Pattern pattern=Pattern.compile(mobile);
                mobilematcher=pattern.matcher(contact);




                if (name.isEmpty() || contact.isEmpty() || email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(Registerpage.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Registerpage.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    eEmail.setError("Email is required");
                    eEmail.requestFocus();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(Registerpage.this, "Please re-Enter Your Email", Toast.LENGTH_SHORT).show();
                    eEmail.setError("Valid Email is required");
                    eEmail.requestFocus();

                } else if (contact.length() !=10) {
                    Toast.makeText(Registerpage.this, "Please re-Enter your mobile no.", Toast.LENGTH_SHORT).show();
                    eContact.setError("Mobile no.should be 10 digits");
                    eContact.requestFocus();
                } else if (!mobilematcher.find()) {
                    Toast.makeText(Registerpage.this, "Please re-enter your mobile no", Toast.LENGTH_SHORT).show();
                    eContact.setError("Mobile no is not valid");
                    eContact.requestFocus();

                } else if (password.length() < 6) {
                    Toast.makeText(Registerpage.this, "Password should be at least 6 digits", Toast.LENGTH_SHORT).show();
                    ePassword.setError("Password is to weak");
                    ePassword.requestFocus();
                } else if (!password.equals(cpassword)) {
                    Toast.makeText(Registerpage.this, "Password not matched", Toast.LENGTH_SHORT).show();
                    ecpassword.setError("Password Confirmation is required");
                    ecpassword.requestFocus();

                } else{
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseAuth auth=FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Registerpage.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Registerpage.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                FirebaseUser firebaseUser=auth.getCurrentUser();

                                UserDetail detail=new UserDetail(name,contact,address,gender,email,password,city,state,postal,dob,occupation,field);

                                databaseReference.child("Users").child(firebaseUser.getUid()).setValue(detail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            firebaseUser.sendEmailVerification();
                                            Toast.makeText(Registerpage.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(Registerpage.this,MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(Registerpage.this, "User Registered Failed", Toast.LENGTH_SHORT).show();

                                        }
                                        progressBar.setVisibility(View.GONE);

                                    }
                                });


                            }
                            else{
                                try{
                                    throw task.getException();
                                }catch (FirebaseAuthWeakPasswordException e){
                                    ePassword.setError("Your password is to weak.Kindly use a mix alphabets,numbers and special character");
                                    ePassword.requestFocus();
                                }catch (FirebaseAuthInvalidCredentialsException e){
                                    ePassword.setError("Email is invalid or already use");
                                    ePassword.requestFocus();
                                }catch (FirebaseAuthUserCollisionException e){
                                    ePassword.setError("Email is already use");
                                    ePassword.requestFocus();
                                }catch (Exception e){
                                    Log.e(TAG,e.getMessage());
                                    Toast.makeText(Registerpage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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