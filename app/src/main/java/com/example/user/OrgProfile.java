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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrgProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    DrawerLayout drawerLayout;
    NavigationView navigationView;

    TextView uname,user;
    Toolbar toolbar;
    TextView name,license,address,contact,type,email;
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
        titleName=findViewById(R.id.welcome);
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
                    String Type=orgUserDetail.Type;

                    titleName.setText(Name);
                    name.setText(Name);
                    license.setText(License);
                    contact.setText(Contact);
                    address.setText(Address);
                    type.setText(Type);
                    email.setText(Email);

                    uname.setText(Name);
                    user.setText(Email);

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrgProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int ID = item.getItemId();

        if (ID == R.id.n_home) {
            Intent intent=new Intent(OrgProfile.this,OrganisationHome.class);
            startActivity(intent);
        }else if (ID == R.id.n_feed) {
            Intent intent=new Intent(OrgProfile.this, Post.class);
            startActivity(intent);
        }
        else if (ID == R.id.n_no) {
            Intent intent=new Intent(OrgProfile.this,Notification.class);
            startActivity(intent);
        } else if (ID == R.id.nList) {
            Intent intent=new Intent(OrgProfile.this, VolunteerList.class);
            startActivity(intent);

        } else if (ID == R.id.onList) {
            Intent intent=new Intent(OrgProfile.this,Olist.class);
            startActivity(intent);

        } else if (ID == R.id.n_profile) {



        }else if (ID == R.id.leaderboard) {
            Intent intent = new Intent(OrgProfile.this, leaderboard.class);
            startActivity(intent);
        }
        else if (ID == R.id.n_logout) {

            auth.signOut();
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrgProfile.this, Start.class);
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