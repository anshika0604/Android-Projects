package com.example.diagnostic;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;

import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.AudioFormat;

import android.media.AudioRecord;
import android.media.AudioTrack;

import android.media.MediaRecorder;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.preference.PreferenceManager;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;


public class FinalCheck extends AppCompatActivity implements SensorEventListener {

    private ImageView rootStatus, bluetoothStatus, micStatus, gyroStatus, gpsStatus, cameraStatus, accStatus;
    private Button checkButton;
    private static BluetoothAdapter bluetoothAdapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private FrameLayout frameLayout;
    private SurfaceView cameraView;
    private SurfaceView cameraView2;
    private Camera camera;
    TableLayout testResultsTable;

    Boolean cameraResult, rootedResult, micResult, bluetoothResult, gpsResult, gyroResult, accResult;



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
        setContentView(R.layout.activity_final_check);

        checkButton = findViewById(R.id.start);
        rootStatus = findViewById(R.id.rootStatus);
        bluetoothStatus = findViewById(R.id.bluetoothStatus);
        micStatus = findViewById(R.id.micStatus);
        gyroStatus = findViewById(R.id.gyroStatus);
        gpsStatus = findViewById(R.id.gpsStatus);
        accStatus = findViewById(R.id.accStatus);
        cameraStatus = findViewById(R.id.cameraStatus);
        cameraView = findViewById(R.id.smallCameraView);
        cameraView2 = findViewById(R.id.cameraView);
        frameLayout = findViewById(R.id.framelayout);
        frameLayout.setVisibility(View.GONE);
        testResultsTable = findViewById(R.id.testResultsTable);

