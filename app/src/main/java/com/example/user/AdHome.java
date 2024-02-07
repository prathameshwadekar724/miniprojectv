package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdHome extends AppCompatActivity {
    private DatabaseReference reference;

    private ListView listView;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_home);

        listView = findViewById(R.id.listview);
        progressBar=findViewById(R.id.progressbar);


        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.user_item, list);
        listView.setAdapter(adapter);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        progressBar.setVisibility(View.VISIBLE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Information post=dataSnapshot.getValue(Information.class);
                    String txt="Volunteer Name:\t"+post.getName()+"\n"+"Contact:\t"+post.getContact()+"\n"+"Address:\t"+post.getAddress()+"\n"+"Username:\t"+post.getEmail()+"\n"+"Password:\t"+post.getPassword()+"\n"+"City:\t"+post.getCity()+"\n"+"State:\t"+post.getState()+"\n"+"Postal Code:\t"+post.getPostal()+"\n"+"Date of Birth:\t"+post.getDob()+"\n"+"Occupation:\t"+post.getOccupation()+"\n"+"Area of interest:\t"+post.getField();
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