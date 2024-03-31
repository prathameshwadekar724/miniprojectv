package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Vlist extends AppCompatActivity {

    ProgressBar progressBar;
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

        reference.child(current).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ratings.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Ratings ratings1=dataSnapshot.getValue(Ratings.class);
                    ratings.add(ratings1);
                }
                adapter.sortAscending();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
}