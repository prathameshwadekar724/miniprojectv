package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class adminHompage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hompage);

        drawerLayout = findViewById(R.id.lay_drw);
        navigationView = findViewById(R.id.view_nav);
        toolbar = findViewById(R.id.toolb);
        View headerView = navigationView.getHeaderView(0);

        TextView uname = headerView.findViewById(R.id.fname);
        TextView user = headerView.findViewById(R.id.fuser);
        Intent intent = getIntent();
        String nameuser = intent.getStringExtra("admin");
        uname.setText(nameuser);





        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // navigationView.setCheckedItem(R.id.n_home);




    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int ID = item.getItemId();

        if (ID == R.id.n_home) {
            Intent intent=new Intent(adminHompage.this,AdHome.class);
            startActivity(intent);

        }  else if (ID == R.id.n_org) {
            Intent intent = new Intent(adminHompage.this, organisationuser.class);
            startActivity(intent);


        } else if (ID == R.id.n_logout) {


            Intent intent = new Intent(adminHompage.this, Start.class);
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