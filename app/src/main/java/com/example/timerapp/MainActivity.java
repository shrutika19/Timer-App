package com.example.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView countDownText;
    SeekBar timerSeekBar;
    boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void resetTimer(){

        countDownText.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("Go");
        counterIsActive = false;
    }

    public void buttonClicked(View view){

        if(counterIsActive){

            resetTimer();

        }else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP");

             countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    Log.i("Finished", "Done");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mlgairhorn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft){

        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondsString = Integer.toString(seconds);

        if(seconds <= 9){
            secondsString = "0" +secondsString;
        }

        countDownText.setText(Integer.toString(minutes) + " : " +secondsString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        countDownText = findViewById(R.id.countDownText);
        goButton = findViewById(R.id.goButton);

        timerSeekBar.setMax(600);   //For 10 min //600 second = 10 min
        timerSeekBar.setProgress(30); //for 30 seconds

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}