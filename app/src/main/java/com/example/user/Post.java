package com.example.user;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Post extends AppCompatActivity {
    private Button uploadButton;
    private ImageView uploadImage;
    EditText name,description;
    ProgressBar progressBar;
    private Uri imageUri;
    final private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Posts").child("Upload");
    final private StorageReference storageReference= FirebaseStorage.getInstance().getReference();

    private DatabaseReference organizationRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        uploadButton=findViewById(R.id.upb);
        name=findViewById(R.id.up);
        description=findViewById(R.id.desc);
        uploadImage=findViewById(R.id.imageView);
        progressBar=findViewById(R.id.progressbar);

        progressBar.setVisibility(View.GONE);



        organizationRef=FirebaseDatabase.getInstance().getReference("Organisation");



        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == Activity.RESULT_OK){
                    Intent data=o.getData();
                    imageUri=data.getData();
                    uploadImage.setImageURI(imageUri);
                }else{
                    Toast.makeText(Post.this, "No image selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image=new Intent();
                image.setAction(Intent.ACTION_GET_CONTENT);
                image.setType("image/*");
                activityResultLauncher.launch(image);
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri!=null){
                    upload(imageUri);
                }else{
                    Toast.makeText(Post.this, "Select image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void upload(Uri uri){
        String Name = name.getText().toString();
        String desc=description.getText().toString();
        final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

        // Fetch organization name
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            organizationRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String organizationName = dataSnapshot.child("Name").getValue(String.class);

                        // Upload image with organization name
                        imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String key = databaseReference.push().getKey();
                                        Data data = new Data(uri.toString(), Name, key,  organizationName,desc);
                                        databaseReference.child(key).setValue(data);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(Post.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Post.this, OrganisationHome.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Post.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle any errors
                }
            });
        }
    }

    private String getFileExtension(Uri fileuri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(fileuri));
    }
}