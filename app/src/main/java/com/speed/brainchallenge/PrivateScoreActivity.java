package com.speed.brainchallenge;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.speed.brainchallenge.utils.Constant;

import java.util.Locale;

public class PrivateScoreActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_private_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the username from the intent
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.USERS, MODE_PRIVATE);
        username = sharedPreferences.getString(Constant.CURRENTUSER, "");
        
        // Set the back button click listener
        ImageButton backBtn = (ImageButton)findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> {
            // Start GameMenuActivity and pass the username
            finish();
        });

        // Set the username in the screen
       ((TextView) findViewById(R.id.tv_username)).setText("Username: " + username);

        // Set the stage 1 record in the screen
        setStageOneRecord();
        // Set the stage 2 record in the screen
        setStageTwoRecord();
        // Set the stage 3 record in the screen
        setStageThreeRecord();
        // Set the stage 4 record in the screen
        setStageFourRecord();
        // Set the stage 5 record in the screen
        setStageFiveRecord();
        // Set the stage 6 record in the screen
        setStageSixRecord();
        // Set the stage 7 record in the screen
        //setStageSevenRecord();
        // Set the stage 8 record in the screen
        //setStageEightRecord();


    }

    private void setStageOneRecord() {
        TextView tv_score = findViewById(R.id.game1Score);
        TextView tv_time = findViewById(R.id.game1Time);
        try {
            // Get the stage 1 record from the shared preferences
            SharedPreferences data = getSharedPreferences(Constant.STAGEONE + username, MODE_PRIVATE);
            int score = data.getInt(Constant.SCORE, 0);
            long time = data.getLong(Constant.TIME, 0);
            // Set the stage 1 record in the screen
            tv_score.setText("Score: " + score);
            long minutes = time / 60;
            long seconds = time % 60;
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            tv_time.setText("Time: " + timeFormatted);

        } catch (Exception e) {
            // Set the stage 1 record in the screen
            tv_score.setText("Score: 0");
            tv_time.setText("Time: 00:00");
        }
    }
    private void setStageTwoRecord() {
        TextView tv_score = findViewById(R.id.game2Score);
        TextView tv_time = findViewById(R.id.game2Time);
        try {
            // Get the stage 2 record from the shared preferences
            SharedPreferences data = getSharedPreferences(Constant.STAGETWO + username, MODE_PRIVATE);
            int score = data.getInt(Constant.SCORE, 0);
            long time = data.getLong(Constant.TIME, 0);
            // Set the stage 2 record in the screen
            tv_score.setText("Score: " + score);
            long minutes = time / 60;
            long seconds = time % 60;
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            tv_time.setText("Time: " + timeFormatted);

        } catch (Exception e) {
            // Set the stage 2 record in the screen
            tv_score.setText("Score: 0");
            tv_time.setText("Time: 00:00");
        }
    }

    private void setStageFourRecord() {
        TextView tv_score = findViewById(R.id.game4Score);
        TextView tv_time = findViewById(R.id.game4Time);
        try {
            // Get the stage 3 record from the shared preferences
            SharedPreferences data = getSharedPreferences(Constant.STAGEFOUR + username, MODE_PRIVATE);
            int score = data.getInt(Constant.SCORE, 0);
            long time = data.getLong(Constant.TIME, 0);
            // Set the stage 3 record in the screen
            tv_score.setText("Score: " + score);
            long minutes = time / 60;
            long seconds = time % 60;
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            tv_time.setText("Time: " + timeFormatted);

        } catch (Exception e) {
            // Set the stage 3 record in the screen
            tv_score.setText("Score: 0");
            tv_time.setText("Time: 00:00");
        }
    }

    private void setStageThreeRecord() {
        TextView tv_score = findViewById(R.id.game3Score);
        TextView tv_time = findViewById(R.id.game3Time);
        try {
            // Get the stage 3 record from the shared preferences
            SharedPreferences data = getSharedPreferences(Constant.STAGETHREE + username, MODE_PRIVATE);
            int score = data.getInt(Constant.SCORE, 0);
            long time = data.getLong(Constant.TIME, 0);
            // Set the stage 3 record in the screen
            tv_score.setText("Score: " + score);
            long minutes = time / 60;
            long seconds = time % 60;
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            tv_time.setText("Time: " + timeFormatted);

        } catch (Exception e) {
            // Set the stage 3 record in the screen
            tv_score.setText("Score: 0");
            tv_time.setText("Time: 00:00");
        }
    }
    
    private void setStageFiveRecord() {
        TextView tv_score = findViewById(R.id.game5Score);
        TextView tv_time = findViewById(R.id.game5Time);
        try {
            // Get the stage 5 record from the shared preferences
            SharedPreferences data = getSharedPreferences(Constant.STAGEFIVE + username, MODE_PRIVATE);
            int score = data.getInt(Constant.SCORE, 0);
            long time = data.getLong(Constant.TIME, 0);
            // Set the stage 5 record in the screen
            tv_score.setText("Score: " + score);
            long minutes = time / 60;
            long seconds = time % 60;
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            tv_time.setText("Time: " + timeFormatted);

        } catch (Exception e) {
            // Set the stage 5 record in the screen
            tv_score.setText("Score: 0");
            tv_time.setText("Time: 00:00");
        }
    }

    private void setStageSixRecord() {
        TextView tv_score = findViewById(R.id.game6Score);
        TextView tv_time = findViewById(R.id.game6Time);
        try {
            // Get the stage 6 record from the shared preferences
            SharedPreferences data = getSharedPreferences(Constant.STAGESIX + username, MODE_PRIVATE);
            int score = data.getInt(Constant.SCORE, 0);
            long time = data.getLong(Constant.TIME, 0);
            // Set the stage 6 record in the screen
            tv_score.setText("Score: " + score);
            long minutes = time / 60;
            long seconds = time % 60;
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            tv_time.setText("Time: " + timeFormatted);

        } catch (Exception e) {
            // Set the stage 6 record in the screen
            tv_score.setText("Score: 0");
            tv_time.setText("Time: 00:00");
        }
    }
}
