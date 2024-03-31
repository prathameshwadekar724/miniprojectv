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

public class MyAdapter4 extends RecyclerView.Adapter<MyAdapter4.MyViewHolder4> {

    private ArrayList<Msg> data;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private MyAdapter4.OnItemClickListener mListener;
    public void setOnItemClickListener(MyAdapter4.OnItemClickListener listener) {
        mListener = listener;
    }


    public MyAdapter4(ArrayList<Msg> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter4.MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list4,parent,false);
        return new MyAdapter4.MyViewHolder4(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter4.MyViewHolder4 holder, int position) {
        Msg msg=data.get(position);
        holder.Name.setText(msg.getName());
        holder.Email.setText(msg.getUserEmail());

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String user=firebaseUser.getUid();
        holder.getRating(user,msg.getUserKey(),msg.getName());

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

    public class MyViewHolder4 extends RecyclerView.ViewHolder {

        TextView Name;
        TextView Email;

        RatingBar ratingBar;

        Button button;
        CardView cardView;

        public MyViewHolder4(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview);
            Name=itemView.findViewById(R.id.pName);
            Email=itemView.findViewById(R.id.pEmail);
            ratingBar=itemView.findViewById(R.id.rating);
            button=itemView.findViewById(R.id.submit);



        }

        public void getRating(String user,String userKey,String name) {
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts").child("Ratings");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float rating=(ratingBar.getRating());
                    Ratings ratings=new Ratings(user,userKey,name,rating);
                    reference.child(user).child(userKey).setValue(ratings).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, rating+ "  Stars", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                }
            });
            UpdateRatings(user,userKey);


        }

        private void UpdateRatings(String user,String key) {
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Posts").child("Ratings");
            databaseReference.child(user).child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        Ratings ratings=snapshot.getValue(Ratings.class);
                        if (ratings!=null){
                            float update=ratings.getRating();
                            ratingBar.setRating(update);
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
