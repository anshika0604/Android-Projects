package com.example.diagnostic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class PermissionPage extends AppCompatActivity {
    private Button checkButton;
    private static final int CAMERA_PERMISSION_REQUEST = 1;
    private static final int RECORD_AUDIO_PERMISSION_REQUEST = 2;
    private static final int LOCATION_PERMISSION_REQUEST = 3;
    private static final int CONTACTS_PERMISSION_REQUEST = 4;
    private static final int BLUETOOTH_PERMISSION_REQUEST = 5;
    private static final int PHONE_CALL_PERMISSION_REQUEST = 6;

    private int currentPermissionRequest = CAMERA_PERMISSION_REQUEST;
    private boolean permissionDeniedDialogShown = false;
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
        setContentView(R.layout.activity_permission_page);

        checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission(currentPermissionRequest);
            }
        });
    }
    private void requestPermission(int requestCode) {
        String permission;
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST:
                permission = android.Manifest.permission.CAMERA;
                break;
            case RECORD_AUDIO_PERMISSION_REQUEST:
                permission = android.Manifest.permission.RECORD_AUDIO;
                break;
            case LOCATION_PERMISSION_REQUEST:
                permission = android.Manifest.permission.ACCESS_FINE_LOCATION;
                break;
            case CONTACTS_PERMISSION_REQUEST:
                permission = android.Manifest.permission.READ_CONTACTS;
                break;
            case BLUETOOTH_PERMISSION_REQUEST:
                permission = android.Manifest.permission.BLUETOOTH;
                break;
            case PHONE_CALL_PERMISSION_REQUEST:
                permission = android.Manifest.permission.CALL_PHONE;
                break;
            default:
                permission = "";
        }

        if (!permission.isEmpty() && ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        } else {
            savePermissionInSharedPreferences(requestCode);
            // Move to the next permission request
            currentPermissionRequest++;
            if (currentPermissionRequest <= PHONE_CALL_PERMISSION_REQUEST) {
                requestPermission(currentPermissionRequest);
            } else {
                // All permissions are granted; move to the final activity
                startActivity(new Intent(this, FinalCheck.class));
            }
        }
    }
    private void savePermissionInSharedPreferences(int requestCode) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST:
                editor.putBoolean("cameraPermissionGranted", true);
                break;
            case RECORD_AUDIO_PERMISSION_REQUEST:
                editor.putBoolean("audioPermissionGranted", true);
                break;
            case LOCATION_PERMISSION_REQUEST:
                editor.putBoolean("locationPermissionGranted", true);
                break;
            case CONTACTS_PERMISSION_REQUEST:
                editor.putBoolean("contactsPermissionGranted", true);
                break;
            // Add other cases as needed
        }

        editor.apply();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == currentPermissionRequest) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, move to the next permission request
                currentPermissionRequest++;
                if (currentPermissionRequest <= PHONE_CALL_PERMISSION_REQUEST) {
                    requestPermission(currentPermissionRequest);
                } else {
                    // All permissions are granted; move to the final activity
                    startActivity(new Intent(this, FinalCheck.class));
                }
            } else {
                if (!permissionDeniedDialogShown) {
                    // Permission is denied, show a dialog or take appropriate action
                    showPermissionDeniedDialog();
                    permissionDeniedDialogShown = true;
                }
            }
        }
    }

    private void showPermissionDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Permission denied. All permissions are compulsory to complete the action.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        permissionDeniedDialogShown = false; // Allow the user to request permissions again
                    }
                });
        builder.create().show();
    }
}