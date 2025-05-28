package com.lynch.schoolschedule.UI;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.lynch.schoolschedule.R;
import com.lynch.schoolschedule.Users.User;
import com.lynch.schoolschedule.helper.UserManager;
import com.lynch.schoolschedule.helper.UserManager.AuthCallback;
import com.lynch.schoolschedule.helper.UserManager.UserCallback;

public class LoginRegisterActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button actionButton;
    private TextView switchModeButton;
    private boolean isLoginMode = true;

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        actionButton = findViewById(R.id.actionButton);
        switchModeButton = findViewById(R.id.toggleText);

        userManager = new UserManager(this);

        actionButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (isLoginMode) {
                handleLogin(username, password);
            } else {
                handleRegister(username, password);
            }
        });

        switchModeButton.setOnClickListener(v -> {
            isLoginMode = !isLoginMode;
            updateMode();
        });

        updateMode();
    }

    private void updateMode() {
        if (isLoginMode) {
            actionButton.setText("Login");
            switchModeButton.setText("No account? Register");
        } else {
            actionButton.setText("Register");
            switchModeButton.setText("Have an account? Login");
        }
    }

    private void handleRegister(String username, String password) {
        userManager.registerUser(username, password, new UserCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(LoginRegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    isLoginMode = true;
                    updateMode();
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() ->
                    Toast.makeText(LoginRegisterActivity.this, message, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    private void handleLogin(String username, String password) {
        userManager.authenticateUser(username, password, new AuthCallback() {
            @Override
            public void onAuthenticated(User user) {
                runOnUiThread(() -> {
                    Toast.makeText(LoginRegisterActivity.this, "Welcome " + user.getUsername(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
            }


            @Override
            public void onFailure(String message) {
                runOnUiThread(() ->
                    Toast.makeText(LoginRegisterActivity.this, message, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}