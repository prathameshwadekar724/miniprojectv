package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class organisationuser extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    ArrayList<Data> dataList;
    MyAdapter adapter;
    Toolbar toolbar;

    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Posts").child("Upload");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisationuser);

        progressBar = findViewById(R.id.progressbar);

        toolbar = findViewById(R.id.toolb);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dataList = new ArrayList<>();
        adapter = new MyAdapter(dataList, this);
        recyclerView.setAdapter(adapter);




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
                searchPost(query);
                recyclerView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchPost(newText);
                recyclerView.setVisibility(View.GONE);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchPost(String searchText) {
        Query query = databaseReference.orderByChild("name");


        String searchTextLowercase = searchText.toLowerCase(Locale.getDefault());
        dataList.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for(DataSnapshot childSnapshot:snapshot.getChildren()){
                    Data post = childSnapshot.getValue(Data.class);
                    String postNameLowercase = post.getPostName().toLowerCase(Locale.getDefault());
                    if (postNameLowercase.contains(searchTextLowercase)) {

                        dataList.add(post);
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