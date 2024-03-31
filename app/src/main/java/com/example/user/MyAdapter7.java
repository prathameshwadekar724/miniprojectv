package com.example.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MyAdapter7 extends RecyclerView.Adapter<MyAdapter7.MyViewHolder7> {

    private ArrayList<Information> data;
    private Context context;

    private ArrayList<Ratings> ratingsList;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private MyAdapter7.OnItemClickListener mListener;
    public void setOnItemClickListener(MyAdapter7.OnItemClickListener listener) {
        mListener = listener;
    }

    public MyAdapter7(ArrayList<Information> data,ArrayList<Ratings> ratingsList ,Context context) {
        this.data = data;
        this.ratingsList=ratingsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter7.MyViewHolder7 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list5,parent,false);
        return new MyAdapter7.MyViewHolder7(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter7.MyViewHolder7 holder, int position) {
        Information information=data.get(position);
        holder.Name.setText(information.getName());
        holder.Email.setText(information.getEmail());

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String current=firebaseUser.getUid();

        String user=information.getName();
        holder.getRatings(current,user);


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

    public void sortAscending() {
        Collections.sort(data, new Comparator<Information>() {
            @Override
            public int compare(Information o1, Information o2) {
                float rating1 = getRatingForUser(o1.getName());
                float rating2 = getRatingForUser(o2.getName());
                return Float.compare(rating1, rating2);
            }
        });
        notifyDataSetChanged();
    }

    // Method to get rating for a specific user
    private float getRatingForUser(String userName) {
        for (Ratings rating : ratingsList) {
            if (rating.getName().equals(userName)) {
                return rating.getRating();
            }
        }
        return 0; // Default rating if user's rating is not found
    }

    public class MyViewHolder7 extends RecyclerView.ViewHolder{

        TextView Name;
        TextView Email;

        RatingBar ratingBar;

        Button button;
        CardView cardView;

        public MyViewHolder7(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.cardview);
            Name=itemView.findViewById(R.id.pName);
            Email=itemView.findViewById(R.id.pEmail);
            ratingBar=itemView.findViewById(R.id.rating);
            button=itemView.findViewById(R.id.submit);


        }

        public void getRatings(String current,String name) {
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Posts").child("Ratings");

            reference.child(current).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Ratings ratings = dataSnapshot.getValue(Ratings.class);
                            String Name=ratings.getName();
                            if (ratings != null) {
                                float rate=ratings.getRating();
                                if (name.equals(Name)){
                                    ratingBar.setRating(rate);
                                }

                            }
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