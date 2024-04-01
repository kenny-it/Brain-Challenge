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

    private String username;

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

        // Get the username from the intent
        username = getIntent().getStringExtra("username");

        MaterialButton btnGameMenu = findViewById(R.id.btnGameMenu);
        MaterialButton btnPrivateScore = findViewById(R.id.btnPrivateScore);
        MaterialButton btnGlobalScore = findViewById(R.id.btnGlobalScore);

        btnGameMenu.setOnClickListener(v -> {
            // Start GameMenuActivity and pass the username
            Intent intent = new Intent(this, GameMenuActivity.class).putExtra("username", username);
            startActivity(intent);
        });

        btnPrivateScore.setOnClickListener(v -> {
            // Start PrivateScoreActivity and pass the username
            Intent intent = new Intent(this, PrivateScoreActivity.class).putExtra("username", username);
            startActivity(intent);
        });

        btnGlobalScore.setOnClickListener(v -> {
            // Start GlobalScoreActivity and pass the username
            Intent intent = new Intent(this, GlobalScoreActivity.class).putExtra("username", username);
            startActivity(intent);
        });
    }
}