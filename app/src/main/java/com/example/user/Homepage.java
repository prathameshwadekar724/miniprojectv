package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
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

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth auth;

    TextView uname,user;
    ProgressBar progressBar;




    RecyclerView recyclerView1;
    ArrayList<Data> dataList;
    MyAdapter adapter;


    final private DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference("Posts").child("Upload");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);




        drawerLayout = findViewById(R.id.lay_drw);
        navigationView = findViewById(R.id.view_nav);
        View headerView = navigationView.getHeaderView(0);

        uname = headerView.findViewById(R.id.fname);
        user = headerView.findViewById(R.id.fuser);
        progressBar=findViewById(R.id.progressbar);


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



        toolbar = findViewById(R.id.toolb);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.n_home);



        recyclerView1=findViewById(R.id.recyclerview);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        dataList=new ArrayList<>();
        adapter=new MyAdapter(dataList,this);
        recyclerView1.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Data dataClass=dataSnapshot.getValue(Data.class);
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Homepage.this, "Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });




    }



    private void showAlertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(Homepage.this);
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
                Toast.makeText(Homepage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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

        } else if (ID == R.id.n_no) {
            Intent intent=new Intent(Homepage.this,VNotification.class);
            startActivity(intent);
        } else if (ID == R.id.nList) {
            Intent intent=new Intent(Homepage.this, Vlist.class);
            startActivity(intent);
        } else if (ID == R.id.onList) {
            Intent intent=new Intent(Homepage.this,organisationuser.class);
            startActivity(intent);
        } else if (ID == R.id.n_profile) {
            Intent profile = new Intent(Homepage.this, Profile.class);
            startActivity(profile);
            Toast.makeText(this, "Opening Profile", Toast.LENGTH_SHORT).show();


        } else if (ID == R.id.n_logout) {

            auth.signOut();
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Homepage.this, Start.class);
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchOrganizations(newText);
                recyclerView1.setVisibility(View.VISIBLE);
                return false;

            }


        });

        return super.onCreateOptionsMenu(menu);
    }


    private void searchOrganizations(String searchText) {
        Query query = databaseReference1.orderByChild("postName");


        String searchTextLowercase = searchText.toLowerCase(Locale.getDefault());
        dataList.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList.clear();

                for(DataSnapshot childSnapshot:dataSnapshot.getChildren()){
                    Data post = childSnapshot.getValue(Data.class);
                    String postNameLowercase = post.getPostName().toLowerCase(Locale.getDefault());
                    if (postNameLowercase.contains(searchTextLowercase)) {

                        dataList.add(post);
                    }
                }
                adapter.notifyDataSetChanged();


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }


}