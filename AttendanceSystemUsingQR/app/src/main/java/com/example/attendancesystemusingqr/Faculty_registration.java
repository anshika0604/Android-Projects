package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Faculty_registration extends AppCompatActivity {

    Button btnRegister;
    EditText emailText, passText;
    FirebaseAuth mAuth;
    String[] courses = { "BCA", "MCA", "BSc IT", "BSc CS", "MSc IT", "BTech" };
    String[] subject = { "C Programming ", "Operating System", "Computer Architecture", "Discrete Mathematics", "Data Structure and Algorithms", "Web Development" };

    // Check if User is already Logged in
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
        setContentView(R.layout.activity_faculty_registration);

        // Authentication
        emailText = findViewById(R.id.emailFaculty);
        passText = findViewById(R.id.passFaculty);
        btnRegister = findViewById(R.id.Register);
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = emailText.getText().toString();
                password = passText.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(Faculty_registration.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(Faculty_registration.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                regis(email, password);

            }
        });

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

    private void regis(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Faculty_registration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(Faculty_registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Faculty_registration.this, Faculty_attendance_page.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(Faculty_registration.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void faculty_login(View view) {
        Intent intent = new Intent(Faculty_registration.this, Faculty_attendance_page.class);
        startActivity(intent);
    }


}