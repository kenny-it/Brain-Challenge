package com.speed.brainchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.speed.brainchallenge.stageFour.StageFourActivity;
import com.speed.brainchallenge.stagethree.StageThreeActivity;
import com.speed.brainchallenge.utils.Constant;

public class GameMenuActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the username from the intent
        username = getIntent().getStringExtra(Constant.USERS);

        // set onclick listener for back button
        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> {
            // go back to the main activity
            Intent intent = new Intent(this, MainActivity.class).putExtra(Constant.USERS, username);
            startActivity(intent);
        });

        MaterialCardView card_stage_1 = findViewById(R.id.card_stage_1);
        MaterialCardView card_stage_2 = findViewById(R.id.card_stage_2);
        MaterialCardView card_stage_3 = findViewById(R.id.card_stage_3);
        MaterialCardView card_stage_4 = findViewById(R.id.card_stage_4);
        MaterialCardView card_stage_5 = findViewById(R.id.card_stage_5);
        MaterialCardView card_stage_6 = findViewById(R.id.card_stage_6);
        MaterialCardView card_stage_7 = findViewById(R.id.card_stage_7);
        MaterialCardView card_stage_8 = findViewById(R.id.card_stage_8);


        card_stage_1.setOnClickListener(v -> {
            // go to stage 1 and pass the username
            // Intent intent = new Intent(this, StageActivityOne.class).putExtra("username", username);
            //startActivity(intent);
            Toast.makeText(this, "Stage 1", Toast.LENGTH_SHORT).show();
        });

        card_stage_2.setOnClickListener(v -> {
            // go to stage 2
            // Intent intent = new Intent(this, StageActivityTwo.class).putExtra("username", username);
            //startActivity(intent);
        });

        card_stage_3.setOnClickListener(v -> {
            // go to stage 3
            Intent intent = new Intent(this, StageThreeActivity.class).putExtra(Constant.USERS, username);
            startActivity(intent);
        });

        card_stage_4.setOnClickListener(v -> {
            // go to stage 4
           Intent intent = new Intent(this, StageFourActivity.class).putExtra(Constant.USERS, username);
            startActivity(intent);
        });

        card_stage_5.setOnClickListener(v -> {
            // go to stage 5
            // Intent intent = new Intent(this, StageActivityFive.class).putExtra(Constant.USERS, username);
            //startActivity(intent);
        });

        card_stage_6.setOnClickListener(v -> {
            // go to stage 6
            // Intent intent = new Intent(this, StageActivitySix.class).putExtra(Constant.USERS, username);
            //startActivity(intent);
        });

        card_stage_7.setOnClickListener(v -> {
            // go to stage 7
            // Intent intent = new Intent(this, StageActivitySeven.class).putExtra(Constant.USERS, username);
            //startActivity(intent);
        });

        card_stage_8.setOnClickListener(v -> {
            // go to stage 8
            // Intent intent = new Intent(this, StageActivityEight.class).putExtra(Constant.USERS, username);
            //startActivity(intent);

        });

    }
}