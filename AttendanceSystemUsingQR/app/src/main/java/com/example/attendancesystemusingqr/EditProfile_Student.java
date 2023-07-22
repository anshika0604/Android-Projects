package com.example.attendancesystemusingqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile_Student extends AppCompatActivity {

    EditText editName, editRoll, editCourse, editSection, editPassword;
    Button saveButton;
    String nameUser, rollUser, courseUser, sectionUser, passwordUser, email;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://qr-based-attendance-7053b-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_student);

        editName = findViewById(R.id.editName);
        editRoll = findViewById(R.id.editRoll);
        editCourse = findViewById(R.id.editCourse);
        editSection = findViewById(R.id.editSection);
        editPassword = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        email = email.replace(".", ",");

        showData();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged() || isPasswordChanged() || isRollChanged() || isCourseChanged() || isSectionChanged()){
                    Toast.makeText(EditProfile_Student.this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfile_Student.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
                Intent intent1 = new Intent(EditProfile_Student.this, Profile.class);
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
            databaseReference.child("Student").child(email).child("name").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isRollChanged() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        email = email.replace(".", ",");
        if (!rollUser.equals(editRoll.getText().toString())){
            databaseReference.child("Student").child(email).child("roll").setValue(editRoll.getText().toString());
            rollUser = editRoll.getText().toString();
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
            databaseReference.child("Student").child(email).child("password").setValue(editPassword.getText().toString());
            passwordUser = editPassword.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isCourseChanged() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        email = email.replace(".", ",");
        if (!courseUser.equals(editCourse.getText().toString())){
            databaseReference.child("Student").child("email").child("course").setValue(editCourse.getText().toString());
            courseUser = editCourse.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isSectionChanged() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        email = email.replace(".", ",");
        if (!sectionUser.equals(editSection.getText().toString())){
            databaseReference.child("Student").child("email").child("section").setValue(editSection.getText().toString());
            sectionUser = editSection.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public void showData(){
        Intent intent = getIntent();
        nameUser = intent.getStringExtra("name");
        rollUser = intent.getStringExtra("roll");
        courseUser = intent.getStringExtra("course");
        sectionUser = intent.getStringExtra("section");
        passwordUser = intent.getStringExtra("password");
        editName.setText(nameUser);
        editRoll.setText(rollUser);
        editCourse.setText(courseUser);
        editSection.setText(sectionUser);
        editPassword.setText(passwordUser);
    }
}