package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Apply extends AppCompatActivity {
    private TextView detailTextView, detail1TextView, detail2TextView, detail3TextView, detail4TextView;
    private Button applyButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        detailTextView=findViewById(R.id.detail);
        detail1TextView=findViewById(R.id.detail1);
        detail2TextView=findViewById(R.id.detail2);
        detail3TextView=findViewById(R.id.detail3);
        detail4TextView=findViewById(R.id.detail4);
        applyButton=findViewById(R.id.apply);

        String n=detail1TextView.getText().toString();

        Intent intent=getIntent();
        intent.putExtra("detail",n);

        detailTextView.setText(n);



        //detail1TextView.setText("Event Location: " + selectPost.getEventLocation());
        //detail2TextView.setText("No. of Volunteers Required: " + selectPost.getEventVolunteers());
        //detail3TextView.setText("Event Category: " + selectPost.getEventCategory());
        //detail4TextView.setText("Event Manager: " + selectPost.getEventManger());

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}