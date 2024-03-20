package com.speed.brainchallenge;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get username, password, confirm password, and register button
        TextInputEditText username = findViewById(R.id.username_text);
        TextInputEditText password = findViewById(R.id.password_text);
        TextInputEditText confirmPassword = findViewById(R.id.confirm_password_text);
        MaterialButton registerButton = findViewById(R.id.register_button);

        // Set the on click listener for the register button
        registerButton.setOnClickListener(v -> {
            // Get the username, password, and confirm password
            String usernameText = Objects.requireNonNull(username.getText()).toString();
            String passwordText = Objects.requireNonNull(password.getText()).toString();
            String confirmPasswordText = Objects.requireNonNull(confirmPassword.getText()).toString();

            // Check if the username and password are valid
            if (usernameText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
                // If the username or password is empty, show an error message
                username.setError("Username cannot be empty");
                password.setError("Password cannot be empty");
                confirmPassword.setError("Confirm password cannot be empty");
            } else if (!passwordText.equals(confirmPasswordText)) {
                // If the password and confirm password do not match, show an error message
                confirmPassword.setError("Passwords do not match");
            } else {
                // If the username and password are valid, show a success message
                username.setError(null);
                password.setError(null);
                confirmPassword.setError(null);
                // show register success message
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}