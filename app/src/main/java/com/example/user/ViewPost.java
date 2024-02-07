package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewPost extends AppCompatActivity {
    private DatabaseReference reference;

    private ListView listView;
    private ProgressBar progressBar;
    private String txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        listView = findViewById(R.id.listview);
        progressBar=findViewById(R.id.progressbar);



        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.user_item, list);
        listView.setAdapter(adapter);
        reference = FirebaseDatabase.getInstance().getReference().child("posts");
        progressBar.setVisibility(View.VISIBLE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CreatePost post=dataSnapshot.getValue(CreatePost.class);
                    txt="Event Name:\t"+post.getEventName()+"\n"+"Event Location:\t"+post.getEventLocation()+"\n"+"No. of volunteers required:\t"+post.getEventVolunteers()+"\n"+"Event category:\t"+post.getEventCategory()+"\n"+"Event manager:\t"+post.getEventManger()+"\n"+"Contact:\t"+post.getEventContact();
                    list.add(txt);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        // CreatePost selectedPost=list.get(position);
                        // DataHolder.setSelectedPost(selectedPost);
                        Intent intent = new Intent(ViewPost.this, Apply.class);
                        intent.putExtra("Event Name",txt);
                        startActivity(intent);
                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });



    }

}