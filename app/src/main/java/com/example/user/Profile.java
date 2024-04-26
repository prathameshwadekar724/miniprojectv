package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    TextView uname,user;
    Toolbar toolbar;
    TextView pname,pcontact,paddress,pusername,gender,pcity,pstate,postal,pdob,pocc,field;
    TextView titleName;
    ProgressBar progressBar;
    FirebaseAuth auth;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pname=findViewById(R.id.nam_user);
        pcontact=findViewById(R.id.cont_user);
        paddress=findViewById(R.id.add_user);
        gender=findViewById(R.id.pass_user);
        pusername=findViewById(R.id.username_user);
        pcity=findViewById(R.id.city_user);
        pstate=findViewById(R.id.state_user);
        postal=findViewById(R.id.pos_user);
        pdob=findViewById(R.id.dob_user);
        pocc=findViewById(R.id.occupation_user);
        field=findViewById(R.id.field_user);
        titleName=findViewById(R.id.welcome);
        edit=findViewById(R.id.edit);
        progressBar=findViewById(R.id.progressbar);

        drawerLayout = findViewById(R.id.lay_drw);
        navigationView = findViewById(R.id.view_nav);
        View headerView = navigationView.getHeaderView(0);

        uname = headerView.findViewById(R.id.fname);
        user = headerView.findViewById(R.id.fuser);

        toolbar = findViewById(R.id.toolb);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.n_profile);



        auth=FirebaseAuth.getInstance();

        FirebaseUser firebaseUser=auth.getCurrentUser();


        if (firebaseUser==null){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            showDetail(firebaseUser);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    passUserData(firebaseUser);
                }
            });
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
                    String Gender=userDetail.Gender;
                    String Email=firebaseUser.getEmail();
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
                    gender.setText(Gender);
                    pusername.setText(Email);
                    pcity.setText(City);
                    pstate.setText(State);
                    postal.setText(Postal);
                    pdob.setText(Dob);
                    pocc.setText(Occupation);
                    field.setText(Field);

                    uname.setText(Name);
                    user.setText(Email);

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void passUserData(FirebaseUser firebaseUser){
        String username=pusername.getText().toString().trim();
        String id=firebaseUser.getUid();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");

        Query query=reference.orderByChild("Email").equalTo(username);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name=snapshot.child(id).child("Name").getValue(String.class);
                    String contact=snapshot.child(id).child("Contact").getValue(String.class);
                    String address=snapshot.child(id).child("Address").getValue(String.class);
                    String email=snapshot.child(id).child("Email").getValue(String.class);
                    String city=snapshot.child(id).child("City").getValue(String.class);
                    String state=snapshot.child(id).child("State").getValue(String.class);
                    String code=snapshot.child(id).child("Postal").getValue(String.class);
                    String age=snapshot.child(id).child("Dob").getValue(String.class);
                    String occ=snapshot.child(id).child("Occupation").getValue(String.class);
                    String interest=snapshot.child(id).child("Field").getValue(String.class);
                    String gender=snapshot.child(id).child("Gender").getValue(String.class);
                    String password=snapshot.child(id).child("Password").getValue(String.class);

                    Intent intent=new Intent(Profile.this, EditProfile.class);

                    intent.putExtra("Name",name);
                    intent.putExtra("Contact",contact);
                    intent.putExtra("Address",address);
                    intent.putExtra("Email",email);
                    intent.putExtra("City",city);
                    intent.putExtra("State",state);
                    intent.putExtra("Postal",code);
                    intent.putExtra("Dob",age);
                    intent.putExtra("Occupation",occ);
                    intent.putExtra("Field",interest);
                    intent.putExtra("Gender",gender);
                    intent.putExtra("Password",password);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int ID = item.getItemId();

        if (ID == R.id.n_home) {
            Intent intent=new Intent(Profile.this,Homepage.class);
            startActivity(intent);
        } else if (ID == R.id.n_no) {
            Intent intent=new Intent(Profile.this,VNotification.class);
            startActivity(intent);
        } else if (ID == R.id.nList) {
            Intent intent=new Intent(Profile.this, Vlist.class);
            startActivity(intent);
        } else if (ID == R.id.onList) {
            Intent intent=new Intent(Profile.this,organisationuser.class);
            startActivity(intent);
        } else if (ID == R.id.n_profile) {


        }else if (ID == R.id.leaderboard) {
            Intent intent = new Intent(Profile.this, leaderboard_activity.class);
            startActivity(intent);
        }
        else if (ID == R.id.n_logout) {

            auth.signOut();
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Profile.this, Start.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            finish();

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}