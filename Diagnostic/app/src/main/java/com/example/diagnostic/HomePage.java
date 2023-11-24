package com.example.diagnostic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class HomePage extends AppCompatActivity {
    private CardView check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
            window.setNavigationBarColor(ContextCompat.getColor(this, android.R.color.transparent));

            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_status_bar_gradient));
        }
        setContentView(R.layout.activity_home_page);

        check = findViewById(R.id.check);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, PermissionPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}