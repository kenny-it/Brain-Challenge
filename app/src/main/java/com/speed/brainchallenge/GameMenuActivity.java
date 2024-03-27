package com.speed.brainchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class GameMenuActivity extends AppCompatActivity {

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

        MaterialCardView card_stage_1 = findViewById(R.id.card_stage_1);
        MaterialCardView card_stage_2 = findViewById(R.id.card_stage_2);
        MaterialCardView card_stage_3 = findViewById(R.id.card_stage_3);
        MaterialCardView card_stage_4 = findViewById(R.id.card_stage_4);
        MaterialCardView card_stage_5 = findViewById(R.id.card_stage_5);
        MaterialCardView card_stage_6 = findViewById(R.id.card_stage_6);
        MaterialCardView card_stage_7 = findViewById(R.id.card_stage_7);
        MaterialCardView card_stage_8 = findViewById(R.id.card_stage_8);


        card_stage_1.setOnLongClickListener(v -> {
            // go to stage 1
            //startActivity(new Intent(StageActivityOne.class,this));
            Toast.makeText(this, "Stage 1", Toast.LENGTH_SHORT).show();
            return true;
        });

        card_stage_2.setOnLongClickListener(v -> {
            // go to stage 2
            //startActivity(new Intent(StageActivityTwo.class,this));
            return true;
        });

        card_stage_3.setOnLongClickListener(v -> {
            // go to stage 3
            //startActivity(new Intent(StageActivityThree.class,this));
            return true;
        });

        card_stage_4.setOnLongClickListener(v -> {
            // go to stage 4
            //startActivity(new Intent(StageActivityFour.class,this));
            return true;
        });

        card_stage_5.setOnLongClickListener(v -> {
            // go to stage 5
            //startActivity(new Intent(StageActivityFive.class,this));
            return true;
        });

        card_stage_6.setOnLongClickListener(v -> {
            // go to stage 6
            //startActivity(new Intent(StageActivitySix.class,this));
            return true;
        });

        card_stage_7.setOnLongClickListener(v -> {
            // go to stage 7
            //startActivity(new Intent(StageActivitySeven.class,this));
            return true;
        });

        card_stage_8.setOnLongClickListener(v -> {
            // go to stage 8
            //startActivity(new Intent(StageActivityEight.class,this));
            return true;
        });

    }
}