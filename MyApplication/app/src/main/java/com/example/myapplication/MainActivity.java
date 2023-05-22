package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public void sendNow(View view) {
        Toast.makeText(this, "Sending data from app....", Toast.LENGTH_SHORT).show();
    }
    public void ReceiveNow(View view) {
        Toast.makeText(this, "Receiving data from app....", Toast.LENGTH_SHORT).show();
    }
    public void DeleteNow(View view) {
        Toast.makeText(this, "Deleting data from app....", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}