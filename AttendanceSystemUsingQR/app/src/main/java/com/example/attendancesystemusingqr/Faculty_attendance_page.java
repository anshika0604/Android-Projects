package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Faculty_attendance_page extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    String date1 = getTodaysDate();
    String email1 = "";
    String value, value2;
    private DatePickerDialog datePickerDialog;
    private Button dateButton, timeButton;
    int hour, minute;
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, mark1, view1, profile, logout;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://qr-based-attendance-7053b-default-rtdb.firebaseio.com/");
    ArrayList<String> course2 = new ArrayList<>();
    ArrayAdapter ad;
    ArrayList<String> subject = new ArrayList<>();
    ArrayAdapter ad3;
    Integer[] semester = { 1,2,3,4,5,6 };
    String[] section = { "A", "B", "C"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_attendance_page);

        // Create Database for date specified

        final Spinner courseVal = (Spinner) findViewById(R.id.course_spin);
        final Spinner subjectVal = (Spinner) findViewById(R.id.sub_spin);
        final Spinner semesterVal = (Spinner) findViewById(R.id.sem_spin);
        final Spinner sectionVal = (Spinner) findViewById(R.id.sec_spin);
        final Button generateQR = findViewById(R.id.Register);


        // Navigation Drawer

        drawerLayout = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.nav_home);
        mark1 = findViewById(R.id.nav_mark);
        view1 = findViewById(R.id.nav_view);
        profile = findViewById(R.id.nav_profile);
        logout = findViewById(R.id.nav_logout);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Faculty_attendance_page.this, Faculty_selectionpage.class, email);
            }
        });
        mark1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Faculty_attendance_page.this, View_details.class, email);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Faculty_attendance_page.this, Profile_Faculty.class, email);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                redirectActivity(Faculty_attendance_page.this, Faculty_loginpage.class, email);
            }
        });

        // Date Selector
        initDatePicker();
        dateButton = findViewById(R.id.setDate);
        dateButton.setText(getTodaysDate());

        // Time Selector
        timeButton = findViewById(R.id.setTime);

        // Get current user information

        email1 = email;

        // Course Spinner
        showCourseSpinner();
        Spinner spin1 = findViewById(R.id.course_spin);
        ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, course2);
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
        showSubjectSpinner();
        Spinner spin4 = findViewById(R.id.sub_spin);
        ad3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, subject);
        ad3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin4.setAdapter(ad3);

        // Generate QR
        generateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String course, semester, section, subject;
                course = courseVal.getSelectedItem().toString();
                semester = semesterVal.getSelectedItem().toString();
                section = sectionVal.getSelectedItem().toString();
                subject = subjectVal.getSelectedItem().toString();

                if( TextUtils.isEmpty(course) || TextUtils.isEmpty(semester) || TextUtils.isEmpty(section) || TextUtils.isEmpty(subject)) {
                    if(TextUtils.isEmpty(course)) {
                        Toast.makeText(Faculty_attendance_page.this, "Enter course", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(semester)) {
                        Toast.makeText(Faculty_attendance_page.this, "Enter semester", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(section)) {
                        Toast.makeText(Faculty_attendance_page.this, "Enter section", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(TextUtils.isEmpty(subject)) {
                        Toast.makeText(Faculty_attendance_page.this, "Enter subject", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    databaseReference.child(subject).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Storing data to firebase Realtime Database

                            //databaseReference.child(subject).child(course).child(section).child(date1).child("semester").setValue(semester);
                            Toast.makeText(Faculty_attendance_page.this, "QR Generated Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), QR_generator.class);
                            intent.putExtra("course",course);
                            intent.putExtra("subject",subject);
                            intent.putExtra("section",section);
                            intent.putExtra("email", email);
                            intent.putExtra("date",date1);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }

    // Time Dialog Box
    public void set_time(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    // Date Dialog Box
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                date1 = date;
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void set_date(View view)
    {
        datePickerDialog.show();
    }
    public void showCourseSpinner() {
        databaseReference.child("Faculty").child(email1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren()) {
                    String key = i.getKey().toString();
                    if( key.equals("courses")) {
                        value = i.getValue(String.class);
                        String str = "";
                        for(int j = 0;j<value.length();j++) {
                            char ch = value.charAt(j);
                            if(ch != '/') {
                                str += ch;
                            }
                            else {
                                course2.add(str);
                                str = "";
                            }
                        }
                        course2.add(str);
                    }
                }
                ad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void showSubjectSpinner() {
        databaseReference.child("Faculty").child(email1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren()) {
                    String key = i.getKey().toString();
                    if( key.equals("subjects")) {
                        value2 = i.getValue(String.class);
                        String str = "";
                        for(int j = 0;j<value2.length();j++) {
                            char ch = value2.charAt(j);
                            if(ch != '/') {
                                str += ch;
                            }
                            else {
                                subject.add(str);
                                str = "";
                            }
                        }
                        subject.add(str);
                    }
                }
                ad3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity, String email) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("email", email);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}