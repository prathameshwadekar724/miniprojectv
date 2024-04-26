package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class leaderboard_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth auth;

    TextView uname,user;
    Toolbar toolbar;

    ProgressBar progressBar;
    RecyclerView recyclerView;
    ArrayList<Average> dataList;
    MyAdapter8 adapter;
    final private DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Average");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard2);
        progressBar = findViewById(R.id.progressbar);
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

        navigationView.setCheckedItem(R.id.leaderboard);



        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dataList = new ArrayList<>();
        adapter = new MyAdapter8(dataList, this);
        recyclerView.setAdapter(adapter);

        auth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=auth.getCurrentUser();

        if (firebaseUser==null){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        else {

            progressBar.setVisibility(View.VISIBLE);
            showDetail(firebaseUser);

        }


        progressBar.setVisibility(View.VISIBLE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Average information=dataSnapshot.getValue(Average.class);
                        dataList.add(information);

                    }
                    Collections.sort(dataList, new Comparator<Average>() {
                        @Override
                        public int compare(Average o1, Average o2) {
                            return Double.compare(o2.getAverage(),o1.getAverage());
                        }
                    });
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(leaderboard_activity.this, "Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
    private void showDetail(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDetail detail=snapshot.getValue(UserDetail.class);
                if (detail!=null){
                    String Name=detail.Name;
                    String Email=firebaseUser.getEmail();

                    uname.setText(Name);
                    user.setText(Email);

                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(leaderboard_activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int ID = item.getItemId();

        if (ID == R.id.n_home) {
            Intent intent=new Intent(leaderboard_activity.this,Homepage.class);
            startActivity(intent);

        } else if (ID == R.id.n_no) {
            Intent intent=new Intent(leaderboard_activity.this,VNotification.class);
            startActivity(intent);
        } else if (ID == R.id.nList) {
            Intent intent=new Intent(leaderboard_activity.this, Vlist.class);
            startActivity(intent);
        } else if (ID == R.id.onList) {
            Intent intent=new Intent(leaderboard_activity.this,organisationuser.class);
            startActivity(intent);
        } else if (ID == R.id.n_profile) {
            Intent profile = new Intent(leaderboard_activity.this, Profile.class);
            startActivity(profile);
            Toast.makeText(this, "Opening Profile", Toast.LENGTH_SHORT).show();


        }else if (ID == R.id.leaderboard) {

        }
        else if (ID == R.id.n_logout) {

            auth.signOut();
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(leaderboard_activity.this, Start.class);
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