package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Profile_Faculty extends AppCompatActivity {

    TextView profileName, profileEmail, profilePassword, titleName, titleEmail;
    MaterialCardView profileCourses, profileSubjects;
    boolean [] selectedCourses, selectedSubjects;
    String[] courses = { "BCA", "MCA", "BSc IT", "BSc CS", "MSc IT", "BTech" };;
    String[] subjects = { "C Programming ", "Operating System", "Computer Architecture", "Discrete Mathematics", "Data Structure and Algorithms", "Web Development" , "Scripting Languages", "Java Programming"};
    String name, email, course, subject, password;
    Button editButton;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://qr-based-attendance-7053b-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_faculty);

        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileCourses = findViewById(R.id.profileCourses);
        profilePassword = findViewById(R.id.profilePassword);
        profileSubjects = findViewById(R.id.profileSubjects);
        titleName = findViewById(R.id.titleName);
        titleEmail = findViewById(R.id.titleEmail);
        editButton = findViewById(R.id.editButton);
        showUserData();

        // courses Dialog Box
        selectedCourses = new boolean[courses.length];
        profileCourses.setOnClickListener(v -> {
            showCourseDialog();
        });

        // Subjects Dialog Box
        selectedSubjects = new boolean[subjects.length];
        profileSubjects.setOnClickListener(v -> {
            showSubjectDialog();
        });
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
        databaseReference.child("Faculty").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren()) {
                    String key = i.getKey().toString();
                    if (key.equals("name")) {
                        name = i.getValue(String.class);
                    } else if (key.equals("password")) {
                        password = i.getValue(String.class);
                    }
                }
                email = email.replace(",",".");
                titleName.setText(name);
                titleEmail.setText(email);
                profileName.setText(name);
                profileEmail.setText(email);
                profilePassword.setText(password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showCourseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile_Faculty.this);
        builder.setTitle("Courses");
        builder.setCancelable(false);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        databaseReference.child("Faculty").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot i : snapshot.getChildren()) {
                    String key = i.getKey().toString();

                    if (key.equals("courses")) {
                        course = i.getValue(String.class);

                    }
                }
                String str = "";
                for(int i=0;i<course.length();i++) {
                    char ch = course.charAt(i);
                    if(ch != '/') {
                        str += ch;
                    }
                    else {
                        for(int j =0;j<courses.length;j++) {
                            if(courses[j].equals(str)) {
                                selectedCourses[j] = true;

                                break;
                            }
                        }
                        str = "";
                    }
                }
                for(int j =0;j<courses.length;j++) {
                    if(courses[j].equals(str)) {
                        selectedCourses[j] = true;

                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        builder.setMultiChoiceItems(courses, selectedCourses, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void showSubjectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile_Faculty.this);
        builder.setTitle("Subjects");
        builder.setCancelable(false);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        databaseReference.child("Faculty").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot i : snapshot.getChildren()) {
                    String key = i.getKey().toString();

                    if (key.equals("subjects")) {
                        subject = i.getValue(String.class);
                    }
                }
                String str = "";
                for(int i=0;i<subject.length();i++) {
                    char ch = subject.charAt(i);
                    if(ch != '/') {
                        str += ch;
                    }
                    else {
                        for(int j =0;j<subjects.length;j++) {
                            if(subjects[j].equals(str)) {
                                selectedSubjects[j] = true;

                                break;
                            }
                        }
                        str = "";
                    }
                }
                for(int j =0;j<subjects.length;j++) {
                    if(subjects[j].equals(str)) {
                        selectedSubjects[j] = true;

                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        builder.setMultiChoiceItems(subjects, selectedSubjects, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    public void passUserData() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        databaseReference.child("Faculty").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren()) {
                    String key = i.getKey().toString();
                    if (key.equals("name")) {
                        name = i.getValue(String.class);
                    } else if (key.equals("password")) {
                        password = i.getValue(String.class);
                    } else if (key.equals("courses")) {
                        course = i.getValue(String.class);
                    } else if (key.equals("subjects")) {
                        subject = i.getValue(String.class);
                    }

                }
                Intent intent1 = new Intent(Profile_Faculty.this, EditProfile_Faculty.class);
                intent1.putExtra("name", name);
                intent1.putExtra("course", course);
                intent1.putExtra("email", email);
                intent1.putExtra("subject", subject);
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