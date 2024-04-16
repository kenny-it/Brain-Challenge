package com.speed.brainchallenge;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StageActivitySeven extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_seven);
        Button startbtn = findViewById(R.id.startbtn);
        startbtn.setOnClickListener(v -> {
            Intent intent = new Intent(StageActivitySeven.this,SevenGameActivity.class);
            startActivity(intent);
        });
    }
}
