package com.speed.brainchallenge.stageTwo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.speed.brainchallenge.GameMenuActivity;
import com.speed.brainchallenge.MainActivity;
import com.speed.brainchallenge.R;
import com.speed.brainchallenge.stageFour.StageFourActivity;
import com.speed.brainchallenge.stagethree.StageThreeActivity;
import com.speed.brainchallenge.utils.Constant;

public class StageTwoActivity extends AppCompatActivity implements View.OnClickListener {
    private String username;
    private ImageView blackHat, blueCapOne, blueCapTwo, blueCapThree, magicHat;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Chronometer timer;
    private boolean coinsFound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stage_two);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        blackHat = findViewById(R.id.hat_one);
        blueCapOne = findViewById(R.id.cap_one);
        blueCapTwo = findViewById(R.id.cap_two);
        blueCapThree = findViewById(R.id.cap_three);

        setHatTouchListener(blackHat);
        setHatTouchListener(blueCapOne);
        setHatTouchListener(blueCapTwo);
        setHatTouchListener(blueCapThree);

        magicHat = findViewById(R.id.hat_special);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(this);
        // Get the username
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.USERS, MODE_PRIVATE);
        username = sharedPreferences.getString(Constant.CURRENTUSER, "");

        initTimer(this);
        timer.start();
    }
    private void initTimer(Context context) {
        timer = findViewById(R.id.timer);

        // set the timer format to MM:SS not hh:mm:ss
        timer.setFormat("%s");
        // reset timer
        timer.setBase(SystemClock.elapsedRealtime());
        timer.stop();
    }
    @SuppressLint("ClickableViewAccessibility")
    private void setHatTouchListener(final ImageView hatImageView) {
        hatImageView.setOnTouchListener(new View.OnTouchListener() {
            private float startY;
            private float initialY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        initialY = v.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float deltaY = event.getY() - startY;
                        if (deltaY < 0) {
                            v.setY(initialY + deltaY);
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (v.getY() < initialY - v.getHeight() / 2) {
                            // Hat is moved upward, make it disappear
                            v.setVisibility(View.INVISIBLE);
                            coinsFound();
                        }
                        else {
                            v.setY(initialY);
                        }

                        return true;
                }
                return false;
            }
        });
    }
    private final SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent!=null){
                float x_accelerometer = sensorEvent.values[0];
                float y_accelerometer = sensorEvent.values[1];
                float z_accelerometer = sensorEvent.values[2];

                if(x_accelerometer > 15 || x_accelerometer < -15 ||
                        y_accelerometer > 15 || y_accelerometer < -15 ||
                        z_accelerometer > 15 || z_accelerometer < -15) {
                    magicHat.setVisibility(View.INVISIBLE);
                    coinsFound();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void coinsFound() {
        if (coinsFound) {
            // Coins have already been found, skip further execution
            return;
        }
        if (blackHat.getVisibility() == View.INVISIBLE && blueCapOne.getVisibility() == View.INVISIBLE &&
                blueCapTwo.getVisibility() == View.INVISIBLE && blueCapThree.getVisibility() == View.INVISIBLE &&
                magicHat.getVisibility() == View.INVISIBLE) {
            // All coins found
            timer.stop();
            Toast.makeText(this, "You find all the coins!", Toast.LENGTH_SHORT).show();

            long time = (SystemClock.elapsedRealtime() - timer.getBase()) / 1000;

            // calculate score
            int score = calculateScore(time);
            // Save the records on sharepreferences
            SharedPreferences sharedPreferences = getSharedPreferences(Constant.STAGETWO + username, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(Constant.SCORE, score);
            editor.putLong(Constant.TIME, time);
            editor.apply();

            // Show dialog and go to next stage
            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
            dialog.setTitle("Congratulations");
            dialog.setMessage("You have completed the stage 2 with score " + score);
            dialog.setPositiveButton("Next", (dialog1, which) -> {
                Intent intent = new Intent(this, StageThreeActivity.class);
                startActivity(intent);
            });
            dialog.setNegativeButton("Back to Menu", (dialog1, which) -> {
                // go back to main menu
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
            dialog.show();

            coinsFound = true;
        }
    }

    private int calculateScore(long time) {
        int score = 0;
        // get 5 score if the time is within 5 minutes
        if (time <= 300) {
            score = 5;
        }
        // get 8 score if the time is within 3 minutes
        if (time <= 180) {
            score = 8;
        }
        // get 10 score if the time is within 1 minute
        if (time <= 60) {
            score = 10;
        }

        return score;
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            // go back to main menu
            Intent intent = new Intent(this, GameMenuActivity.class).putExtra(Constant.USERS, username);
            startActivity(intent);
        }
    }
}