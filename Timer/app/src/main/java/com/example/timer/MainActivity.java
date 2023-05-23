package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int number = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(35000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("lpg", "onTick: Negi");
            }

            @Override
            public void onFinish() {
                Log.d("lpg", "Task Finished");
            }
        }.start();
        // Task Scheduler using Runnable and Handler
//        final Handler handler = new Handler();
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//                number++;
//                Toast.makeText(MainActivity.this, "This is a toast "+number , Toast.LENGTH_SHORT).show();
//                handler.postDelayed(this, 2000);
//            }
//        };
//        handler.post(run);
    }
}