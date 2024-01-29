package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;














    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        drawerLayout = findViewById(R.id.lay_drw);
        navigationView = findViewById(R.id.view_nav);
        View headerView=navigationView.getHeaderView(0);

        TextView uname=headerView.findViewById(R.id.fname);
        TextView user=headerView.findViewById(R.id.fuser);
        Intent intent=getIntent();
        String nameuser=intent.getStringExtra("Name");
        String userusername=intent.getStringExtra("eUsername");
        uname.setText(nameuser);
        user.setText(userusername);
        toolbar =findViewById(R.id.toolb);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.n_home);



    }




    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int ID=item.getItemId();

            if (ID == R.id.n_home) {


            } else if (ID == R.id.n_feed) {
                Intent intent = new Intent(Homepage.this, Post.class);
                startActivity(intent);
            } else if (ID == R.id.n_no) {
                Toast.makeText(this, "Open Notification", Toast.LENGTH_SHORT).show();
            } else if (ID==R.id.n_profile) {
                Intent intent=getIntent();
                String nameuser=intent.getStringExtra("Name");
                String userusername=intent.getStringExtra("eUsername");
                String contact=intent.getStringExtra("Contact");
                String address=intent.getStringExtra("Address");
                String Password=intent.getStringExtra("Password");
                String city=intent.getStringExtra("City");
                String state=intent.getStringExtra("State");
                String postal=intent.getStringExtra("Postal Code");
                String dob=intent.getStringExtra("Date of birth");
                String occupation=intent.getStringExtra("Occupation");
                String Field=intent.getStringExtra("Area of Interest");

                Intent profile = new Intent(Homepage.this,Profile.class);
                profile.putExtra("Name", nameuser);
                profile.putExtra("Contact", contact);
                profile.putExtra("Address",address);
                profile.putExtra("eUsername", userusername);
                profile.putExtra("Password", Password);
                profile.putExtra("City",city);
                profile.putExtra("State",state);
                profile.putExtra("Postal Code",postal);
                profile.putExtra("Date of birth",dob);
                profile.putExtra("Occupation",occupation);
                profile.putExtra("Area of Interest",Field);
                startActivity(profile);
                Toast.makeText(this, "Opening Profile", Toast.LENGTH_SHORT).show();


            } else if (ID == R.id.n_logout) {


                Intent intent=new Intent(Homepage.this, Start.class);
                startActivity(intent);
                Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                finish();

            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;




    }



}