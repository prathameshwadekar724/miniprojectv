package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Start extends AppCompatActivity {
    TextView select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);





        Spinner spinner = findViewById(R.id.spinner);
        String[] options = {"Select Your Account Type","Admin","Volunteers","Organisation"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = options[position];
                if (selectedOption!=options[0]){
                    if (selectedOption.equals(options[1])){
                        Intent intent=new Intent(Start.this, Admin.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (selectedOption.equals(options[2])) {
                        Intent intent=new Intent(Start.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (selectedOption.equals(options[3])) {
                        Intent intent=new Intent(Start.this,OrganisationLogin.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(Start.this, "Select Your Account Type", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Start.this, "Error", Toast.LENGTH_SHORT).show();

            }
        } );
    }


}
