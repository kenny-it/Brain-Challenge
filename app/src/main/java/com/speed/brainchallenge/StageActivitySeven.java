package com.speed.brainchallenge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.speed.brainchallenge.utils.Constant;

public class StageActivitySeven extends AppCompatActivity {

    private String username;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_seven);
        Button startbtn = findViewById(R.id.startbtn);
        Button returnbtn = findViewById(R.id.returnbtn);



        SharedPreferences sharedPreferences = getSharedPreferences(Constant.USERS, MODE_PRIVATE);
        username = sharedPreferences.getString(Constant.CURRENTUSER, "");


        startbtn.setOnClickListener(v -> {
            Intent intent = new Intent(StageActivitySeven.this,SevenGameActivity.class);
            startActivity(intent);
        });
        returnbtn.setOnClickListener(v -> {
            Intent intent = new Intent(StageActivitySeven.this,GameMenuActivity.class);
            startActivity(intent);
        });
        TextView highScoreTxt = findViewById(R.id.score);

        SharedPreferences prefs = getSharedPreferences(Constant.SEVENGAMESCORE + username,MODE_PRIVATE);
        highScoreTxt.setText("YOU Highest score "+ prefs.getInt("highscore",0));



    }
}
