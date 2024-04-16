package com.speed.brainchallenge.stageOne;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.speed.brainchallenge.stageTwo.StageTwoActivity;
import com.speed.brainchallenge.stagethree.StageThreeActivity;
import com.speed.brainchallenge.utils.Constant;

public class StageOneActivity extends AppCompatActivity implements View.OnClickListener {
    private String username;
    private ImageView greyDragon, blueDragonOne, blueDragonTwo;
    private ImageView circleLeftHorn, circleRightHorn, circleLeftTeeth, circleRightTeeth, circleLeftTail, circleRightTail;
    private CountDownTimer touchTimer;
    private Chronometer timer;
    private boolean diffSpot = false;

    private boolean hasPrevHighScore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stage_one);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        greyDragon = findViewById(R.id.grey_dragon);
        blueDragonOne = findViewById(R.id.left_blue_dragon);
        blueDragonTwo = findViewById(R.id.right_blue_dragon);

        circleLeftHorn = findViewById(R.id.circle_left_horn);
        circleRightHorn = findViewById(R.id.circle_right_horn);
        circleLeftTeeth = findViewById(R.id.circle_left_teeth);
        circleRightTeeth = findViewById(R.id.circle_right_teeth);
        circleLeftTail = findViewById(R.id.circle_left_tail);
        circleRightTail = findViewById(R.id.circle_right_tail);
        circleLeftHorn.setVisibility(View.INVISIBLE);
        circleRightHorn.setVisibility(View.INVISIBLE);
        circleLeftTeeth.setVisibility(View.INVISIBLE);
        circleRightTeeth.setVisibility(View.INVISIBLE);
        circleLeftTail.setVisibility(View.INVISIBLE);
        circleRightTail.setVisibility(View.INVISIBLE);

        touchEye(greyDragon);

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
    private void touchEye(final ImageView imageView) {
        final Rect eye = new Rect(400, 325, 430, 370);
        final long requiredDuration = 5000; // 5 seconds in milliseconds

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (eye.contains((int) x, (int) y)) {

                        touchTimer = new CountDownTimer(requiredDuration, requiredDuration) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // No action needed on each tick
                            }

                            @Override
                            public void onFinish() {
                                greyDragon.setVisibility(View.INVISIBLE);
                            }
                        };
                        touchTimer.start();
                    }
                }

                else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {

                    if (touchTimer != null) {
                        touchTimer.cancel();
                    }
                }

                spotDifferences(blueDragonOne);
                spotDifferences(blueDragonTwo);
                return true;
            }
        });
    }
    @SuppressLint("ClickableViewAccessibility")
    private void spotDifferences(final ImageView imageView) {
        final Rect rightHorn = new Rect(420, 225, 440, 310);
        final Rect rightTeeth = new Rect(450, 515, 480, 545);
        final Rect rightTail = new Rect(530, 970, 560, 1010);
        final Rect leftHorn = new Rect(420, 225, 440, 310);
        final Rect leftTeeth = new Rect(450, 515, 480, 545);
        final Rect leftTail = new Rect(35, 970, 75, 1010);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                if (rightHorn.contains((int) x, (int) y) || leftHorn.contains((int) x, (int) y) ) {
                    circleLeftHorn.setVisibility(View.VISIBLE);
                    circleRightHorn.setVisibility(View.VISIBLE);
                }
                if (rightTeeth.contains((int) x, (int) y) || leftTeeth.contains((int) x, (int) y)){
                    circleLeftTeeth.setVisibility(View.VISIBLE);
                    circleRightTeeth.setVisibility(View.VISIBLE);
                }
                if (rightTail.contains((int) x, (int) y) || leftTail.contains((int) x, (int) y)){
                    circleLeftTail.setVisibility(View.VISIBLE);
                    circleRightTail.setVisibility(View.VISIBLE);
                }
                allDiffSpot();
                return true;

            }
        });
    }

    private void allDiffSpot() {
        if (diffSpot) {
            // Coins have already been found, skip further execution
            return;
        }
        if (circleLeftHorn.getVisibility() == View.VISIBLE && circleRightHorn.getVisibility() == View.VISIBLE &&
                circleLeftTeeth.getVisibility() == View.VISIBLE && circleRightTeeth.getVisibility() == View.VISIBLE &&
                circleLeftTail.getVisibility() == View.VISIBLE && circleRightTail.getVisibility() == View.VISIBLE) {
            // All differences found
            timer.stop();
            Toast.makeText(this, "You spot all the differences!", Toast.LENGTH_SHORT).show();

            long time = (SystemClock.elapsedRealtime() - timer.getBase()) / 1000;

            // calculate score
            int score = calculateScore(time);
            // Save the records on sharepreferences
            SharedPreferences sharedPreferences = getSharedPreferences(Constant.STAGEONE + username, MODE_PRIVATE);
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

            // Show dialog and go to next stage
            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
            dialog.setTitle("Congratulations");

            dialog.setMessage("You have completed the stage 1 with score " + score);
            dialog.setCancelable(false);
            dialog.setPositiveButton("Next", (dialog1, which) -> {
                Intent intent = new Intent(this, StageTwoActivity.class);
                startActivity(intent);
            });
            dialog.setNegativeButton("Back to Menu", (dialog1, which) -> {
                // go back to main menu
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
            dialog.show();

            diffSpot = true;
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
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            // go back to main menu
            Intent intent = new Intent(this, GameMenuActivity.class).putExtra(Constant.USERS, username);
            startActivity(intent);
        }
    }
}