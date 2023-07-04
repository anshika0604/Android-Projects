package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
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


public class LoginPage extends AppCompatActivity {

    Button btnLogin;
    EditText emailText, passText;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://qr-based-attendance-7053b-default-rtdb.firebaseio.com/");

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), QR_Scanner.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        // Authentication
        emailText = findViewById(R.id.emailStudent);
        passText = findViewById(R.id.passStudent);
        btnLogin = findViewById(R.id.Login);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email, password;
                email = emailText.getText().toString();
                password = passText.getText().toString();

                if( TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    if(TextUtils.isEmpty(email)) {
                        Toast.makeText(LoginPage.this, "Enter email", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(password)) {
                        Toast.makeText(LoginPage.this, "Enter password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    databaseReference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(email)) {
                                String getPassword = snapshot.child(email).child("password").getValue(String.class);
                                String roll = snapshot.child(email).child("roll").getValue(String.class);
                                String name = snapshot.child(email).child("name").getValue(String.class);
                                if(getPassword.equals(password)) {
                                    Toast.makeText(LoginPage.this, "Successfully Logged in ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), QR_Scanner.class);
                                    intent.putExtra("roll",roll);
                                    intent.putExtra("name",name);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
//                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginPage.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(LoginPage.this, "Email Does not Exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        // Toolbar Styling & Back Button
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button_foreground);



    }
    public void student_register(View view) {
        Intent intent = new Intent(LoginPage.this, Student_registration.class);
        startActivity(intent);
    }
    public void attendance(View view) {
        Intent intent = new Intent(LoginPage.this, QR_Scanner.class);
        startActivity(intent);
    }

}