package com.example.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter6 extends RecyclerView.Adapter<MyAdapter6.MyViewHolder6> {

    private ArrayList<Msg> data;
    private Context context;

    public MyAdapter6(ArrayList<Msg> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter6.MyViewHolder6 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list2, parent, false);
        return new MyAdapter6.MyViewHolder6(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter6.MyViewHolder6 holder, int position) {
        Msg info=data.get(position);
        holder.name.setText(info.getOrgName());
        holder.email.setText(info.getEmail());
        holder.postName.setText(info.getPostName());
        holder.location.setText(info.getAddress());
        holder.type.setText(info.getType());
        holder.msg.setText(info.getMsg());

    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder6 extends RecyclerView.ViewHolder{
        TextView name,email,postName,location,type,msg;

        public MyViewHolder6(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.mName);
            email=itemView.findViewById(R.id.mEmail);
            postName=itemView.findViewById(R.id.postName);
            location=itemView.findViewById(R.id.mLocation);
            type=itemView.findViewById(R.id.mField);
            msg=itemView.findViewById(R.id.mMsg);


        }
    }
}
