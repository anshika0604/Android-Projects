package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Student_registration extends AppCompatActivity {

    Button btnRegister;
    EditText emailText, passText;
    FirebaseAuth mAuth;
    String[] courses = { "BCA", "MCA", "BSc IT", "BSc CS", "MSc IT", "BTech" };
    Integer[] semester = { 1,2,3,4,5,6 };
    String[] section = { "A", "B", "C"};

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), Faculty_attendance_page.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        // Authentication
        emailText = findViewById(R.id.emailStudent);
        passText = findViewById(R.id.passStudent);
        btnRegister = findViewById(R.id.Register);
        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = emailText.getText().toString();
                password = passText.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(Student_registration.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(Student_registration.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                regis(email, password);

            }
        });

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

    private void regis(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Student_registration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(Student_registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Student_registration.this, QR_Scanner.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(Student_registration.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}