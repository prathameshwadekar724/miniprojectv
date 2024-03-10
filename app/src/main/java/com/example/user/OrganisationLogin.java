package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrganisationLogin extends AppCompatActivity {
    TextInputEditText Username, editTextPassword;
    Button signIn;
    TextView signUp;

    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://user-4d03f-default-rtdb.firebaseio.com/");
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private static final String TAG="orglogin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation_login);

        Username = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);
        progressBar=findViewById(R.id.progressbar);
        auth=FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrganisationLogin.this, Registeration2.class);
                startActivity(intent);
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=Username.getText().toString();
                String Password=editTextPassword.getText().toString();

                if (TextUtils.isEmpty(Email)){
                    Toast.makeText(OrganisationLogin.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    Username.setError("Email is required");
                    Username.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    Toast.makeText(OrganisationLogin.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    Username.setError("Invalid email");
                    Username.requestFocus();
                } else if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(OrganisationLogin.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);

                    auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(OrganisationLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user=auth.getCurrentUser();
                                if (user.isEmailVerified()){
                                    Toast.makeText(OrganisationLogin.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(OrganisationLogin.this, OrganisationHome.class));
                                    finish();

                                }
                                else {
                                    user.sendEmailVerification();
                                    auth.signOut();
                                    AlertDialog.Builder builder=new AlertDialog.Builder(OrganisationLogin.this);
                                    builder.setTitle("Email not verified");
                                    builder.setMessage("Please verify your email");

                                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent=new Intent(Intent.ACTION_MAIN);
                                            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });
                                    AlertDialog alertDialog=builder.create();
                                    alertDialog.show();

                                }
                            }
                            else {
                                try {
                                    throw task.getException();
                                }catch (FirebaseAuthInvalidUserException e){
                                    Username.setError("User does not exists");
                                    Username.requestFocus();
                                }catch (FirebaseAuthInvalidCredentialsException e){
                                    Username.setError("Invalid credentials");
                                    Username.requestFocus();
                                }catch (Exception e){
                                    Log.e(TAG,e.getMessage());
                                    Toast.makeText(OrganisationLogin.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }
            }
        });



    }
    public void onStart(){
        super.onStart();
        if (auth.getCurrentUser()!=null){
            Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(OrganisationLogin.this, OrganisationHome.class));
            finish();

        }
        else{
            Toast.makeText(this, "You can logged now", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(OrganisationLogin.this, Start.class);
        startActivity(intent);
        finish();

    }
}