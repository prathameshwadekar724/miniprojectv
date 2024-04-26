package com.example.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.StringValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyAdapter8 extends RecyclerView.Adapter<MyAdapter8.MyViewHolder8> {

    private ArrayList<Average> data;
    private Context context;

    public MyAdapter8(ArrayList<Average> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter8.MyViewHolder8 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.leaderboard1,parent,false);
        return new MyAdapter8.MyViewHolder8(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter8.MyViewHolder8 holder, int position) {
        Average information=data.get(position);
        holder.name.setText(information.getName());

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String current=firebaseUser.getUid();

        String user=information.getName();
        holder.getRatings(user);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder8 extends RecyclerView.ViewHolder{

        TextView name,rating;
        public MyViewHolder8(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.vName);
            rating=itemView.findViewById(R.id.vRate);

        }

        public void getRatings( String name1) {
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Average").child(name1);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        if (snapshot.child("average").getValue() != null) {
                            double rate = snapshot.child("average").getValue(Double.class);

                            rating.setText(String.valueOf(rate));
                        } else {

                            rating.setText("0");
                        }
                    }else {
                        rating.setText("0");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}
