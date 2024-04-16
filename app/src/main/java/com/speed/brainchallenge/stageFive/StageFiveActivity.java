package com.speed.brainchallenge.stageFive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.speed.brainchallenge.GameMenuActivity;
import com.speed.brainchallenge.MainActivity;
import com.speed.brainchallenge.R;
import com.speed.brainchallenge.stageSix.StageSixActivity;
import com.speed.brainchallenge.utils.Constant;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class StageFiveActivity extends AppCompatActivity implements ImageButton.OnClickListener, SensorEventListener {

    // username
    private String username;
    // text view to show the text of building HP
    private TextView buildingHP;
    // timer to count the score
    private Chronometer timer;
    // int that storge the HP of the building
    private int HP = 300;
    // sensor manager
    private SensorManager sensorManager;
    // accelerometer sensor
    private Sensor accelerometer;

    private boolean hasPrevHighScore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_five);

        // Get the username
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.USERS, MODE_PRIVATE);
        username = sharedPreferences.getString(Constant.CURRENTUSER, "");

        // Initialize views
        ImageView buildingImageView = new ImageView(this);
        buildingImageView = findViewById(R.id.building);
        buildingHP = findViewById(R.id.buildingHP);
        timer = findViewById(R.id.timer);

        // update the HP of the building
        updateHP();

        // set onclick listener for back button
        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(this);

        // Initialize timer
        initTimer(this);
        // start timer
        timer.start();

        // set OnClickListener for the building
        buildingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // when press the building reduce HP by 1
                reduceHP(1.0f);
            }
        });

        // initialize the SensorManager and accelerometer sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register the accelerometer sensor listener
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregister the accelerometer sensor listener
        sensorManager.unregisterListener(this);
    }

    // Initialize timer
    private void initTimer(Context context) {
        timer = findViewById(R.id.timer);
        // set the timer format to MM:SS not hh:mm:ss
        timer.setFormat("%s");
        // reset timer
        timer.setBase(SystemClock.elapsedRealtime());
        timer.stop();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            // go back to main menu
            Intent intent = new Intent(this, GameMenuActivity.class).putExtra(Constant.USERS, username);
            startActivity(intent);
        }
    }

    // reduce the HP of the building
    private void reduceHP(float amount) {
        HP -= amount;
        // the HP cannot be less then 0
        if (HP <= 0) {
            // if the HP is 0, player won
            HP = 0;
            // unregister the accelerometer sensor listener to avoid multi message and win method
            sensorManager.unregisterListener(this);
            // the player won call win method
            win();
        }
        // update the HP
        updateHP();
    }

    // update the HP of the building
    private void updateHP() {
        buildingHP.setText(String.valueOf(HP) + "/300");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // if the sensor type is accelerometer
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // retrieve acceleration values for x, y, z
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // calculate the amount of the acceleration
            double accelerationAmount = Math.sqrt(x * x + y * y + z * z);
            // define a threshold for acceleration that count as shake
            double threshold = 13.0;
            // if the amount of the acceleration exceed the threshold
            if (accelerationAmount > threshold) {
                // rduce the HP by 50
                reduceHP(50.0f);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // the player destoryed the building do..
    private void win() {
        // stop timer
        timer.stop();
        long time = (SystemClock.elapsedRealtime() - timer.getBase()) / 1000;
        // calculate the score
        int score = calculateScore(time);
        // save the records on sharePreference
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.STAGEFIVE + username, MODE_PRIVATE);
        // Check if there is a previous score and compare the score to save the highest score
        // if the score is same as previous score, then compare the time to save the lowest time
        int prevScore = sharedPreferences.getInt(Constant.SCORE, 0);
        long prevTime = sharedPreferences.getLong(Constant.TIME, 0);
        if (score < prevScore) {
            hasPrevHighScore = true;
        } else if (score == prevScore) {
            if (time > prevTime) {
                time = prevTime;
            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (hasPrevHighScore) {
            editor.putInt(Constant.SCORE, prevScore);
        } else {
            editor.putInt(Constant.SCORE, score);
        }
        editor.putLong(Constant.TIME, time);
        editor.apply();
        // show message to tell the result of the game and allow user to return
        ShowDialog(score);
    }

    // show win message
    private void ShowDialog(int score) {
        // Show dialog and go to next stage
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle("Congratulations");
        dialog.setMessage("You have completed the stage 5 with score " + score);
        dialog.setCancelable(false);
        dialog.setPositiveButton("Next", (dialog1, which) -> {
            // go to next stage
            Intent intent = new Intent(this, StageSixActivity.class).putExtra("username", username);
            startActivity(intent);
        });
        dialog.setNegativeButton("Back to Menu", (dialog1, which) -> {
            // go back to main menu
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        dialog.show();
    }

    // count the sore by time
    private int calculateScore(long time) {
        int score = 0;
        if (time <= 30) {
            score = 10;
        } else if (time <= 45) {
            score = 8;
        } else if (time <= 60) {
            score = 6;
        } else if (time <= 120) {
            score = 4;
        } else if (time <= 180) {
            score = 2;
        }
        return score;
    }
}
