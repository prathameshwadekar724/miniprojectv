package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class OrganisationHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth auth;

    TextView uname,user;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    MyAdapter2 adapter2;
    ArrayList<Information> info;

    RecyclerView recyclerView1;
    ArrayList<Data> dataList;
    MyAdapter5 adapter;


    final private DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference("Posts").child("Upload");




    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation_home);

        drawerLayout = findViewById(R.id.lay_drw);
        navigationView = findViewById(R.id.view_nav);
        View headerView = navigationView.getHeaderView(0);

        uname = headerView.findViewById(R.id.fname);
        user = headerView.findViewById(R.id.fuser);
        progressBar=findViewById(R.id.progressbar);

        recyclerView=findViewById(R.id.oList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        info=new ArrayList<>();

        adapter2=new MyAdapter2(info);
        recyclerView.setAdapter(adapter2);


        auth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=auth.getCurrentUser();


        if (firebaseUser==null){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        else {

            if (!firebaseUser.isEmailVerified()){
                showAlertDialog();
            }
            progressBar.setVisibility(View.VISIBLE);
            showDetail(firebaseUser);
        }



        toolbar=findViewById(R.id.toolb);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(OrganisationHome.this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.n_home);

        recyclerView1=findViewById(R.id.recyclerview);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        dataList=new ArrayList<>();
        adapter=new MyAdapter5(dataList,this);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);

        String userId=firebaseUser.getUid();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Organisation").child(userId);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String oName=snapshot.child("Name").getValue(String.class);
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                Data dataClass=dataSnapshot.getValue(Data.class);
                                if (oName.equals(dataClass.getName())){
                                    dataList.add(dataClass);
                                }

                            }
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(OrganisationHome.this, "Error", Toast.LENGTH_SHORT).show();
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



    private void showAlertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(OrganisationHome.this);
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
                Toast.makeText(OrganisationHome.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        } );
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


        } else if (ID == R.id.n_feed) {
            Intent intent = new Intent(OrganisationHome.this, Post.class);
            startActivity(intent);
        } else if (ID == R.id.n_no) {
            Intent intent=new Intent(OrganisationHome.this, Notification.class);
            startActivity(intent);
            Toast.makeText(this, "Open Notification", Toast.LENGTH_SHORT).show();
        } else if (ID == R.id.n_profile) {
            Intent intent=new Intent(OrganisationHome.this, OrgProfile.class);
            startActivity(intent);

            Toast.makeText(this, "Opening Profile", Toast.LENGTH_SHORT).show();


        } else if (ID == R.id.nList) {
            Intent intent=new Intent(OrganisationHome.this,Vlist.class);
            startActivity(intent);

        } else if (ID == R.id.onList) {
            Intent intent=new Intent(OrganisationHome.this, Olist.class);
            startActivity(intent);
        } else if (ID == R.id.n_delete) {
            Intent intent=new Intent(OrganisationHome.this, leaderboard.class);
            startActivity(intent);
        } else if (ID == R.id.n_logout) {

            auth.signOut();
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrganisationHome.this, Start.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            finish();

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchOrganizations(query);
                recyclerView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchOrganizations(newText);
                recyclerView.setVisibility(View.GONE);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void searchOrganizations(String searchText){
        Query query = databaseReference.orderByChild("Field").equalTo(searchText);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                info.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Information users = dataSnapshot.getValue(Information.class);
                    info.add(users);
                }

                adapter2.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}