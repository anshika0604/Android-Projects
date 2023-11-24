package com.example.netclanclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class refine extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    ImageView back;
    String[] options = { "Available | Hey Let Us Connect", "Away | Stay Discrete And Watch", "Busy | Do Not Disturb | Will Catch Up Later", "SOS | Emergency | Need Assistance | HELP" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refine);

        Spinner spin1 = findViewById(R.id.spinner1);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, options);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(ad);


        back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn1 = (findViewById(R.id.button1));
        // Set OnClick listener for Button programmatically
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String colorCode = (String)btn1.getTag();
                if(colorCode.equals("#FFFFFF")) {
                    btn1.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
                    btn1.setTextColor(getResources().getColor(R.color.white));
                    btn1.setTag("#30406A");
                }
                else if(colorCode.equals("#30406A")){
                    btn1.setBackgroundColor(getResources().getColor(R.color.white));
                    btn1.setTextColor(getResources().getColor(R.color.toolbar_color));
                    btn1.setTag("#FFFFFF");
                }
            }
        });
        btn2 = (findViewById(R.id.button2));
        // Set OnClick listener for Button programmatically
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String colorCode = (String)btn2.getTag();
                if(colorCode.equals("#FFFFFF")) {
                    btn2.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
                    btn2.setTextColor(getResources().getColor(R.color.white));
                    btn2.setTag("#30406A");
                }
                else if(colorCode.equals("#30406A")){
                    btn2.setBackgroundColor(getResources().getColor(R.color.white));
                    btn2.setTextColor(getResources().getColor(R.color.toolbar_color));
                    btn2.setTag("#FFFFFF");
                }
            }
        });
        btn3 = (findViewById(R.id.button3));
        // Set OnClick listener for Button programmatically
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String colorCode = (String)btn3.getTag();
                if(colorCode.equals("#FFFFFF")) {
                    btn3.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
                    btn3.setTextColor(getResources().getColor(R.color.white));
                    btn3.setTag("#30406A");
                }
                else if(colorCode.equals("#30406A")){
                    btn3.setBackgroundColor(getResources().getColor(R.color.white));
                    btn3.setTextColor(getResources().getColor(R.color.toolbar_color));
                    btn3.setTag("#FFFFFF");
                }
            }
        });
        btn4 = (findViewById(R.id.button4));
        // Set OnClick listener for Button programmatically
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String colorCode = (String)btn4.getTag();
                if(colorCode.equals("#FFFFFF")) {
                    btn4.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
                    btn4.setTextColor(getResources().getColor(R.color.white));
                    btn4.setTag("#30406A");
                }
                else if(colorCode.equals("#30406A")){
                    btn4.setBackgroundColor(getResources().getColor(R.color.white));
                    btn4.setTextColor(getResources().getColor(R.color.toolbar_color));
                    btn4.setTag("#FFFFFF");
                }
            }
        });
        btn5 = (findViewById(R.id.button5));
        // Set OnClick listener for Button programmatically
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String colorCode = (String)btn5.getTag();
                if(colorCode.equals("#FFFFFF")) {
                    btn5.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
                    btn5.setTextColor(getResources().getColor(R.color.white));
                    btn5.setTag("#30406A");
                }
                else if(colorCode.equals("#30406A")){
                    btn5.setBackgroundColor(getResources().getColor(R.color.white));
                    btn5.setTextColor(getResources().getColor(R.color.toolbar_color));
                    btn5.setTag("#FFFFFF");
                }
            }
        });
        btn6 = (findViewById(R.id.button6));
        // Set OnClick listener for Button programmatically
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String colorCode = (String)btn6.getTag();
                if(colorCode.equals("#FFFFFF")) {
                    btn6.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
                    btn6.setTextColor(getResources().getColor(R.color.white));
                    btn6.setTag("#30406A");
                }
                else if(colorCode.equals("#30406A")){
                    btn6.setBackgroundColor(getResources().getColor(R.color.white));
                    btn6.setTextColor(getResources().getColor(R.color.toolbar_color));
                    btn6.setTag("#FFFFFF");
                }
            }
        });
        btn7 = (findViewById(R.id.button7));
        // Set OnClick listener for Button programmatically
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String colorCode = (String)btn7.getTag();
                if(colorCode.equals("#FFFFFF")) {
                    btn7.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
                    btn7.setTextColor(getResources().getColor(R.color.white));
                    btn7.setTag("#30406A");
                }
                else if(colorCode.equals("#30406A")){
                    btn7.setBackgroundColor(getResources().getColor(R.color.white));
                    btn7.setTextColor(getResources().getColor(R.color.toolbar_color));
                    btn7.setTag("#FFFFFF");
                }
            }
        });
        btn8 = (findViewById(R.id.button8));
        // Set OnClick listener for Button programmatically
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String colorCode = (String)btn8.getTag();
                if(colorCode.equals("#FFFFFF")) {
                    btn8.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
                    btn8.setTextColor(getResources().getColor(R.color.white));
                    btn8.setTag("#30406A");
                }
                else if(colorCode.equals("#30406A")){
                    btn8.setBackgroundColor(getResources().getColor(R.color.white));
                    btn8.setTextColor(getResources().getColor(R.color.toolbar_color));
                    btn8.setTag("#FFFFFF");
                }
            }
        });
    }
}