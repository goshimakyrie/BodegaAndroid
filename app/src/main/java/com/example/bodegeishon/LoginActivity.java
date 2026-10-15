package com.example.bodegeishon;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private TextView tvErrorMessage;
    private Button btnLogin;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);
        dbHelper = new DatabaseHelper(this);

        // If user is already logged in, redirect to MainActivity
        if (sessionManager.isLoggedIn()) {
            navigateToMain();
            return;
        }

        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        tvErrorMessage = findViewById(R.id.tv_error_message);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = etUsername.getText() != null ? etUsername.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        // Clear previous error
        hideError();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            showError(getString(R.string.error_empty_fields));
            return;
        }

        User user = dbHelper.getUserByUsername(username);

        if (user == null) {
            showError(getString(R.string.error_user_not_found));
            return;
        }

        if (!user.getPassword().equals(password)) {
            showError(getString(R.string.error_invalid_password));
            return;
        }

        if (!user.isActivo()) {
            showError(getString(R.string.error_user_inactive));
            return;
        }

        // Login successful
        sessionManager.createLoginSession(user.getUsername(), user.getRole(), user.getNombre());
        Toast.makeText(this, getString(R.string.welcome_message, user.getNombre()), Toast.LENGTH_SHORT).show();

        navigateToMain();
    }

    private void showError(String message) {
        tvErrorMessage.setText(message);
        tvErrorMessage.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        tvErrorMessage.setVisibility(View.GONE);
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
