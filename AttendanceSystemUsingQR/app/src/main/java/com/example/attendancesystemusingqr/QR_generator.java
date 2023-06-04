package com.example.attendancesystemusingqr;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QR_generator extends AppCompatActivity {

    Button genertor_qr;
    String value;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    ImageView iv_output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generator);

        // Toolbar Styling and Back Button
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button_foreground);

        // QR Generator
        genertor_qr = findViewById(R.id.generate);
        iv_output = findViewById(R.id.qrCode);

        genertor_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = "Attendance Marked Successfully";

                // Initialize multi format writer
//                MultiFormatWriter writer = new MultiFormatWriter();
//                try {
//                    BitMatrix bitMatrix = writer.encode(value, BarcodeFormat.QR_CODE,200,200);
//                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//                    iv_output.setImageBitmap(bitmap);
//                } catch (WriterException e) {
//                    e.printStackTrace();
//                }
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                // initializing a variable for default display.
                Display display = manager.getDefaultDisplay();

                // creating a variable for point which
                // is to be displayed in QR Code.
                Point point = new Point();
                display.getSize(point);

                // getting width and
                // height of a point
                int width = point.x;
                int height = point.y;

                // generating dimension from width and height.
                int dimen = width < height ? width : height;
                dimen = dimen * 3 / 4;

                // setting this dimensions inside our qr code
                // encoder to generate our qr code.
                qrgEncoder = new QRGEncoder(value, null, QRGContents.Type.TEXT, dimen);
                try {
                    // getting our qrcode in the form of bitmap.
                    bitmap = qrgEncoder.encodeAsBitmap();
                    // the bitmap is set inside our image
                    // view using .setimagebitmap method.
                    iv_output.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    // this method is called for
                    // exception handling.
                    Log.e("Tag", e.toString());
                }
            }
        });

    }
    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 350, 350, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }
}