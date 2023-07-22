package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    Button editButton;
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
        editButton = findViewById(R.id.editButton);
        showUserData();
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passUserData();
            }
        });
    }

    public void showUserData() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        databaseReference.child("Student").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren()) {
                    String key = i.getKey().toString();
                    if (key.equals("name")) {
                        name = i.getValue(String.class);
                    } else if (key.equals("course")) {
                        course = i.getValue(String.class);
                    } else if (key.equals("roll")) {
                        roll = i.getValue(String.class);
                    } else if (key.equals("password")) {
                        password = i.getValue(String.class);
                    } else if (key.equals("section")) {
                        section = i.getValue(String.class);
                    }
                }
                email = email.replace(",",".");
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
    }
    public void passUserData() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        databaseReference.child("Student").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren()) {
                    String key = i.getKey().toString();
                    if (key.equals("name")) {
                        name = i.getValue(String.class);
                    } else if (key.equals("course")) {
                        course = i.getValue(String.class);
                    } else if (key.equals("roll")) {
                        roll = i.getValue(String.class);
                    } else if (key.equals("password")) {
                        password = i.getValue(String.class);
                    } else if (key.equals("section")) {
                        section = i.getValue(String.class);
                    }
                }
                Intent intent1 = new Intent(Profile.this, EditProfile_Student.class);
                intent1.putExtra("name", name);
                intent1.putExtra("course", course);
                intent1.putExtra("roll", roll);
                intent1.putExtra("email", email);
                intent1.putExtra("section", section);
                intent1.putExtra("password", password);
                startActivity(intent1);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}