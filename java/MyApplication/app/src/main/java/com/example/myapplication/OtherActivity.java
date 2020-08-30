package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OtherActivity extends AppCompatActivity {

    private SimpleDateFormat mTimeFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.US);
    private TextView mOutputTextView;
    private Button mWorkButton;
    private Button mResetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        mOutputTextView = (TextView) findViewById(R.id.view_output);
        mWorkButton = (Button) findViewById(R.id.view_work);
        mResetButton = (Button) findViewById(R.id.view_reset);

        mWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOutputTextView.setText(mTimeFormat.format(Calendar.getInstance().getTime()));
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOutputTextView.setText("No output");
            }
        });
    }
}