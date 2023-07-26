package com.example.facedetectionusingfirebaseml;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class Result_dialog extends DialogFragment {
    Button okBtn;
    TextView resultTextview;
    @Nullable
    public View oncCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resultdialog, container, false);
        String resultText = "";

        okBtn = view.findViewById(R.id.result_ok);
        resultTextview = view.findViewById(R.id.result_text);

        Bundle bundle = getArguments();
        resultText = bundle.getString(LCOFaceDetection.RESULT_TEXT);
        resultTextview.setText(resultText);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
