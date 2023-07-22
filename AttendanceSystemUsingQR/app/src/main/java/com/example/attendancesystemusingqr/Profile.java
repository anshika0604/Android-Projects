package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    TextView profileName, profileEmail, profileRoll, profilePassword;
    TextView profileCourse, profileSection, titleName, titleEmail;
    String name, email, course, section, roll, password;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://qr-based-attendance-7053b-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileRoll = findViewById(R.id.profileRoll);
        profileCourse = findViewById(R.id.profileCourse);
        profilePassword = findViewById(R.id.profilePassword);
        profileSection = findViewById(R.id.profileSection);
        titleName = findViewById(R.id.titleName);
        titleEmail = findViewById(R.id.titleEmail);
        showUserData();
    }

    public void showUserData() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        databaseReference.child("Student").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren()) {
                    String key = i.getKey().toString();
                    Log.d("hello key - ", key);
                    if (key.equals("name")) {
                        name = i.getValue(String.class);
                        Log.d("hello name - ", name);
                    } else if (key.equals("course")) {
                        course = i.getValue(String.class);
                        Log.d("hello course - ", course);
                    } else if (key.equals("roll")) {
                        roll = i.getValue(String.class);
                        Log.d("hello roll - ", roll);
                    } else if (key.equals("password")) {
                        password = i.getValue(String.class);
                        Log.d("hello pass - ", password);
                    } else if (key.equals("section")) {
                        section = i.getValue(String.class);
                        Log.d("hello section - ", section);
                    }
                }
                titleName.setText(name);
                titleEmail.setText(email);
                profileName.setText(name);
                profileEmail.setText(email);
                profileCourse.setText(course);
                profileRoll.setText(roll);
                profilePassword.setText(password);
                profileSection.setText(section);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("values", name+course+email+section+roll+section);
    }
}