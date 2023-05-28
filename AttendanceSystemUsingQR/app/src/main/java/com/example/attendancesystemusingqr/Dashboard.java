package com.example.attendancesystemusingqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }
    public void faculty_act(View view) {
        Intent intent = new Intent(Dashboard.this, Faculty_loginpage.class);
        startActivity(intent);
    }
    public void student_act(View view) {
        Intent intent = new Intent(Dashboard.this, LoginPage.class);
        startActivity(intent);
    }
}