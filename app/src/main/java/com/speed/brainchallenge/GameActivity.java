package com.speed.brainchallenge;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {
    private SevenGameView sevenGameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //get the size of the screen
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        sevenGameView = new SevenGameView(this, point.x, point.y);
        setContentView(sevenGameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sevenGameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sevenGameView.resume();
    }
}
