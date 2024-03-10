package com.example.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {


    private ArrayList<Information> data;

    public MyAdapter2(ArrayList<Information> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyAdapter2.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new MyAdapter2.MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.MyViewHolder2 holder, int position) {
        Information information=data.get(position);
        holder.name.setText(information.getName());
        holder.username.setText(information.getEmail());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView name,username;
        public MyViewHolder2(View view) {
            super(view);
            name=view.findViewById(R.id.rName);
            username=view.findViewById(R.id.rEmail);
        }
    }
}
