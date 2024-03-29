package com.example.facedetectionusingfirebaseml;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    AppCompatButton cameraButton;

    private final static int REQUEST_IMAGE_CAPTURE = 124;
    FirebaseVisionImage image;
    FirebaseVisionFaceDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        cameraButton = findViewById(R.id.camera_button);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null) {
                    //startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    Log.d("hello 1", intent.toString());
                    someActivityResultLauncher.launch(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bundle extra = data.getExtras();
                        Bitmap bitmap = (Bitmap)extra.get("data");
                        Log.d("hello 2", data.toString());
                        detectFace(bitmap);

                    }
                }
            });
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extra = data.getExtras();
//            Bitmap bitmap = (Bitmap)extra.get("data");
//            detectFace(bitmap);
//        }
//    }
    private void detectFace(Bitmap bitmap) {
        Log.d("hello 3", bitmap.toString());
        FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder().setModeType(FirebaseVisionFaceDetectorOptions.ACCURATE_MODE).setLandmarkType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS).setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS).build();


            image = FirebaseVisionImage.fromBitmap(bitmap);
            detector = FirebaseVision.getInstance().getVisionFaceDetector(options);
            Log.d("hello 4", image.toString());
            Log.d("hello 5", detector.toString());



        detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                        String resultText = "";
                        int i = 1;
                        for(FirebaseVisionFace face: firebaseVisionFaces) {
                            resultText = resultText.concat("\nFACE NUMBER - " + i + ": ").concat("\nSmile: " + face.getSmilingProbability()*100 + "%").concat("\nLeft Eye Open: "+face.getLeftEyeOpenProbability()*100 + "%").concat("\nRight Eye Open: " + face.getRightEyeOpenProbability()*100 + "%");
                            i++;
                        }
                        if(firebaseVisionFaces.size() == 0) {
                            Toast.makeText(MainActivity.this, "NO FACE DETECTED", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Bundle bundle = new Bundle();
                            bundle.putString(LCOFaceDetection.RESULT_TEXT, resultText);
                            DialogFragment resultdialog = new ResultDialog();
                            resultdialog.setArguments(bundle);
                            resultdialog.setCancelable(true);
                            resultdialog.show(getSupportFragmentManager(), LCOFaceDetection.RESULT_DIALOG);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Oops, Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}