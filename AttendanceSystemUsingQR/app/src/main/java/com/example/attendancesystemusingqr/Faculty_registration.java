package com.example.attendancesystemusingqr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Faculty_registration extends AppCompatActivity {

    String[] courses = { "BCA", "MCA", "BSc IT", "BSc CS", "MSc IT", "BTech" };
    String[] subject = { "C Programming ", "Operating System", "Computer Architecture", "Discrete Mathematics", "Data Structure and Algorithms", "Web Development" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_registration);

        // Toolbar Styling and Back Button
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button_foreground);

        // Course Spinner
        Spinner spin1 = findViewById(R.id.course_spin);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(ad);

        // Subject Spinner
        Spinner spin2 = findViewById(R.id.sub_spin);
        ArrayAdapter ad1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, subject);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(ad1);
    }
    public void faculty_login(View view) {
        Intent intent = new Intent(Faculty_registration.this, Faculty_attendance_page.class);
        startActivity(intent);
    }
}