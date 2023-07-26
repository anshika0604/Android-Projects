package com.example.facedetectionusingfirebaseml;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class LCOFaceDetection extends Application {
    public final static String RESULT_TEXT = "RESULT_TEXT";
    public final static String RESULT_DIALOG = "RESULT_DIALOG";

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }

}
