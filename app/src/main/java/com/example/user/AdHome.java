package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdHome extends AppCompatActivity {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    ArrayList<Information> dataList;
    MyAdapter1 adapter;
    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_home);

        progressBar=findViewById(R.id.progressbar);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        dataList=new ArrayList<>();
        adapter=new MyAdapter1(dataList,this);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Information dataClass=dataSnapshot.getValue(Information.class);
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdHome.this, "Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        adapter.setOnItemClickListener(new MyAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Information clickedItem = dataList.get(position);
                Intent intent=new Intent(AdHome.this, Details2.class);
                intent.putExtra("Name",clickedItem.getName());
                intent.putExtra("Contact",clickedItem.getContact());
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
}