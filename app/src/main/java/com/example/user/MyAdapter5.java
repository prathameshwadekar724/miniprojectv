package com.example.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MyAdapter5 extends RecyclerView.Adapter<MyAdapter5.MyViewHolder5> {

    private ArrayList<Data> data;
    private Context context;

    public MyAdapter5(ArrayList<Data> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter5.MyViewHolder5 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycle_post,parent,false);
        return new MyAdapter5.MyViewHolder5(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter5.MyViewHolder5 holder, int position) {
        Data current = data.get(position);
        Glide.with(context).load(current.getImageUrl()).into(holder.recycleImage);
        holder.eName.setText(data.get(position).getName());

        String key=current.getKey();
        String name=current.getName();

        holder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Display2.class);
                intent.putExtra("key",key);
                intent.putExtra("OrgName",name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder5 extends RecyclerView.ViewHolder{

        ImageView recycleImage;
        TextView eName, oName;
        Button open;

        public MyViewHolder5(@NonNull View itemView) {
            super(itemView);

            recycleImage = itemView.findViewById(R.id.rImage);
            eName = itemView.findViewById(R.id.rCaption);
            open = itemView.findViewById(R.id.bSubmit);
        }
    }
}
