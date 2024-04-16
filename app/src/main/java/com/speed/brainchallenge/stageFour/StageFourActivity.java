package com.speed.brainchallenge.stageFour;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.speed.brainchallenge.GameMenuActivity;
import com.speed.brainchallenge.MainActivity;
import com.speed.brainchallenge.R;
import com.speed.brainchallenge.utils.Constant;

public class StageFourActivity extends AppCompatActivity implements ImageButton.OnClickListener, SensorEventListener {

    private String username;
    private Chronometer timer;
    private TextView tv_light;
    private SensorManager sensorManager;
    private Long previousTime = 0L;

    private Boolean isWin = null;

    private boolean hasPrevHighScore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stage_four);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the username
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.USERS, MODE_PRIVATE);
        username = sharedPreferences.getString(Constant.CURRENTUSER, "");
        // set onclick listener for back button
        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(this);

        initTimer(this);
        setSwitchColor();
        // start timer
        timer.start();

        // light sensor
        tv_light = findViewById(R.id.tv_light);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    private void setSwitchColor() {
        // set switch button color
        MaterialSwitch lightSwitch = findViewById(R.id.lightSwitch);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        Color.parseColor("#D3D3D3"),
                        Color.parseColor("#FFD700")
                }
        );
        lightSwitch.setTrackTintList(colorStateList);
    }

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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float light = event.values[0];
            tv_light.setText(getString(R.string.light_intensity) + light);
            // if light is less than 20 more then 5 seconds, then win the game
            if (light <= 20 && previousTime == 0) {
                previousTime = SystemClock.elapsedRealtime();
            } else if (light > 20) {
                previousTime = SystemClock.elapsedRealtime();
            } else if (light <= 20 && SystemClock.elapsedRealtime() - previousTime > 5000) {
                if (isWin == null) {
                    isWin = true;
                    // win the game
                    win();
                }

            }
        }
    }

    private void win() {
        // stop timer
        timer.stop();
        // set the background color to black
        findViewById(R.id.main).setBackgroundColor(Color.BLACK);
        long time = (SystemClock.elapsedRealtime() - timer.getBase()) / 1000;
        // calculate the score
        int score = calculateScore(time);
        // save the records on sharePreference
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.STAGEFOUR + username, MODE_PRIVATE);
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

        ShowDialog(score);
    }

    private void ShowDialog(int score) {
        // Show dialog and go to next stage
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle("Congratulations");
        dialog.setCancelable(false);
        dialog.setMessage("You have completed the stage 4 with score " + score);
        dialog.setPositiveButton("Next", (dialog1, which) -> {
            // go to next stage
            //Intent intent = new Intent(this, StageFiveActivity.class).putExtra("username", username);
            //startActivity(intent);
        });
        dialog.setNegativeButton("Back to Menu", (dialog1, which) -> {
            // go back to main menu
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        dialog.show();
    }

    private int calculateScore(long time) {

        int score = 2;
        // get 5 score if the time is within 5 minutes
        if (time <= 300) {
            score = 5;
        }
        // get 8 score if the time is within 2 minutes
        if (time <= 120) {
            score = 8;
        }
        // get 10 score if the time is within 1 minute
        if (time <= 60) {
            score = 10;
        }

        return score;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
