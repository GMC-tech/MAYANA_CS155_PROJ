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

    private long break_millisInput;
    private long work_millisInput;

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

                String work_duration_input = inputWorkDuration.getText().toString();

                if (work_duration_input.length() == 0){
                    Toast.makeText(CountdownTimer.this, "Please enter desired duration in the minutes field", Toast.LENGTH_SHORT).show();
                    return;
                }

                work_millisInput = Long.parseLong(work_duration_input) * 60000;

                if (work_millisInput == 0) {
                    Toast.makeText(CountdownTimer.this, "Duration must be greater than '0'", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(CountdownTimer.this, "Work duration successfully set.", Toast.LENGTH_SHORT).show();
                }

                setTime(work_millisInput);
                inputWorkDuration.setText("");
                inputBreakDuration.setText("");
            }
        });

        setBreakDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String break_duration_input = inputBreakDuration.getText().toString();

                if (break_duration_input.length() == 00){
                    Toast.makeText(CountdownTimer.this, "Please enter desired duration in the minutes field", Toast.LENGTH_SHORT).show();
                    return;
                }

                break_millisInput = Long.parseLong(break_duration_input) * 60000;

                if (break_millisInput == 0) {
                    Toast.makeText(CountdownTimer.this, "Duration must be greater than '0'", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(CountdownTimer.this, "Break Duration successfully set.", Toast.LENGTH_SHORT).show();
                }
                setTime(break_millisInput);
                inputWorkDuration.setText("");
                inputBreakDuration.setText("");
            }
        });

        workButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mTimerRunning){
                    pauseTimer();
                }else{
                    workButton.setText("Pause Work");
                    breakButton.setText("Start Break");
                    startTimer();
                }
            }
        });

        breakButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mTimerRunning){
                    pauseTimer();
                }else{
                    workButton.setText("Start Work");
                    breakButton.setText("Pause Break");
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
        breakButton.setVisibility(View.VISIBLE);
        mButtonReset.setVisibility(View.VISIBLE);
    }
    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        workButton.setText("Start Working");
        breakButton.setText("Start Break");
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