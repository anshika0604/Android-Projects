package com.example.dbdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class displayContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Rname");
        String phone = intent.getStringExtra("Rphone");

        TextView textView1 = findViewById(R.id.displayName);
        textView1.setText(name);
        TextView textView2 = findViewById(R.id.displayPhone);
        textView2.setText(phone);
    }
}