
package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

public class QR_Scanner extends AppCompatActivity {

    Button btnSignOut, btn_scanner;
    //Button profilePage;
    TextView scannedTV;
    String course, subject, section, date, email;
    int count = 0;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://qr-based-attendance-7053b-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        // Toolbar Styling and Back Button
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button_foreground);

        // Log Out
        btnSignOut = findViewById(R.id.signOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        // QR Code Scanner
        btn_scanner = findViewById(R.id.scanner_btn);
        scannedTV = findViewById(R.id.scan_details);

        // Profile page open
        //profilePage = findViewById(R.id.profileclick);
//        profilePage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = getIntent();
//                email = intent.getStringExtra("email");
//                Intent intent1 = new Intent(getApplicationContext(), Profile.class);
//                intent1.putExtra("email", email);
//                startActivity(intent1);
//                finish();
//            }
//        });


        btn_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(QR_Scanner.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setPrompt("Scan a QR Code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });

    }

    // QR Scanner

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null) {
            String contents = intentResult.getContents();
            if(contents != null) {
                String str = "";
                for(int i=0;i<contents.length();i++) {
                    char s = contents.charAt(i);
                    if(s == '/') {
                        if(count == 0) {
                            course = str;
                        }
                        else if(count == 1) {
                            subject = str;
                        }
                        else if(count == 2) {
                            section = str;
                        }
                        else {
                            date = str;
                        }
                        str = "";
                        count++;
                    }
                    else {
                        str+= s;
                    }
                }

                // Get roll and Name of Student
                Intent intent = getIntent();
                String roll = intent.getStringExtra("roll");
                String name = intent.getStringExtra("name");
                databaseReference.child(course).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseReference.child(course).child(subject).child(section).child(date).child(roll).setValue(name);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                scannedTV.setText("Attendance Marked Successfully");
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}