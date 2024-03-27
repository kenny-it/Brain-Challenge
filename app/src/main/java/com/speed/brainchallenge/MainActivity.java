package com.speed.brainchallenge;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialButton btnGameMenu = findViewById(R.id.btnGameMenu);
        MaterialButton btnPrivateScore = findViewById(R.id.btnPrivateScore);
        MaterialButton btnGlobalScore = findViewById(R.id.btnGlobalScore);

        btnGameMenu.setOnClickListener(v -> {
            // Start GameMenuActivity
            startActivity(new Intent(this, GameMenuActivity.class));
        });

        btnPrivateScore.setOnClickListener(v -> {
            // Start PrivateScoreActivity
            startActivity(new Intent(this, PrivateScoreActivity.class));
        });

        btnGlobalScore.setOnClickListener(v -> {
            // Start GlobalScoreActivity
            startActivity(new Intent(this, GlobalScoreActivity.class));
        });
    }
}