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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Faculty_registration extends AppCompatActivity {


    FirebaseAuth mAuth;

    // Create object to DatabaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://qr-based-attendance-7053b-default-rtdb.firebaseio.com/");
    String[] courses = { "BCA", "MCA", "BSc IT", "BSc CS", "MSc IT", "BTech" };
    String[] subject = { "C Programming ", "Operating System", "Computer Architecture", "Discrete Mathematics", "Data Structure and Algorithms", "Web Development" };

    // Check if User is already Logged in
    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), Faculty_selectionpage.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_registration);

        // Authentication
        final EditText emailText = findViewById(R.id.emailFaculty);
        final EditText passText = findViewById(R.id.passFaculty);
        final EditText nameText = findViewById(R.id.nameFaculty);
        final Spinner courseVal = (Spinner) findViewById(R.id.course_spin);
        final Spinner subjectVal = (Spinner) findViewById(R.id.sub_spin);
        final Button btnRegister = findViewById(R.id.Register);
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, name, course, subject;
                email = emailText.getText().toString();
                email = email.replace(".",",");
                password = passText.getText().toString();
                name = nameText.getText().toString();
                course = courseVal.getSelectedItem().toString();
                subject = subjectVal.getSelectedItem().toString();

                if( TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {
                    if(TextUtils.isEmpty(email)) {
                        Toast.makeText(Faculty_registration.this, "Enter email", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(password)) {
                        Toast.makeText(Faculty_registration.this, "Enter password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(name)) {
                        Toast.makeText(Faculty_registration.this, "Enter Name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    String finalEmail = email;
                    databaseReference.child("Faculty").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(finalEmail)) {
                                Toast.makeText(Faculty_registration.this, "Email Already Registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // Storing data to firebase Realtime Database

                                HashMap<String, String> m =new HashMap<String, String>();
                                m.put("name", name);
                                m.put("password", password);
                                m.put("course", course);
                                m.put("subject", subject);
                                databaseReference.child("Faculty").child(finalEmail).setValue(m);
                                Toast.makeText(Faculty_registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Faculty_selectionpage.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                //regis(email, password);



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

    public void faculty_login(View view) {
        Intent intent = new Intent(Faculty_registration.this, Faculty_selectionpage.class);
        startActivity(intent);
    }


}