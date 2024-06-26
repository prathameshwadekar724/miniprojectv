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

public class VNotification extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth auth;

    TextView uname,user;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Msg> dataList;
    MyAdapter6 adapter;
    ProgressBar progressBar;
    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Posts").child("Approved");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vnotification);

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
        navigationView.setCheckedItem(R.id.n_no);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataList=new ArrayList<>();
        adapter=new MyAdapter6(dataList,this);
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


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String vName=snapshot.child("Name").getValue(String.class);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                for (DataSnapshot usersnapshot :dataSnapshot.getChildren()){
                                    Msg message=usersnapshot.getValue(Msg.class);
                                    if (vName.equals(message.getName())){
                                        dataList.add(message);
                                    }
                                }


                            }
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(VNotification.this, "error", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(VNotification.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int ID = item.getItemId();

        if (ID == R.id.n_home) {
            Intent intent=new Intent(VNotification.this,Homepage.class);
            startActivity(intent);

        } else if (ID == R.id.n_no) {

        } else if (ID == R.id.nList) {
            Intent intent=new Intent(VNotification.this, Vlist.class);
            startActivity(intent);
        } else if (ID == R.id.onList) {
            Intent intent=new Intent(VNotification.this,organisationuser.class);
            startActivity(intent);
        } else if (ID == R.id.n_profile) {
            Intent profile = new Intent(VNotification.this, Profile.class);
            startActivity(profile);



        }else if (ID == R.id.leaderboard) {
            Intent intent=new Intent(VNotification.this,leaderboard_activity.class);
            startActivity(intent);
        }
        else if (ID == R.id.n_logout) {

            auth.signOut();
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(VNotification.this, Start.class);
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