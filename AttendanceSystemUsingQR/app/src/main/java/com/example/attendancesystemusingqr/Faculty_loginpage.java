package com.example.attendancesystemusingqr;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Faculty_loginpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_loginpage);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button_foreground);

    }
    public void faculty_register(View view) {
        Intent intent = new Intent(Faculty_loginpage.this, Faculty_registration.class);
        startActivity(intent);
    }
    public void login_faculty(View view) {
        Intent intent = new Intent(Faculty_loginpage.this, Faculty_attendance_page.class);
        startActivity(intent);
    }
}