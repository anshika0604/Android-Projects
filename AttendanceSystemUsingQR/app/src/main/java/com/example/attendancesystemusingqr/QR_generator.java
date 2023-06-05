package com.example.attendancesystemusingqr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QR_generator extends AppCompatActivity {

    Button genertor_qr, btnSignOut;
    String value;

    ImageView iv_output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generator);

        // Toolbar Styling and Back Button
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button_foreground);

        // QR Generator
        genertor_qr = findViewById(R.id.generate);
        iv_output = findViewById(R.id.qrCode);

        genertor_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = "Attendance Marked Successfully";

                // Initialize multi format writer
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = writer.encode(value, BarcodeFormat.QR_CODE,800,900);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    iv_output.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        // Log Out
        btnSignOut = findViewById(R.id.signOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Faculty_loginpage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}