
package com.example.attendancesystemusingqr;

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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QR_Scanner extends AppCompatActivity {

    Button btnSignOut, btn_scanner;
    TextView scannedTV;

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
                scannedTV.setText(intentResult.getContents());
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}