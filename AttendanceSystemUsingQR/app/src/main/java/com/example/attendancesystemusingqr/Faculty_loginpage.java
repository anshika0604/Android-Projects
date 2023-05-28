package com.example.attendancesystemusingqr;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Faculty_loginpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_loginpage);

    }
    public void faculty_register(View view) {
        Intent intent = new Intent(Faculty_loginpage.this, Faculty_registration.class);
        startActivity(intent);
    }
}