package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText Username, editTextPassword;
    private Button signIn;
    private TextView signUp;
    private ProgressBar progressBar;
    private static final String TAG="MainActivity";

    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://user-4d03f-default-rtdb.firebaseio.com/");
    private FirebaseAuth auth;
    private Button forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signIn = findViewById(R.id.sign_in);
        signUp =findViewById(R.id.sign_up);
        progressBar=findViewById(R.id.progressbar);
        forgot=findViewById(R.id.forgot);

        auth=FirebaseAuth.getInstance();

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=Username.getText().toString();
                String Password=editTextPassword.getText().toString();

                if (TextUtils.isEmpty(Email)){
                    Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    Username.setError("Email is required");
                    Username.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    Toast.makeText(MainActivity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    Username.setError("Invalid email");
                    Username.requestFocus();
                } else if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(MainActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                FirebaseUser user=auth.getCurrentUser();
                                if(user.isEmailVerified()){
                                    Toast.makeText(MainActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, Homepage.class));
                                    finish();
                                }else{
                                    user.sendEmailVerification();
                                    auth.signOut();
                                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
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
                                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registerpage.class);
                startActivity(intent);
                finish();
            }
        });



    }
    public void onStart(){
        super.onStart();
        if (auth.getCurrentUser()!=null){
            Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Homepage.class));
            finish();

        }
        else{
            Toast.makeText(this, "You can logged in now", Toast.LENGTH_SHORT).show();
        }
    }
    public  void onBackPressed(){
        Intent intent=new Intent(MainActivity.this,Start.class);
        startActivity(intent);
        finish();

    }
}