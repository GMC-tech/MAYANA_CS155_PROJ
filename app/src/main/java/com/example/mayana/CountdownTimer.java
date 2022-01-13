package com.example.mayana;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class CountdownTimer extends AppCompatActivity {

    private EditText inputWorkDuration;
    private EditText inputBreakDuration;

    private TextView mTextViewCountDown;

    private TextView workDurationLabel;
    private TextView breakDurationLabel;
    private ProgressBar progressBar;

    private Button setWorkingDuration;
    private Button setBreakDuration;

    private Button workButton;
    private Button breakButton;

    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartWorkTimeInMillis;
    private long mWorkTimeLeftInMillis = mStartWorkTimeInMillis;

    private long mStartBreakTimeInMillis;
    private long mBreakTimeLeftInMillis = mStartWorkTimeInMillis;

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
        progressBar = findViewById(R.id.progressBar2);

        setWorkingDuration = findViewById(R.id.button_set);
        setBreakDuration = findViewById(R.id.button_set_break);

        workButton = findViewById(R.id.button_start_pause);
        breakButton = findViewById(R.id.button_start_pause_break);

        mButtonReset = findViewById(R.id.button_reset);

        progressBar.setVisibility(View.INVISIBLE);

        setWorkingDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String work_duration_input = inputWorkDuration.getText().toString();

                if (work_duration_input.length() == 0){
                    Toast.makeText(CountdownTimer.this, "Please enter desired duration in the minutes field", Toast.LENGTH_SHORT).show();
                    return;
                }

                work_millisInput = Long.parseLong(work_duration_input) * 60000;
                validateDuration(work_millisInput);

                setWorkTime(work_millisInput);
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
                validateDuration(break_millisInput);

                setBreakTime(break_millisInput);
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
                    breakButton.setText("Resume Break");

                    mCountDownTimer = new CountDownTimer(mWorkTimeLeftInMillis, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mWorkTimeLeftInMillis = millisUntilFinished;
                            updateWorkCountDownText();
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
                    workDurationLabel.setVisibility(View.INVISIBLE);
                    breakDurationLabel.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    inputWorkDuration.setVisibility(View.INVISIBLE);
                    inputBreakDuration.setVisibility(View.INVISIBLE);
                    setWorkingDuration.setVisibility(View.INVISIBLE);
                    setBreakDuration.setVisibility(View.INVISIBLE);
                    breakButton.setVisibility(View.VISIBLE);
                    mButtonReset.setVisibility(View.VISIBLE);
                }
            }
        });

        breakButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mTimerRunning){
                    pauseTimer();
                }else{
                    //setBreakTime(break_millisInput);
                    workButton.setText("Resume Work");
                    breakButton.setText("Pause Break");

                    mCountDownTimer = new CountDownTimer(mBreakTimeLeftInMillis, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mBreakTimeLeftInMillis = millisUntilFinished;
                            updateBreakCountDownText();
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
                    workDurationLabel.setVisibility(View.INVISIBLE);
                    breakDurationLabel.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    inputWorkDuration.setVisibility(View.INVISIBLE);
                    inputBreakDuration.setVisibility(View.INVISIBLE);
                    setWorkingDuration.setVisibility(View.INVISIBLE);
                    setBreakDuration.setVisibility(View.INVISIBLE);
                    breakButton.setVisibility(View.VISIBLE);
                    mButtonReset.setVisibility(View.VISIBLE);
                }
            }
        });


        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateWorkCountDownText();
        updateBreakCountDownText();
    }

    private void validateDuration (long inputDuration){
        if (inputDuration == 0) {
            Toast.makeText(CountdownTimer.this, "Duration must be greater than '0'", Toast.LENGTH_SHORT).show();
            return;
        }else {
            Toast.makeText(CountdownTimer.this, "Duration successfully set.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setWorkTime(long work_milliseconds){
        mStartWorkTimeInMillis = work_milliseconds;
        resetTimer();
    }

    private void setBreakTime(long break_milliseconds){
        mStartBreakTimeInMillis = break_milliseconds;
        resetTimer();
    }

   /* private void startTimer(){
        mCountDownTimer = new CountDownTimer(mWorkTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mWorkTimeLeftInMillis = millisUntilFinished;
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
    }*/

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        workButton.setText("Resume Work");
        breakButton.setText("Start Break");
        breakButton.setVisibility(View.VISIBLE);
        mButtonReset.setVisibility(View.VISIBLE);
        workDurationLabel.setVisibility(View.VISIBLE);
        breakDurationLabel.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        inputWorkDuration.setVisibility(View.VISIBLE);
        inputBreakDuration.setVisibility(View.VISIBLE);
        setWorkingDuration.setVisibility(View.VISIBLE);
        setBreakDuration.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        mWorkTimeLeftInMillis = mStartWorkTimeInMillis;
        mBreakTimeLeftInMillis=mStartBreakTimeInMillis;
        updateWorkCountDownText();
        updateBreakCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        breakButton.setVisibility(View.INVISIBLE);
        workButton.setVisibility(View.VISIBLE);
    }

    private void updateWorkCountDownText(){
        int hours = (int) (mWorkTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mWorkTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mWorkTimeLeftInMillis / 1000) % 60;


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

    private void updateBreakCountDownText(){
        int hours = (int) (mBreakTimeLeftInMillis/ 1000) / 3600;
        int minutes = (int) ((mBreakTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mBreakTimeLeftInMillis / 1000) % 60;


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