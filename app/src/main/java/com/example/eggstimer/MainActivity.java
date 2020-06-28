package com.example.eggstimer;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerBar;
    TextView timerText;
    Button controllerButton;
    boolean counteractive = false;
    CountDownTimer countDownTimer;

    public void reset() {

        timerText.setText("0:30");
        timerBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Go!");
        timerBar.setEnabled(true);
        counteractive=false;

    }

    public void updatetimer (int secondsLeft) {

        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft - minutes*60;

        String seconding = Integer.toString(seconds);

        if (seconds<=9) seconding = "0" + seconding;

        timerText.setText(String.format("%s:%s", Integer.toString(minutes), seconding));

    }

    @SuppressLint("SetTextI18n")
    public void Timer (View view) {

        if(counteractive==false) {

            counteractive = true;
            timerBar.setEnabled(false);
            controllerButton.setText("Stop");

            countDownTimer = new CountDownTimer(timerBar.getProgress() * 1000 + 100, 1000) {


                @Override
                public void onTick(long millisUntilFinished) {

                    updatetimer((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {

                    reset();

                    int i;
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sniper);
                    mp.start();

                }
            }.start();
        } else {

            reset();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerBar = (SeekBar)findViewById(R.id.timerBar);
        timerText = (TextView)findViewById(R.id.timerText);
        controllerButton = (Button)findViewById(R.id.controllerButton);

        timerBar.setMax(600);
        timerBar.setProgress(30);

        timerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            updatetimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
