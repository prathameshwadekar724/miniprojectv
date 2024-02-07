package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class organisationuser extends AppCompatActivity {
    private ListView listView1;
    private DatabaseReference reference;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisationuser);

        listView1=findViewById(R.id.listview1);
        progressBar=findViewById(R.id.progressbar);


        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.user_item, list);
        listView1.setAdapter(adapter);
        reference = FirebaseDatabase.getInstance().getReference().child("Organisation");
        progressBar.setVisibility(View.VISIBLE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Information post=dataSnapshot.getValue(Information.class);
                    String txt="Organisation Name:\t"+post.getName()+"\n"+"License No.:\t"+post.getLicense()+"\n"+"Address:\t"+post.getAddress()+"\n"+"Type of organisation:\t"+post.getType()+"\n"+"Username:\t"+post.getEmail()+"\n"+"Password:\t"+post.getPassword();
                    list.add(txt);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }


}