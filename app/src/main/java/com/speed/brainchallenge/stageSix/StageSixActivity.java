package com.speed.brainchallenge.stageSix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.speed.brainchallenge.GameMenuActivity;
import com.speed.brainchallenge.MainActivity;
import com.speed.brainchallenge.R;
import com.speed.brainchallenge.StageActivitySeven;
import com.speed.brainchallenge.utils.Constant;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class StageSixActivity extends AppCompatActivity implements ImageButton.OnClickListener, View.OnTouchListener {

    // username
    private String username;
    // timer to count the score
    private Chronometer timer;
    // int for player to answer the number of grape
    private int grapeCount ;
    // text view for player to answer the number of grape
    private TextView grapeNumberTextView;
    // int array of all the grape
    private ImageView[] grapeImages;
    // id of each grape
    private int[] grapeImageIds = {
            R.id.grapeOne,
            R.id.grapeTwo,
            R.id.grapeThree,
            R.id.grapeFour,
            R.id.grapeFive,
            R.id.grapeSix,
            R.id.grapeSeven,
            R.id.grapeEight,
            R.id.grapeNine,
            R.id.grapeTen,
            R.id.grapeEleven
    };
    // int to count the wrong answer of user, for score count
    private int missAnswer;
    // int for count the values for moving of x
    private int xDelta;
    // int for count the values for moving of y
    private int yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_six);

        // Get the username
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.USERS, MODE_PRIVATE);
        username = sharedPreferences.getString(Constant.CURRENTUSER, "");

        // Initialize views
        grapeNumberTextView = findViewById(R.id.grapeNumberTextView);
        timer = findViewById(R.id.timer);

        // back button
        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(this);

        // add button
        ImageButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        // subtract button
        ImageButton subButton = findViewById(R.id.subButton);
        subButton.setOnClickListener(this);

        // answer button
        Button answerButton = findViewById(R.id.answerButton);
        answerButton.setOnClickListener(this);

        // the number of grape answer by player
        grapeCount = 0;

        // Initialize views for each grape
        grapeImages = new ImageView[grapeImageIds.length];
        for (int i = 0; i < grapeImageIds.length; i++) {
            grapeImages[i] = findViewById(grapeImageIds[i]);
            grapeImages[i].setOnTouchListener(this);
        }

        // Initialize timer
        initTimer(this);
        // start timer
        timer.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            // go back to main menu
            Intent intent = new Intent(this, GameMenuActivity.class).putExtra(Constant.USERS, username);
            startActivity(intent);
        } else if (v.getId() == R.id.addButton) {
            // the range for player to answer is 0 to 20
            // add the text view for user to answer by 1
            if (grapeCount < 20) {
                grapeCount++;
                // update the number of grape for the text view
                updateGrapeNumber();
            }
        } else if (v.getId() == R.id.subButton) {
            // subtract the text view for user to answer by 1
            if (grapeCount > 0) {
                grapeCount--;
                // update the number of grape for the text view
                updateGrapeNumber();
            }
        } else if (v.getId() == R.id.answerButton) {
            // Expect answer of the grape
            int grapeCountAnswer = 11;
            // if the answer is correct
            if (grapeCount == grapeCountAnswer) {
                // win
                win();
            } else {
                // miss answer plus one
                missAnswer += 1;
                // message to tell players that they are wrong
                Toast.makeText(this, "Wrong answer!", Toast.LENGTH_LONG).show();
            }
        }
    }

    // if player touch the grape do..
    public boolean onTouch(View view, MotionEvent event) {
        // Get the raw X and Y of the touch event
        final int x = (int) event.getRawX();
        final int y = (int) event.getRawY();
        switch (event.getAction()) {
            // when player touch down the image
            case MotionEvent.ACTION_DOWN:
                // get the layout parameters of the view
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                        view.getLayoutParams();
                // calculate the delta values
                xDelta = x - lParams.leftMargin;
                yDelta = y - lParams.topMargin;
                break;
            // When the user moves the image
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                // Update the left and top margins of the view
                layoutParams.leftMargin = x - xDelta;
                layoutParams.topMargin = y - yDelta;
                // apply the new position to the view
                view.setLayoutParams(layoutParams);
                break;
        }
        return true;
    }

    // update the text view for player to answer the number of grape
    public void updateGrapeNumber() {
        grapeNumberTextView.setText(String.valueOf(grapeCount));
    }

    // // Initialize timer
    private void initTimer(Context context) {
        timer = findViewById(R.id.timer);
        // set the timer format to MM:SS not hh:mm:ss
        timer.setFormat("%s");
        // reset timer
        timer.setBase(SystemClock.elapsedRealtime());
        timer.stop();
    }

    // the player answer correct do..
    private void win() {
        // stop timer
        timer.stop();
        // set the background color to black
        findViewById(R.id.main).setBackgroundColor(Color.BLACK);
        long time = (SystemClock.elapsedRealtime() - timer.getBase()) / 1000;
        // calculate the score
        int score = calculateScore(time);
        // reduce the score by number of miss answer
        score -= missAnswer;
        // save the records on sharePreference
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.STAGESIX + username, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constant.SCORE, score);
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
        dialog.setMessage("You have completed the stage 6 with score " + score);
        dialog.setCancelable(false);
        dialog.setPositiveButton("Next", (dialog1, which) -> {
            // go to next stage
            Intent intent = new Intent(this, StageActivitySeven.class).putExtra("username", username);
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
        if (time <= 60) {
            score = 10;
        } else if (time <= 90) {
            score = 8;
        } else if (time <= 120) {
            score = 6;
        } else if (time <= 180) {
            score = 4;
        } else if (time <= 240) {
            score = 2;
        }
        return score;
    }
}
