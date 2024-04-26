package com.example.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder1> {
    private ArrayList<Information> data;
    private Context context;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public MyAdapter1(ArrayList<Information> data, Context context) {
        this.data = data;
        this.context = context;

    }

    @NonNull
    @Override
    public MyAdapter1.MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list,parent,false);
        return new MyAdapter1.MyViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter1.MyViewHolder1 holder,int position) {

        holder.Name.setText(data.get(position).getName());
        holder.Email.setText(data.get(position).getEmail());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.onItemClick(position);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class MyViewHolder1 extends RecyclerView.ViewHolder{

        TextView Name;
        TextView Email;
        CardView cardView;
        public MyViewHolder1(@NonNull View view){
            super(view);
            cardView=view.findViewById(R.id.cardview);
            Name=view.findViewById(R.id.rName);
            Email=view.findViewById(R.id.rEmail);
        }

    }
}
