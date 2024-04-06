package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class Vlist extends AppCompatActivity {

    ProgressBar progressBar;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Information> dataList;
    ArrayList<Ratings> ratings;
    MyAdapter7 adapter;
    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
    final private DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts").child("Ratings");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlist);

        progressBar=findViewById(R.id.progressbar);
        toolbar = findViewById(R.id.toolb);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        dataList=new ArrayList<>();
        ratings=new ArrayList<>();
        adapter=new MyAdapter7(dataList,ratings,this);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String current=firebaseUser.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Information information=dataSnapshot.getValue(Information.class);
                    dataList.add(information);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Vlist.this, "Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });



        adapter.setOnItemClickListener(new MyAdapter7.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Information clickedItem=dataList.get(position);
                Intent intent=new Intent(Vlist.this, Details2.class);
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
}