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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Olist extends AppCompatActivity {

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

        progressBar = findViewById(R.id.progressbar);

        toolbar = findViewById(R.id.toolb);
        setSupportActionBar(toolbar);



        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dataList = new ArrayList<>();
        adapter = new MyAdapter1(dataList, this);
        recyclerView.setAdapter(adapter);

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
                String []field={"Name","Address","Type"};
                for(int i=0;i<field.length;i++){
                    searchUser(newText,field[i]);
                }

                recyclerView.setVisibility(View.VISIBLE);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchUser(String searchText,String field) {

        Query query=null;
        switch(field) {
            case "Name":
                query = databaseReference.orderByChild("Name");
                break;
            case "Address":
                query = databaseReference.orderByChild("Address");
                break;
            case "Type":
                query =databaseReference.orderByChild("Type");
                break;
            default :
                query = databaseReference.orderByChild("Type");
                break;


        }


        String searchTextLowercase = searchText.toLowerCase(Locale.getDefault());
        dataList.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for(DataSnapshot childSnapshot:snapshot.getChildren()){
                    Information information = childSnapshot.getValue(Information.class);
                    String fieldValueLowercase=null;
                    switch(field) {
                        case "Name":
                            fieldValueLowercase = information.getName().toLowerCase(Locale.getDefault());
                            break;
                        case "Address":
                            fieldValueLowercase = information.getAddress().toLowerCase(Locale.getDefault());
                            break;
                        case "Type":
                            fieldValueLowercase =information.getType().toLowerCase(Locale.getDefault());
                            break;
                        default:
                            fieldValueLowercase = information.getType().toLowerCase(Locale.getDefault());
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