        boolean check = checkPermissions();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        if (check) {
            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkButton.setBackgroundColor(getResources().getColor(R.color.primary));
                    checkButton.setTextColor(getResources().getColor(R.color.black));
                    checkButton.setText("7 tests Remaining");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (checkCameraPermissions()) {
                                startCamera1();
                                frameLayout.setVisibility(View.VISIBLE);
                                cameraView.setVisibility(View.VISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        cameraView.setVisibility(View.GONE);
                                        startCamera2();
                                        cameraView2.setVisibility(View.VISIBLE);
                                    }
                                }, 2000);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        frameLayout.setVisibility(View.GONE);
                                        cameraResult = true;
                                        cameraStatus.setImageResource(R.drawable.baseline_check_circle_outline_24);
                                    }
                                }, 4000);
                            } else {
                                cameraResult = false;
                                cameraStatus.setImageResource(R.drawable.baseline_cancel_24);
                            }
                            checkButton.setText("6 tests Remaining");
                        }
                    }, 2000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (isMicrophoneAvailable()) {
                                if (isSpeakerAvailable(FinalCheck.this)) {
                                    micResult = true;
                                    micStatus.setImageResource(R.drawable.baseline_check_circle_outline_24);
                                } else {
                                    micResult = false;
                                    micStatus.setImageResource(R.drawable.baseline_cancel_24);
                                }
                            } else {
                                micResult = false;
                                micStatus.setImageResource(R.drawable.baseline_cancel_24);
                            }
                            checkButton.setText("5 tests Remaining");
                        }
                    }, 7000);
                    boolean isEnabled = checkBluetooth();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isEnabled) {
                                bluetoothResult = true;
                                bluetoothStatus.setImageResource(R.drawable.baseline_check_circle_outline_24);
                            } else {
                                bluetoothResult = false;
                                bluetoothStatus.setImageResource(R.drawable.baseline_cancel_24);
                            }
                            checkButton.setText("4 tests Remaining");
                        }
                    }, 10000);
                    boolean isRooted = isDeviceRooted();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isRooted) {
                                rootedResult = true;
                                rootStatus.setImageResource(R.drawable.baseline_check_circle_outline_24);
                            } else {
                                rootedResult = false;
                                rootStatus.setImageResource(R.drawable.baseline_cancel_24);
                            }
                            checkButton.setText("3 tests Remaining");
                        }
                    }, 15000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            boolean check = isAccelerometerAvailable(FinalCheck.this);
                            accResult = check;
                            if (check) {
                                accStatus.setImageResource(R.drawable.baseline_check_circle_outline_24);
                            } else {
                                accStatus.setImageResource(R.drawable.baseline_cancel_24);
                            }
                            checkButton.setText("2 test Remaining");
                        }
                    }, 20000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            boolean check = isGpsAvailableAndEnabled(FinalCheck.this);
                            gpsResult = check;
                            if (check) {
                                gpsStatus.setImageResource(R.drawable.baseline_check_circle_outline_24);
                            } else {
                                gpsStatus.setImageResource(R.drawable.baseline_cancel_24);
                            }
                            checkButton.setText("1 tests Remaining");
                        }
                    }, 25000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

                            if (gyroscopeSensor != null) {

                                gyroResult = true;
                                sensorManager.registerListener(FinalCheck.this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
                                gyroStatus.setImageResource(R.drawable.baseline_check_circle_outline_24);
                            } else {
                                gyroResult = false;
                                gyroStatus.setImageResource(R.drawable.baseline_cancel_24);
                            }
                            checkButton.setText("Finished");
                            checkButton.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                    }, 30000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkButton.setText("Report");
                            checkButton.setBackgroundColor(getResources().getColor(R.color.blue));
                            LinearLayout linearLayout = findViewById(R.id.mainLayout);
                            linearLayout.setVisibility(View.GONE);
                            testResultsTable.setVisibility(View.VISIBLE);
                            addTableRow("Rooted Device: ", rootedResult ? "Passed" : "Failed");
                            addTableRow("Bluetooth Enabled: ", bluetoothResult ? "Passed" : "Failed");
                            addTableRow("Camera Working: ", cameraResult ? "Passed" : "Failed");
                            addTableRow("Mic & Speaker Enabled: ", micResult ? "Passed" : "Failed");
                            addTableRow("Accelerometer Sensor: ", accResult ? "Passed" : "Failed");
                            addTableRow("GyroScope Sensor: ", gyroResult ? "Passed" : "Failed");
                            addTableRow("GPS Sensor: ", gpsResult ? "Passed" : "Failed");
                            pushDataToFirebase();
                        }
                    }, 40000);
                }
            });
        }
    }
    private void pushDataToFirebase() {

        DatabaseReference resultsRef = databaseReference.child("test_results");

        TestResult rootedResultObject = new TestResult("Rooted Device", rootedResult ? "Passed" : "Failed");
        TestResult bluetoothResultObject = new TestResult("Bluetooth Enabled", bluetoothResult ? "Passed" : "Failed");
        TestResult cameraResultObject = new TestResult("Camera Working: ", cameraResult ? "Passed" : "Failed");
        TestResult micResultObject = new TestResult("Mic & Speaker Enabled: ", micResult ? "Passed" : "Failed");
        TestResult accsensorObject = new TestResult("Accelerometer Sensor: ", accResult ? "Passed" : "Failed");
        TestResult gyroScopeObject = new TestResult("GyroScope Sensor: ", gyroResult ? "Passed" : "Failed");
        TestResult gpsSensorObject = new TestResult("GPS Sensor: ", gpsResult ? "Passed" : "Failed");

        resultsRef.push().setValue(rootedResultObject);
        resultsRef.push().setValue(bluetoothResultObject);
        resultsRef.push().setValue(cameraResultObject);
        resultsRef.push().setValue(micResultObject);
        resultsRef.push().setValue(accsensorObject);
        resultsRef.push().setValue(gyroScopeObject);
        resultsRef.push().setValue(gpsSensorObject);

    }
    private void addTableRow(String testName, String testResult) {
        TableRow row = new TableRow(this);

        TextView testNameView = new TextView(this);
        testNameView.setText(testName);
        testNameView.setTextSize(20);
        testNameView.setPadding(8, 0, 16, 0);

        TextView testResultView = new TextView(this);
        testResultView.setTextSize(20);
        testResultView.setText(testResult);

        row.addView(testNameView);
        row.addView(testResultView);

        testResultsTable.addView(row);
    }
    private boolean checkCameraPermissions() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void startCamera1() {
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                    Camera.Parameters parameters = camera.getParameters();

                    Camera.Size optimalSize = getOptimalPreviewSize(parameters.getSupportedPreviewSizes(), cameraView.getWidth(), cameraView.getHeight());
                    parameters.setPreviewSize(optimalSize.width, optimalSize.height);
                    camera.setDisplayOrientation(90);

                    camera.setParameters(parameters);
                    camera.setPreviewDisplay(holder);
                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // Handle changes if needed
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (camera != null) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            }
        });
    }
    private void startCamera2() {
        cameraView2.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    Camera.Parameters parameters = camera.getParameters();

                    Camera.Size optimalSize = getOptimalPreviewSize(parameters.getSupportedPreviewSizes(), cameraView2.getWidth(), cameraView2.getHeight());
                    parameters.setPreviewSize(optimalSize.width, optimalSize.height);
                    camera.setDisplayOrientation(90);

                    camera.setParameters(parameters);
                    camera.setPreviewDisplay(holder);
                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (camera != null) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            }
        });
    }
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int width, int height) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) width / height;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - height) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - height);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - height) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - height);
                }
            }
        }

        return optimalSize;
    }


        private boolean checkPermissions() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean cameraPermissionGranted = sharedPreferences.getBoolean("cameraPermissionGranted", true);
        boolean audioPermissionGranted = sharedPreferences.getBoolean("audioPermissionGranted", true);
        boolean locationPermissionGranted = sharedPreferences.getBoolean("locationPermissionGranted", true);
        boolean contactsPermissionGranted = sharedPreferences.getBoolean("contactsPermissionGranted", true);

        if (cameraPermissionGranted && audioPermissionGranted && locationPermissionGranted && contactsPermissionGranted) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDeviceRooted() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            process.waitFor();
            return true;
        } catch (Exception e) {

        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    public static boolean checkBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            return false;
        } else {
            if (bluetoothAdapter.isEnabled()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isMicrophoneAvailable() {
        int minBufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {

            AudioRecord audioRecord = new AudioRecord.Builder()
                    .setAudioSource(MediaRecorder.AudioSource.MIC)
                    .setAudioFormat(new AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(44100)
                            .setChannelMask(AudioFormat.CHANNEL_IN_MONO)
                            .build())
                    .setBufferSizeInBytes(minBufferSize)
                    .build();

            try {
                audioRecord.startRecording();
                int recordingState = audioRecord.getRecordingState();
                audioRecord.stop();
                audioRecord.release();
                return recordingState == AudioRecord.RECORDSTATE_RECORDING;
            } catch (Exception e) {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean isSpeakerAvailable(Context context) {
        int minBufferSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        AudioTrack audioTrack = new AudioTrack.Builder()
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build())
                .setAudioFormat(new AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(44100)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build())
                .setBufferSizeInBytes(minBufferSize)
                .build();

        try {
            audioTrack.play();
            int playbackState = audioTrack.getPlayState();
            if (playbackState == AudioTrack.PLAYSTATE_PLAYING) {

                for (int i = 0; i < 3; i++) {
                    playBeepSound(audioTrack);
                }
                audioTrack.stop();
                audioTrack.release();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private void playBeepSound(AudioTrack audioTrack) {
        int sampleRate = 44100;
        int duration = 1000; // Duration of the beep sound in milliseconds
        int numSamples = (int) (duration * sampleRate / 1000);
        double[] sample = new double[numSamples];
        short[] buffer = new short[numSamples];

        // Generate a simple beep waveform
        for (int i = 0; i < numSamples; i++) {
            sample[i] = Math.sin(2.0 * Math.PI * 1000 * i / sampleRate);
            buffer[i] = (short) (sample[i] * Short.MAX_VALUE);
        }

        audioTrack.write(buffer, 0, buffer.length);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (gyroscopeSensor != null) {
            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    public boolean isGpsAvailableAndEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);


        boolean isGpsAvailable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return isGpsAvailable;
    }
    public boolean isAccelerometerAvailable(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        return accelerometerSensor != null;
    }
}