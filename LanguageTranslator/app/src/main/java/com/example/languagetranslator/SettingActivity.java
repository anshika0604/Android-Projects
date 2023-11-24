package com.example.languagetranslator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //below line is to change the title of our action bar.
        getSupportActionBar().setTitle("Settings");
        //below line is used to check if frame layout is empty or not.
        if (findViewById(R.id.idFrameLayout) != null) {
            if (savedInstanceState != null) {
                return;
            }

            getFragmentManager().beginTransaction().add(R.id.idFrameLayout, new SettingsFragment()).commit();

        }
    }
}
