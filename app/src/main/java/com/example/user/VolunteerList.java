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

public class VolunteerList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth auth;

    TextView uname,user;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    ArrayList<Information> dataList;
    MyAdapter7 adapter;
    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_list2);
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

        navigationView.setCheckedItem(R.id.nList);
        progressBar=findViewById(R.id.progressbar);


        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataList=new ArrayList<>();
        adapter=new MyAdapter7(dataList,this);
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
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Information information=dataSnapshot.getValue(Information.class);
                        dataList.add(information);
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VolunteerList.this, "Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });



        adapter.setOnItemClickListener(new MyAdapter7.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Information clickedItem=dataList.get(position);
                Intent intent=new Intent(VolunteerList.this, Details2.class);
                intent.putExtra("Name",clickedItem.getName());
                intent.putExtra("Contact",clickedItem.getContact());
                intent.putExtra("Gender",clickedItem.getGender());
                intent.putExtra("Email",clickedItem.getEmail());
                intent.putExtra("Password",clickedItem.getPassword());
                intent.putExtra("Address",clickedItem.getAddress());
                intent.putExtra("City",clickedItem.getCity());
                intent.putExtra("State",clickedItem.getState());
                intent.putExtra("Postal",clickedItem.getPostal());
                intent.putExtra("Dob",clickedItem.getDob());
                intent.putExtra("Occupation",clickedItem.getOccupation());
                intent.putExtra("Field",clickedItem.getField());
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
                Toast.makeText(VolunteerList.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String []field={"Name","Address","Field","Occupation","Dob","Gender"};
                for (int i=0;i<field.length;i++){
                    searchUser(newText,field[i]);
                }

                recyclerView.setVisibility(View.VISIBLE);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void searchUser(String SearchText,String field) {

        Query query=null;
        switch(field) {
            case "Name":
                query = databaseReference.orderByChild("Name");
                break;
            case "Address":
                query = databaseReference.orderByChild("Address");
                break;
            case "Field":
                query = databaseReference.orderByChild("Field");
                break;
            case "Occupation" :
                query = databaseReference.orderByChild("Occupation");
                break;
            case "Dob":
                query =databaseReference.orderByChild("Dob");
                break;
            case "Gender":
                query = databaseReference.orderByChild("Gender");
                break;
            default :
                query = databaseReference.orderByChild("Gender");
                break;

        }


        String searchTextLowercase = SearchText.toLowerCase(Locale.getDefault());
        dataList.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for(DataSnapshot childSnapshot:snapshot.getChildren()){
                    Information information = childSnapshot.getValue(Information.class);
                    String fieldValueLowercase = null;
                    switch(field) {
                        case "Name":
                            fieldValueLowercase = information.getName().toLowerCase(Locale.getDefault());
                            break;
                        case "Address":
                            fieldValueLowercase = information.getAddress().toLowerCase(Locale.getDefault());
                            break;
                        case "Field":
                            fieldValueLowercase = information.getField().toLowerCase(Locale.getDefault());
                            break;
                        case "Occupation":
                            fieldValueLowercase = information.getOccupation().toLowerCase(Locale.getDefault());
                            break;
                        case "Dob":
                            fieldValueLowercase = information.getDob().toLowerCase(Locale.getDefault());
                            break;
                        case "Gender":
                            fieldValueLowercase = information.getGender().toLowerCase(Locale.getDefault());
                            break;
                        default:
                            fieldValueLowercase = information.getGender().toLowerCase(Locale.getDefault());
                            break;

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
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            super.onBackPressed();
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int ID = item.getItemId();

        if (ID == R.id.n_home) {
            Intent intent=new Intent(VolunteerList.this, OrganisationHome.class);
            startActivity(intent);
        } else if (ID == R.id.n_feed) {
            Intent intent = new Intent(VolunteerList.this, Post.class);
            startActivity(intent);
        } else if (ID == R.id.n_no) {
            Intent intent=new Intent(VolunteerList.this, Notification.class);
            startActivity(intent);
            Toast.makeText(this, "Open Notification", Toast.LENGTH_SHORT).show();
        } else if (ID == R.id.n_profile) {
            Intent intent=new Intent(VolunteerList.this, OrgProfile.class);
            startActivity(intent);

        } else if (ID == R.id.nList) {


        } else if (ID == R.id.onList) {
            Intent intent=new Intent(VolunteerList.this, Olist.class);
            startActivity(intent);
        } else if (ID == R.id.leaderboard) {
            Intent intent=new Intent(VolunteerList.this, leaderboard.class);
            startActivity(intent);
        } else if (ID == R.id.n_logout) {

            auth.signOut();
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(VolunteerList.this, Start.class);
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