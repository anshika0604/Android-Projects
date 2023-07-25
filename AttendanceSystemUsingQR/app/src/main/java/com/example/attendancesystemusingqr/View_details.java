package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class View_details extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, mark1, view1, profile, logout;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://qr-based-attendance-7053b-default-rtdb.firebaseio.com/");
    String[] courses = { "BCA", "MCA", "BSc IT", "BSc CS", "MSc IT", "BTech" };
    String[] section = { "A", "B", "C"};
    String[] subject = { "C Programming ", "Operating System", "Computer Architecture", "Discrete Mathematics", "Data Structure and Algorithms", "Web Development" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        // Navigation Drawer

        drawerLayout = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.nav_home);
        mark1 = findViewById(R.id.nav_mark);
        view1 = findViewById(R.id.nav_view);
        profile = findViewById(R.id.nav_profile);
        logout = findViewById(R.id.nav_logout);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(View_details.this, Faculty_selectionpage.class, email);
            }
        });
        mark1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(View_details.this, Faculty_attendance_page.class, email);
            }
        });
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(View_details.this, Profile_Faculty.class, email);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                redirectActivity(View_details.this, Faculty_loginpage.class, email);
            }
        });

        // Details Functionalities

        final Spinner courseVal = (Spinner) findViewById(R.id.course_spin);
        final Spinner subjectVal = (Spinner) findViewById(R.id.sub_spin);
        final Spinner sectionVal = (Spinner) findViewById(R.id.sec_spin);
        final Button viewRecord = findViewById(R.id.Register);

        // Course Spinner
        Spinner spin1 = findViewById(R.id.course_spin);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(ad);


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



        viewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String course, section, subject;
                course = courseVal.getSelectedItem().toString();
                section = sectionVal.getSelectedItem().toString();
                subject = subjectVal.getSelectedItem().toString();

                if( TextUtils.isEmpty(course) ||  TextUtils.isEmpty(section) || TextUtils.isEmpty(subject)) {
                    if(TextUtils.isEmpty(course)) {
                        Toast.makeText(View_details.this, "Enter course", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(section)) {
                        Toast.makeText(View_details.this, "Enter section", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(TextUtils.isEmpty(subject)) {
                        Toast.makeText(View_details.this, "Enter subject", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    databaseReference.child(subject).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Fetching data to firebase Realtime Database

                            Toast.makeText(View_details.this, "Viewing Attendance", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Attendance_view_faculty.class);
                            intent.putExtra("course",course);
                            intent.putExtra("subject",subject);
                            intent.putExtra("section",section);
                            intent.putExtra("email", email);
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