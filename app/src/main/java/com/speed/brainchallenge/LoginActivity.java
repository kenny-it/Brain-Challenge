package com.speed.brainchallenge;

import android.content.Intent;
import android.content.SharedPreferences;
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

            // Check if the username and password are valid
            if (usernameText.isEmpty() || passwordText.isEmpty()) {
                // If the username or password is empty, show an error message
                username.setError("Username cannot be empty");
                password.setError("Password cannot be empty");
                return;
            }

           //
            SharedPreferences users = getSharedPreferences("users", MODE_PRIVATE);
            String storedPassword = users.getString(usernameText, null);
            if (passwordText.equals(storedPassword)) {
                // If the username and password are valid, show a success message
                username.setError(null);
                password.setError(null);
                // show login success message
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                // go to the main activity
                startActivity(new Intent(this, MainActivity.class));
            } else {
                // If the username and password are invalid, show an error message
                username.setError("Invalid username");
                password.setError("Invalid password");

            }


        });

        registerButton.setOnClickListener(v -> {
            // If the user clicks on the register button, go to the register activity
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}