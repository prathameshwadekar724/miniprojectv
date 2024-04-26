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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;

public class Notification extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth auth;

    TextView uname,user;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Message> dataList;
    MyAdapter3 adapter;

    ProgressBar progressBar;
    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Posts").child("Applied");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

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

        navigationView.setCheckedItem(R.id.n_no);

        progressBar=findViewById(R.id.progressbar);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        dataList=new ArrayList<>();
        adapter=new MyAdapter3(dataList,this);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);
        auth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=auth.getCurrentUser();
        String userId=firebaseUser.getUid();

        if (firebaseUser==null){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        else {

            progressBar.setVisibility(View.VISIBLE);
            showDetail(firebaseUser);
        }


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Organisation").child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String oName=snapshot.child("Name").getValue(String.class);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                for (DataSnapshot userSnapshot : postSnapshot.getChildren()) {
                                    Message message=userSnapshot.getValue(Message.class);
                                    if (oName.equals(message.getOrgName())){
                                        dataList.add(message);
                                    }

                                }
                            }
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Notification.this, "error", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void showDetail(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Organisation");
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrgUserDetail detail=snapshot.getValue(OrgUserDetail.class);
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
                Toast.makeText(Notification.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int ID = item.getItemId();

        if (ID == R.id.n_home) {
            Intent intent=new Intent(Notification.this,OrganisationHome.class);
            startActivity(intent);
        }else if (ID == R.id.n_feed) {
            Intent intent = new Intent(Notification.this, Post.class);
            startActivity(intent);
        }
        else if (ID == R.id.n_no) {

        } else if (ID == R.id.nList) {
            Intent intent=new Intent(Notification.this, VolunteerList.class);
            startActivity(intent);

        } else if (ID == R.id.onList) {
            Intent intent=new Intent(Notification.this, Olist.class);
            startActivity(intent);

        } else if (ID == R.id.n_profile) {
            Intent profile = new Intent(Notification.this, OrgProfile.class);
            startActivity(profile);
            Toast.makeText(this, "Opening Profile", Toast.LENGTH_SHORT).show();


        }else if (ID == R.id.leaderboard) {
            Intent intent = new Intent(Notification.this, leaderboard.class);
            startActivity(intent);
        }
        else if (ID == R.id.n_logout) {

            auth.signOut();
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Notification.this, Start.class);
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