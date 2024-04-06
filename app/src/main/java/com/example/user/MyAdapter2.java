package com.example.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {


    private ArrayList<Information> data;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private MyAdapter2.OnItemClickListener mListener;
    public void setOnItemClickListener(MyAdapter2.OnItemClickListener listener) {
        mListener = listener;
    }

    public MyAdapter2(ArrayList<Information> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyAdapter2.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list1, parent, false);
        return new MyAdapter2.MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.MyViewHolder2 holder, int position) {
        Information information=data.get(position);
        holder.name.setText(information.getName());
        holder.username.setText(information.getEmail());

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

    public class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView name,username;

        CardView cardView;
        Button button;

        public MyViewHolder2(View view) {
            super(view);
            name=view.findViewById(R.id.pName);
            username=view.findViewById(R.id.pEmail);
            cardView=view.findViewById(R.id.cardview);

        }

    }

}
