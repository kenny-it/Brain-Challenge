package com.speed.brainchallenge;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.speed.brainchallenge.utils.Constant;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUI(findViewById(R.id.parent_register));

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
                return;
            }

            if (!passwordText.equals(confirmPasswordText)) {
                // If the password and confirm password do not match, show an error message
                confirmPassword.setError("Passwords do not match");
                return;
            }


            sharedPreferences = getSharedPreferences(Constant.USERS, MODE_PRIVATE);
            editor = sharedPreferences.edit();

            // Check if the username already exists
            if (sharedPreferences.contains(usernameText)) {
                // If the username already exists, show an error message
                username.setError("Username already exists");
                return;
            }

                // If the username and password are valid, show a success message
                username.setError(null);
                password.setError(null);
                confirmPassword.setError(null);
                // show register success message
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                // store the username and password in shared preferences

                // put the username and password in shared preferences
                editor.putString(usernameText,passwordText);
                editor.apply();
                // go to the login activity
                finish();

        });

        // Set the on click listener for the back button
        // set onclick listener for back button
        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> {
            // go to the login activity
            finish();
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                hideSoftKeyboard(RegisterActivity.this);
                return false;
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}