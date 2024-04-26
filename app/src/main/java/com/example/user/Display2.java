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

public class Display2 extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    ArrayList<Msg> dataList;
    MyAdapter4 adapter;
    final private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Posts").child("Approved");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display2);

        progressBar=findViewById(R.id.progressbar);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        dataList=new ArrayList<>();
        adapter=new MyAdapter4(dataList,this);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);
        Intent intent=getIntent();


        String name=intent.getStringExtra("OrgName");



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                            Msg info=userSnapshot.getValue(Msg.class);
                            if (info.getOrgName().equals(name)){
                                dataList.add(info);

                            }

                        }


                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);


                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Display2.this, "No result found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}