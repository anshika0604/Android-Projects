package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Faculty_registration extends AppCompatActivity {


    FirebaseAuth mAuth;

    // Create object to DatabaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://qr-based-attendance-7053b-default-rtdb.firebaseio.com/");
    MaterialCardView selectCard, selectSubjectCard;
    TextView tyCourses, tySubjects;
    boolean [] selectedCourses, selectedSubjects;
    ArrayList<Integer> courseList = new ArrayList<>();
    ArrayList<Integer> subjectList = new ArrayList<>();
    String[] courses = { "BCA", "MCA", "BSc IT", "BSc CS", "MSc IT", "BTech" };
    String[] subjects = { "C Programming ", "Operating System", "Computer Architecture", "Discrete Mathematics", "Data Structure and Algorithms", "Web Development" , "Scripting Languages", "Java Programming"};

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

        // Initialise for course
        selectCard  = findViewById(R.id.course_select);
        tyCourses = findViewById(R.id.course_text);
        selectedCourses = new boolean[courses.length];

        selectCard.setOnClickListener(v -> {
            showCourseDialog();
        });

        // Initialise for subject
        selectSubjectCard  = findViewById(R.id.subject_select);
        tySubjects = findViewById(R.id.subject_text);
        selectedSubjects = new boolean[subjects.length];

        selectSubjectCard.setOnClickListener(v -> {
            showSubjectDialog();
        });

        // Authentication
        final EditText emailText = findViewById(R.id.emailFaculty);
        final EditText passText = findViewById(R.id.passFaculty);
        final EditText nameText = findViewById(R.id.nameFaculty);
        final Button btnRegister = findViewById(R.id.Register);
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email;
                String password;
                String name;
                final String[] course = new String[1];
                final String[] subject = new String[1];
                email = emailText.getText().toString();
                email = email.replace(".",",");
                password = passText.getText().toString();
                name = nameText.getText().toString();

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

                                final StringBuilder stringBuilder = new StringBuilder();
                                for(int i=0;i<courseList.size();i++) {
                                    stringBuilder.append(courses[courseList.get(i)]);

                                    if( i != courseList.size()-1) {
                                        stringBuilder.append("/");
                                    }
                                }
                                course[0] = stringBuilder.toString();
                                final StringBuilder stringBuilder1 = new StringBuilder();
                                for(int i=0;i<subjectList.size();i++) {
                                    stringBuilder1.append(subjects[subjectList.get(i)]);

                                    if( i != subjectList.size()-1) {
                                        stringBuilder1.append("/");
                                    }
                                }
                                subject[0] = stringBuilder1.toString();
                                HashMap<String, String> m =new HashMap<String, String>();
                                m.put("name", name);
                                m.put("password", password);
                                m.put("courses", course[0]);
                                m.put("subjects", subject[0]);
                                databaseReference.child("Faculty").child(finalEmail).setValue(m);
                                Toast.makeText(Faculty_registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Faculty_selectionpage.class);
                                intent.putExtra("email", finalEmail);
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

    }

    public void faculty_login(View view) {
        Intent intent = new Intent(Faculty_registration.this, Faculty_selectionpage.class);
        startActivity(intent);
    }

    private void showCourseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Faculty_registration.this);
        builder.setTitle("Select Courses");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(courses, selectedCourses, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if(isChecked) {
                    courseList.add(which);
                }else {
                    courseList.remove(courseList.indexOf(which));
                }
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // create String Builder

                StringBuilder stringBuilder = new StringBuilder();
                for(int i=0;i<courseList.size();i++) {
                    stringBuilder.append(courses[courseList.get(i)]);

                    if( i != courseList.size()-1) {
                        stringBuilder.append(", ");
                    }
                }
                tyCourses.setText(stringBuilder.toString());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for(int i=0;i<selectedCourses.length;i++) {
                    selectedCourses[i] = false;

                    courseList.clear();
                    tyCourses.setText("Select Course");
                }
            }
        });
        builder.show();
    }

    private void showSubjectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Faculty_registration.this);
        builder.setTitle("Select Subjects");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(subjects, selectedSubjects, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if(isChecked) {
                    subjectList.add(which);
                }else {
                    subjectList.remove(subjectList.indexOf(which));
                }
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // create String Builder

                StringBuilder stringBuilder = new StringBuilder();
                for(int i=0;i<subjectList.size();i++) {
                    stringBuilder.append(subjects[subjectList.get(i)]);

                    if( i != subjectList.size()-1) {
                        stringBuilder.append(", ");
                    }
                }
                tySubjects.setText(stringBuilder.toString());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for(int i=0;i<selectedSubjects.length;i++) {
                    selectedSubjects[i] = false;

                    subjectList.clear();
                    tySubjects.setText("Select Subject");
                }
            }
        });
        builder.show();
    }

}