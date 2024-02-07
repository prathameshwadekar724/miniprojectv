package com.example.user;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Post extends AppCompatActivity {
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://user-4d03f-default-rtdb.firebaseio.com/");
    ActivityResultLauncher<String> launcher;
    FirebaseStorage firebaseStorage;
    private ImageView imageView;
    private Button submit;
    private  Button upload;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        firebaseStorage=FirebaseStorage.getInstance();
        imageView=findViewById(R.id.upload);

        database=FirebaseDatabase.getInstance();

        submit =findViewById(R.id.submit_post_button);
        upload=findViewById(R.id.bup);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirebase();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
        databaseReference.child("Images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image=snapshot.getValue(String.class);
                Picasso.get().load(image).into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                imageView.setImageURI(uri);


                final StorageReference reference=firebaseStorage.getReference().child("Image");

                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                databaseReference.child("Image").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Post.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });


            }
        });

    }

    private void saveToFirebase() {
        String eventName=((EditText)findViewById(R.id.event_name_input)).getText().toString();
        String eventLocation=((EditText)findViewById(R.id.event_location_input)).getText().toString();
        String eventVolunteers=((EditText)findViewById(R.id.volunteers_required_input)).getText().toString();
        String eventCategory=((EditText)findViewById(R.id.event_category_input)).getText().toString();
        String eventManger=((EditText)findViewById(R.id.manager_name_input)).getText().toString();
        String eventContact=((EditText)findViewById(R.id.contact_details_input)).getText().toString();

        String postId=FirebaseDatabase.getInstance().getReference().child("posts").push().getKey();

        CreatePost createPost=new CreatePost(postId, eventName, eventLocation,eventVolunteers,eventCategory,eventManger,eventContact);

        databaseReference.child("posts").child(postId).setValue(createPost).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Post.this, "Post saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Post.this, "Post not created", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}