package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Faculty_loginpage extends AppCompatActivity {

    Button btnLogin;
    EditText emailText, passText;
    FirebaseAuth mAuth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://qr-based-attendance-7053b-default-rtdb.firebaseio.com/");


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
        setContentView(R.layout.activity_faculty_loginpage);

        emailText = findViewById(R.id.emailFaculty);
        passText = findViewById(R.id.passFaculty);
        btnLogin = findViewById(R.id.Login);
        mAuth = FirebaseAuth.getInstance();

        // ToolBar Styling and Back Button
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button_foreground);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(emailText.getText());
                password = String.valueOf(passText.getText());

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(Faculty_loginpage.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(Faculty_loginpage.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild(email)) {
                            
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                mAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Toast.makeText(Faculty_loginpage.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(getApplicationContext(), Faculty_attendance_page.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Toast.makeText(Faculty_loginpage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

            }
        });

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