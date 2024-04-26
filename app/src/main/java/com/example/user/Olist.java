package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Olist extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth auth;

    TextView uname,user;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<Information> dataList;
    MyAdapter1 adapter;

    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Organisation");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olist);

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

        navigationView.setCheckedItem(R.id.onList);
        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dataList = new ArrayList<>();
        adapter = new MyAdapter1(dataList, this);
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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Information dataClass = dataSnapshot.getValue(Information.class);
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Olist.this, "Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        adapter.setOnItemClickListener(new MyAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Information clickedItem = dataList.get(position);
                Intent intent=new Intent(Olist.this, Details.class);
                intent.putExtra("Name",clickedItem.getName());
                intent.putExtra("Contact",clickedItem.getContact());
                intent.putExtra("Email",clickedItem.getEmail());
                intent.putExtra("Password",clickedItem.getPassword());
                intent.putExtra("Address",clickedItem.getAddress());
                intent.putExtra("License",clickedItem.getLicense());
                intent.putExtra("Type",clickedItem.getType());
                startActivity(intent);
            }
        });


    }
    private void showDetail(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Organisation");
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
                Toast.makeText(Olist.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String[] field = {"Name", "Address", "Type"};
                if (!field[1].isEmpty()) {
                    searchUser(newText, field[1]);
                } else if (!field[0].isEmpty()) {
                    searchUser(newText, field[0]);
                } else if (!field[2].isEmpty()) {
                    searchUser(newText, field[2]);
                } else {
                    searchUser(newText, field[2]); // Default to searching by "Type"
                }




                recyclerView.setVisibility(View.VISIBLE);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchUser(String searchText,String field) {

        Query query = databaseReference.orderByChild(field);

        String searchTextLowercase = searchText.toLowerCase(Locale.getDefault());
        dataList.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for(DataSnapshot childSnapshot:snapshot.getChildren()){
                    Information information = childSnapshot.getValue(Information.class);
                    String fieldValueLowercase = null;
                    if (field=="Name"){
                        fieldValueLowercase = information.getName().toLowerCase(Locale.getDefault());
                    } else if (field=="Address") {
                        fieldValueLowercase = information.getAddress().toLowerCase(Locale.getDefault());
                    }
                    else if(field=="Type"){
                        fieldValueLowercase = information.getType().toLowerCase(Locale.getDefault());
                    }

                    if (fieldValueLowercase.contains(searchTextLowercase)) {
                        dataList.add(information);
                    }

                }
                adapter.notifyDataSetChanged();

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
            Intent intent=new Intent(Olist.this,OrganisationHome.class);
            startActivity(intent);
        }else if (ID == R.id.n_feed) {
            Intent intent = new Intent(Olist.this, Post.class);
            startActivity(intent);
        }
        else if (ID == R.id.n_no) {
            Intent intent=new Intent(Olist.this,Notification.class);
            startActivity(intent);
        } else if (ID == R.id.nList) {
            Intent intent=new Intent(Olist.this, VolunteerList.class);
            startActivity(intent);

        } else if (ID == R.id.onList) {

        } else if (ID == R.id.n_profile) {
            Intent profile = new Intent(Olist.this, OrgProfile.class);
            startActivity(profile);
            Toast.makeText(this, "Opening Profile", Toast.LENGTH_SHORT).show();


        }else if (ID == R.id.leaderboard) {
            Intent intent = new Intent(Olist.this, leaderboard.class);
            startActivity(intent);
            finish();
        }
        else if (ID == R.id.n_logout) {

            auth.signOut();
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Olist.this, Start.class);
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