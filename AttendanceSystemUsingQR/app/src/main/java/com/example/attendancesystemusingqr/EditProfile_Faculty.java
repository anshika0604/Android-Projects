package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditProfile_Faculty extends AppCompatActivity {

    EditText editName,  editPassword;
    MaterialCardView editCourse, editSubject;

    boolean [] selectedCourses, selectedSubjects;
    String[] courses = { "BCA", "MCA", "BSc IT", "BSc CS", "MSc IT", "BTech" };;
    String[] subjects = { "C Programming ", "Operating System", "Computer Architecture", "Discrete Mathematics", "Data Structure and Algorithms", "Web Development" , "Scripting Languages", "Java Programming"};
    ArrayList<Integer> courseList = new ArrayList<>();
    ArrayList<Integer> subjectList = new ArrayList<>();
    Button saveButton;
    String nameUser, courseUser, subjectUser, passwordUser, email, course, subject;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://qr-based-attendance-7053b-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_faculty);

        editName = findViewById(R.id.editName);
        editCourse = findViewById(R.id.editCourses);
        editSubject = findViewById(R.id.editSubjects);
        editPassword = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);

        showData();

        // courses Dialog Box
        selectedCourses = new boolean[courses.length];
        editCourse.setOnClickListener(v -> {
            showCourseDialog();
        });

        // Subjects Dialog Box
        selectedSubjects = new boolean[subjects.length];
        editSubject.setOnClickListener(v -> {
            showSubjectDialog();
        });
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        saveButton.setOnClickListener(view -> {
            boolean flag = false;
            if (isNameChanged() ){
                flag = true;
            }
            if(isPasswordChanged() ) {
                flag = true;
            }
            if(isCourseChanged() ) {
                flag = true;
            }
            if(isSubjectChanged()) {
                flag = true;
            }
            if( flag  == true) {
                Toast.makeText(EditProfile_Faculty.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), Profile_Faculty.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("email", email);
                startActivity(intent1);
                finish();
            } else {
                Toast.makeText(EditProfile_Faculty.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), Profile_Faculty.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("email", email);
                startActivity(intent1);
                finish();
            }
        });
    }
    private boolean isNameChanged() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        email = email.replace(".", ",");
        if (!nameUser.equals(editName.getText().toString())){
            databaseReference.child("Faculty").child(email).child("name").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isPasswordChanged() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        email = email.replace(".", ",");
        if (!passwordUser.equals(editPassword.getText().toString())){
            databaseReference.child("Faculty").child(email).child("password").setValue(editPassword.getText().toString());
            passwordUser = editPassword.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isCourseChanged() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        final StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<courseList.size();i++) {
            stringBuilder.append(courses[courseList.get(i)]);

            if( i != courseList.size()-1) {
                stringBuilder.append("/");
            }
        }
        course = stringBuilder.toString();
        Log.d("hello - course ", course);
        Log.d("Hello - courseUser", courseUser);

        if (!courseUser.equals(course)){
            databaseReference.child("Faculty").child(email).child("courses").setValue(course);
            courseUser = course;
            return true;
        } else {
            return false;
        }
    }
    private boolean isSubjectChanged() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        final StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<subjectList.size();i++) {
            stringBuilder.append(subjects[subjectList.get(i)]);

            if( i != subjectList.size()-1) {
                stringBuilder.append("/");
            }
        }
        subject = stringBuilder.toString();
        Log.d("hello - subject ", subject);
        Log.d("Hello - subjectUser", subjectUser);
        if (!subjectUser.equals(subject)){
            databaseReference.child("Faculty").child(email).child("subjects").setValue(subject);
            subjectUser = subject;
            return true;
        } else {
            return false;
        }
    }
    private void showCourseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile_Faculty.this);
        builder.setTitle("Edit Courses");
        builder.setCancelable(false);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        String str = "";
        for(int i=0;i<courseUser.length();i++) {
            char ch = courseUser.charAt(i);
            if(ch != '/') {
                str += ch;
            }
            else {
                for(int j =0;j<courses.length;j++) {
                    if(courses[j].equals(str)) {
                        selectedCourses[j] = true;
                        courseList.add(j);
                    }
                }
                str = "";
            }
        }
        for(int j =0;j<courses.length;j++) {
            if(courses[j].equals(str)) {
                selectedCourses[j] = true;
                courseList.add(j);
            }
        }
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
                dialog.dismiss();
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
                }
            }
        });
        builder.show();
    }
    private void showSubjectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile_Faculty.this);
        builder.setTitle("Edit Subjects");
        builder.setCancelable(false);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        String str = "";
        for (int i = 0; i < subjectUser.length(); i++) {
            char ch = subjectUser.charAt(i);
            if (ch != '/') {
                str += ch;
            } else {
                for (int j = 0; j < subjects.length; j++) {
                    if (subjects[j].equals(str)) {
                        selectedSubjects[j] = true;
                        subjectList.add(j);
                        break;
                    }
                }
                str = "";
            }
        }
        for (int j = 0; j < subjects.length; j++) {
            if (subjects[j].equals(str)) {
                selectedSubjects[j] = true;
                subjectList.add(j);
                break;
            }
        }
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
                dialog.dismiss();
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
                }
            }
        });
        builder.show();
    }

    public void showData(){
        Intent intent = getIntent();
        nameUser = intent.getStringExtra("name");
        courseUser = intent.getStringExtra("course");
        subjectUser = intent.getStringExtra("subject");
        passwordUser = intent.getStringExtra("password");
        editName.setText(nameUser);
        editPassword.setText(passwordUser);
    }
}