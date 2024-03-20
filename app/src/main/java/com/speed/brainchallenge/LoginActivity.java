package com.speed.brainchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get Username and Password
        TextInputEditText username = findViewById(R.id.username);
        TextInputEditText password = findViewById(R.id.password);

        // Get login button and register button
        MaterialButton loginButton = findViewById(R.id.login_button);
        MaterialButton registerButton = findViewById(R.id.register_button);

        loginButton.setOnClickListener(v -> {
            // Get the username and password
            String usernameText = Objects.requireNonNull(username.getText()).toString();
            String passwordText = Objects.requireNonNull(password.getText()).toString();

            // Check if the username and password are correct
            if (usernameText.equals("admin") && passwordText.equals("admin")) {
                // If the username and password are correct, go to the main activity
                // startActivity(new Intent(this, MainActivity.class));
                // show login success message
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            } else {
                // If the username and password are incorrect, show an error message
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(v -> {
            // If the user clicks on the register button, go to the register activity
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}