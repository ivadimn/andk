package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.TextValueSanitizer;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final int TICK_DELAY_MILLIS = 250;
    private SimpleDateFormat mTimeFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.US);
    private Handler mHandler = new Handler();
    private TextView mTimeTextView;
    private Runnable mTickRountine = new Runnable() {
        @Override
        public void run() {
            mTimeTextView.setText(mTimeFormat.format(Calendar.getInstance().getTime()));
            mHandler.postDelayed(this, TICK_DELAY_MILLIS);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimeTextView = (TextView) findViewById(R.id.view_time);
        mTickRountine.run();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mTickRountine);
        mTimeTextView = null;
    }
}