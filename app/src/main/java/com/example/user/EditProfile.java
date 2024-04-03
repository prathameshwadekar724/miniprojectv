package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.N;

public class EditProfile extends AppCompatActivity {

    EditText name,contact,address,email,password,gender,city,state,postal,age,occ,field;
    Button save;
    String Name,Contact,Address,Email,Password,Gender,City,State,Postal,Age,Occ,Field;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        name=findViewById(R.id.name);
        contact=findViewById(R.id.contact);
        address=findViewById(R.id.address);
        email=findViewById(R.id.email);
        password=findViewById(R.id.passw);
        gender=findViewById(R.id.gen);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        postal=findViewById(R.id.code);
        age=findViewById(R.id.age);
        occ=findViewById(R.id.occ);
        field=findViewById(R.id.interest);
        save=findViewById(R.id.save);

        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser==null){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        else{
            showData();
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNameChanged() || isContactChanged() || isAddressChanged() || isEmailChanged() || isPasswordChanged() || isGenderChanged() || isCityChanged() || isStateChanged() || isPostalChanged() || isAgeChanged() || isOccupationChanged() || isFieldChanged()){

                        Toast.makeText(EditProfile.this, "Saved", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(EditProfile.this, "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }
    public boolean isNameChanged(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        if (!Name.equals(name.getText().toString())){
            reference.child(id).child("Name").setValue(name.getText().toString());
            Name=name.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isContactChanged(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        if (!Contact.equals(contact.getText().toString())){
            reference.child(id).child("Contact").setValue(contact.getText().toString());
            Contact=contact.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isAddressChanged(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        if (!Address.equals(address.getText().toString())){
            reference.child(id).child("Address").setValue(address.getText().toString());
            Address=address.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isEmailChanged(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        if (!Email.equals(email.getText().toString())){
            reference.child(id).child("Email").setValue(email.getText().toString());
            Email=email.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isPasswordChanged(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        if (!Password.equals(password.getText().toString())){
            reference.child(id).child("Password").setValue(password.getText().toString());
            Password=password.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isGenderChanged(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        if (!Gender.equals(gender.getText().toString())){
            reference.child(id).child("Gender").setValue(gender.getText().toString());
            Gender=gender.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isCityChanged(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        if (!City.equals(city.getText().toString())){
            reference.child(id).child("City").setValue(city.getText().toString());
            City=city.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isStateChanged(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        if (!State.equals(state.getText().toString())){
            reference.child(id).child("State").setValue(state.getText().toString());
            State=state.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isPostalChanged(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        if (!Postal.equals(postal.getText().toString())){
            reference.child(id).child("Postal").setValue(postal.getText().toString());
            Postal=postal.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isAgeChanged(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        if (!Age.equals(age.getText().toString())){
            reference.child(id).child("Dob").setValue(age.getText().toString());
            Age=age.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isOccupationChanged(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        if (!Occ.equals(occ.getText().toString())){
            reference.child(id).child("Occupation").setValue(occ.getText().toString());
            Occ=occ.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isFieldChanged(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        if (!Field.equals(field.getText().toString())){
            reference.child(id).child("Field").setValue(field.getText().toString());
            Field=field.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    public void showData(){
        Intent intent=getIntent();

        Name=intent.getStringExtra("Name");
        Contact=intent.getStringExtra("Contact");
        Address=intent.getStringExtra("Address");
        Email=intent.getStringExtra("Email");
        Password=intent.getStringExtra("Password");
        Gender=intent.getStringExtra("Gender");
        City=intent.getStringExtra("City");
        State=intent.getStringExtra("State");
        Postal=intent.getStringExtra("Postal");
        Age=intent.getStringExtra("Dob");
        Occ=intent.getStringExtra("Occupation");
        Field=intent.getStringExtra("Field");

        name.setText(Name);
        contact.setText(Contact);
        address.setText(Address);
        email.setText(Email);
        password.setText(Password);
        gender.setText(Gender);
        city.setText(City);
        state.setText(State);
        postal.setText(Postal);
        age.setText(Age);
        occ.setText(Occ);
        field.setText(Field);
    }
}