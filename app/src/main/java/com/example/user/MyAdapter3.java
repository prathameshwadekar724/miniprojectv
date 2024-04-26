package com.example.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder3> {

    private ArrayList<Message> data;
    private  Context context;



    public MyAdapter3(ArrayList<Message> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter3.MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list3, parent, false);
        return new MyAdapter3.MyViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter3.MyViewHolder3 holder, int position) {

        Message information=data.get(position);
        holder.name.setText(information.getName());
        holder.email.setText(information.getEmail());
        holder.postName.setText(information.getPostName());
        holder.location.setText(information.getAddress());
        holder.type.setText(information.getType());
        holder.msg.setText(information.getMessage());


        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        String userID=firebaseUser.getUid();


        holder.Approve(information.getName(),userID,information.getPostKey(),information.getOrgName(),information.getEmail(),information.getAddress(),information.getUserId(),information.getPostName(),position);




    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder{
        TextView name,email,postName,location,type,msg;

        CardView cardView;

        Button button;
        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview);
            name=itemView.findViewById(R.id.pName);
            email=itemView.findViewById(R.id.pEmail);
            postName=itemView.findViewById(R.id.postName);
            location=itemView.findViewById(R.id.pLocation);
            type=itemView.findViewById(R.id.pType);
            msg=itemView.findViewById(R.id.pMsg);
            button=itemView.findViewById(R.id.approve);

        }

        public void Approve(String name, String userid,String key,String OrgName,String UserEmail,String userLocation,String vUser,String postName,int pos) {
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Organisation").child(userid);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                String email=snapshot.child("Email").getValue(String.class);
                                String location=snapshot.child("Address").getValue(String.class);
                                String type=snapshot.child("Type").getValue(String.class);
                                String license=snapshot.child("License").getValue(String.class);
                                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Posts").child("Approved");
                                Msg msg=new Msg(name,UserEmail,OrgName,"Your application is approved",key,location,email,type,license,userLocation,vUser,postName);
                                databaseReference.child(key).child(vUser).setValue(msg).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        update(key,vUser,pos);
                                        Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });


        }
        private void update(String key, String vUser, int position) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Applied");
            databaseReference.child(key).child(vUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Message msg1 = snapshot.getValue(Message.class);
                        if (msg1 != null) {
                            snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    data.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, data.size());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(context, "Failed to remove notification", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

}
