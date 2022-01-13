package com.example.mayana;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class CountdownTimer extends AppCompatActivity {

    private EditText inputWorkDuration;
    private EditText inputBreakDuration;
    private TextView mTextViewCountDown;
    private TextView workDurationLabel;
    private TextView breakDurationLabel;
    private Button setWorkingDuration;
    private Button setBreakDuration;
    private Button workButton;
    private Button breakButton;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis = mStartTimeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        inputWorkDuration = findViewById(R.id.edit_text_input);
        inputBreakDuration = findViewById(R.id.edit_text_break);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        workDurationLabel = findViewById(R.id.textView_work_label);
        breakDurationLabel = findViewById(R.id.textView_break_label);
        setWorkingDuration = findViewById(R.id.button_set);
        setBreakDuration = findViewById(R.id.button_set_break);
        workButton = findViewById(R.id.button_start_pause);
        breakButton = findViewById(R.id.button_start_pause_break);
        mButtonReset = findViewById(R.id.button_reset);

        setWorkingDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputWorkDuration.getText().toString();
                if (input.length() == 0){
                    Toast.makeText(CountdownTimer.this, "Please enter desired duration in the minutes field", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(CountdownTimer.this, "Duration must be greater than '0'", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                inputWorkDuration.setText("");
            }
        });

        workButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning){
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateCountDownText();
    }

    private void setTime(long milliseconds){
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                workButton.setText("Start Working");
                workButton.setVisibility(View.INVISIBLE);
                breakButton.setVisibility(View.VISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        inputWorkDuration.setVisibility(View.INVISIBLE);
        inputBreakDuration.setVisibility(View.INVISIBLE);
        setWorkingDuration.setVisibility(View.INVISIBLE);
        setBreakDuration.setVisibility(View.INVISIBLE);
        workButton.setText("Pause Work");
        breakButton.setVisibility(View.INVISIBLE);
        mButtonReset.setVisibility(View.INVISIBLE);
    }
    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        workButton.setText("Start Working");
        breakButton.setVisibility(View.VISIBLE);
        mButtonReset.setVisibility(View.VISIBLE);
        inputWorkDuration.setVisibility(View.VISIBLE);
        inputBreakDuration.setVisibility(View.VISIBLE);
        setWorkingDuration.setVisibility(View.VISIBLE);
        setBreakDuration.setVisibility(View.VISIBLE);
    }
    private void resetTimer(){
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        breakButton.setVisibility(View.INVISIBLE);
        workButton.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText(){
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0){
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d" ,hours,  minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d" , minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}