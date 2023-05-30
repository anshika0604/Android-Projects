package com.example.attendancesystemusingqr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Student_registration extends AppCompatActivity {

    String[] courses = { "BCA", "MCA", "BSc IT", "BSc CS", "MSc IT", "BTech" };
    Integer[] semester = { 1,2,3,4,5,6 };
    String[] section = { "A", "B", "C"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        // Toolbar Styling and Back button
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

        // Semester Spinner
        Spinner spin2 = findViewById(R.id.sub_spin);
        ArrayAdapter ad1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, semester);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(ad1);

        // Section Spinner
        Spinner spin3 = findViewById(R.id.sec_spin);
        ArrayAdapter ad2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, section);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(ad2);
    }
    public void attendance(View view) {
        Intent intent = new Intent(Student_registration.this, QR_Scanner.class);
        startActivity(intent);
    }
}