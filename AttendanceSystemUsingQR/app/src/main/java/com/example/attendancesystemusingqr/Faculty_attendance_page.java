package com.example.attendancesystemusingqr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class Faculty_attendance_page extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    String[] courses = { "BCA", "MCA", "BSc IT", "BSc CS", "MSc IT", "BTech" };
    Integer[] semester = { 1,2,3,4,5,6 };
    String[] section = { "A", "B", "C"};
    String[] subject = { "C Programming ", "Operating System", "Computer Architecture", "Discrete Mathematics", "Data Structure and Algorithms", "Web Development" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_attendance_page);

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

        // Semester Spinner
        Spinner spin2 = findViewById(R.id.sem_spin);
        ArrayAdapter ad1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, semester);
        ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(ad1);

        // Section Spinner
        Spinner spin3 = findViewById(R.id.sec_spin);
        ArrayAdapter ad2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, section);
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(ad2);

        // Subject Spinner
        Spinner spin4 = findViewById(R.id.sub_spin);
        ArrayAdapter ad3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, subject);
        ad3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin4.setAdapter(ad3);
    }
    public void generate_qr(View view) {
        Intent intent = new Intent(Faculty_attendance_page.this, QR_generator.class);
        startActivity(intent);
    }

    // Date Dialog Box
    public void set_date(View view) {
        showDateDialog();
    }
    public void showDateDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    // Time Dialog Box
    public void set_time(View view) {
        showTimeDialog();
    }
    public void showTimeDialog() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Faculty_attendance_page.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay,int minute) {
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}