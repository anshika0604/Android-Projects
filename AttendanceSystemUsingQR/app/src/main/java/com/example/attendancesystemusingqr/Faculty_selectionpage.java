package com.example.attendancesystemusingqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Faculty_selectionpage extends AppCompatActivity {

    Button view, mark, btnSignOut;
    FloatingActionButton fab;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_selectionpage);

        // Toolbar Styling and Back Button
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button_foreground);

        // Get Email
        Intent intent = getIntent();
        String email =  intent.getStringExtra("email");

        // Navigation Views
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,myToolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null ) {
            navigationView.setCheckedItem(R.id.nav_home);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    Log.d("Hello", "i am selected "+item.getItemId());
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();

                } else if (item.getItemId() == R.id.nav_add) {
                    Log.d("Hello", "i am selected "+item.getItemId());
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();

                } else if (item.getItemId() == R.id.nav_mark) {
                    Log.d("Hello", "i am selected "+item.getItemId());
                    Intent intent1 = new Intent(getApplicationContext(), Faculty_attendance_page.class);
                    intent1.putExtra("email",email);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();

                } else if (item.getItemId() == R.id.nav_view_attendance) {
                    Log.d("Hello", "i am selected "+item.getItemId());
                    startActivity(new Intent(getApplicationContext(), View_details.class));
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();

                } else if (item.getItemId() == R.id.nav_profile) {
                    Log.d("Hello", "i am selected "+item.getItemId());
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();

                }  else if (item.getItemId() == R.id.nav_logout) {
                    Log.d("Hello", "i am selected "+item.getItemId());
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), Faculty_loginpage.class);
                    startActivity(intent);
                    finish();

                }
                return true;
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
                return true;
            } else if (itemId == R.id.add) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
                return true;
            } else if (itemId == R.id.mark) {
                Intent intent1 = new Intent(getApplicationContext(), Faculty_attendance_page.class);
                intent1.putExtra("email",email);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
                return true;
            } else if (itemId == R.id.view) {
                startActivity(new Intent(getApplicationContext(), View_details.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
                return true;
            }

            return false;
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                finish();
            }
        });

        // Button Functions

        mark = findViewById(R.id.mark);
        view = findViewById(R.id.view);

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Faculty_attendance_page.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), View_details.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();
            }
        });
    }

}