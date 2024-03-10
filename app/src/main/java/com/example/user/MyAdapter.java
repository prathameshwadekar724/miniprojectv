package com.example.user;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Data> data;
    private Context context;

    Boolean test=false;

    public MyAdapter(ArrayList<Data> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycle_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Data current = data.get(position);
        Glide.with(context).load(current.getImageUrl()).into(holder.recycleImage);
        holder.eName.setText(data.get(position).getName());
        holder.like.setText(current.getLike() + " likes");


        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        final String userid=firebaseUser.getUid();
        final String postkey=current.getKey();

        holder.getLikeStatus(postkey,userid);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("likes");
        holder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test=true;
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (test==true){
                            if(snapshot.child(postkey).hasChild(userid)){
                                reference.child(postkey).child(userid).removeValue();
                                test=false;
                            }
                            else{
                                reference.child(postkey).child(userid).setValue(true);
                                test=false;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });

    }



    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView recycleImage;
        TextView eName,oName;

        ImageView like_btn,dislike_btn;
        TextView like,dislike;
        DatabaseReference databaseReference;
        public MyViewHolder(@NonNull View view){
            super(view);

            recycleImage=view.findViewById(R.id.rImage);
            eName=view.findViewById(R.id.rCaption);
            like_btn=view.findViewById(R.id.like);
            like=view.findViewById(R.id.rLike);


        }

        public void getLikeStatus(final String postkey, final String userid) {
            databaseReference=FirebaseDatabase.getInstance().getReference("likes");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.child(postkey).hasChild(userid)){
                        int likeCount=(int)snapshot.child(postkey).getChildrenCount();
                        like.setText(likeCount+" likes");
                        like_btn.setImageResource(R.drawable.likedb);
                    }
                    else{
                        int likeCount=(int)snapshot.child(postkey).getChildrenCount();
                        like.setText(likeCount+" likes");
                        like_btn.setImageResource(R.drawable.likeb);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}
