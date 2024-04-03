package com.example.user;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Data> data;
    private Context context;

    Boolean test=false;
    Boolean test1=false;

    public MyAdapter(ArrayList<Data> data,Context context) {
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
        holder.pName.setText(data.get(position).getPostName());
        holder.desc.setText(current.getDescription());



        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        final String userid=firebaseUser.getUid();
        final String postkey=current.getKey();
        final String postName= current.getPostName();

        holder.getLikeStatus(postkey,userid);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts").child("Likes");
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


        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.sendApplyMessageToOrganisation(current.getName(),postkey,userid,postName);
                Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();

            }

        });


    }



    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView recycleImage;
        TextView eName, pName,desc;

        ImageView like_btn;
        TextView like;

        CardView cardView;
        Button submit;
        DatabaseReference databaseReference;

        public MyViewHolder(@NonNull View view) {
            super(view);
            cardView=view.findViewById(R.id.cardview);
            recycleImage = view.findViewById(R.id.rImage);
            eName = view.findViewById(R.id.rCaption);
            pName=view.findViewById(R.id.sName);
            desc=view.findViewById(R.id.rDesc);
            like_btn = view.findViewById(R.id.like);
            like = view.findViewById(R.id.rLike);
            submit = view.findViewById(R.id.bSubmit);


        }



        public void getLikeStatus(final String postkey, final String userid) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Likes");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.child(postkey).hasChild(userid)) {
                        int likeCount = (int) snapshot.child(postkey).getChildrenCount();
                        like.setText(likeCount + " likes");
                        like_btn.setImageResource(R.drawable.likedb);


                    } else {
                        int likeCount = (int) snapshot.child(postkey).getChildrenCount();
                        like.setText(likeCount + " likes");
                        like_btn.setImageResource(R.drawable.likeb);


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        public void sendApplyMessageToOrganisation(String oName, String postKey, String userID,String postName) {
            DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

            usersReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String vName=snapshot.child("Name").getValue(String.class);
                        String address=snapshot.child("City").getValue(String.class);
                        String email=snapshot.child("Email").getValue(String.class);
                        String type=snapshot.child("Field").getValue(String.class);
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts").child("Applied");
                        Message message=new Message(vName,oName,"I want to apply",postKey,address,email,type,userID,postName);
                        reference.child(postKey).child(userID).setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
