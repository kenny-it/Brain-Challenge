package com.speed.brainchallenge;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.speed.brainchallenge.utils.Constant;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI(findViewById(R.id.parent));

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
            SharedPreferences users = getSharedPreferences(Constant.USERS, MODE_PRIVATE);
            String storedPassword = users.getString(usernameText, null);
            if (passwordText.equals(storedPassword)) {
                // If the username and password are valid, show a success message
                username.setError(null);
                password.setError(null);
                // show login success message
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                // go to the main activity and pass username to the main activity
                Intent intent = new Intent(this, MainActivity.class).putExtra("username", usernameText);
                startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();

        // Get Username and Password
        TextInputEditText username = findViewById(R.id.username);
        TextInputEditText password = findViewById(R.id.password);

        username.setError(null);
        password.setError(null);

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
                hideSoftKeyboard(LoginActivity.this);
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