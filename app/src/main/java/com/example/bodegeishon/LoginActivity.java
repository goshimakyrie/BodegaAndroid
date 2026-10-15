package com.example.bodegeishon;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private TextView tvErrorMessage;

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

        ImageView ivLogo = findViewById(R.id.iv_logo);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        tvErrorMessage = findViewById(R.id.tv_error_message);
        Button btnLogin = findViewById(R.id.btn_login);

        // Load logo image from URL using Glide
        String logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGFehvXOik-CYuMOcbnOUuuO6o6lCtctlSxI5JiriD_dorXJjOncoIWZc&s=10";
        Glide.with(this)
                .load(logoUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ivLogo);

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

        // Prototype login logic: check DB if user exists, otherwise create prototype session
        User user = dbHelper.getUserByUsername(username);

        if (user != null) {
            if (!user.getPassword().equals(password)) {
                showError(getString(R.string.error_invalid_password));
                return;
            }

            if (!user.isActivo()) {
                showError(getString(R.string.error_user_inactive));
                return;
            }

            sessionManager.createLoginSession(user.getUsername(), user.getRole(), user.getNombre());
        } else {
            // Prototype mode: accept non-empty input without requiring hardcoded pre-loaded DB users
            String role = username.toLowerCase().contains("admin") ? "Administrador" : "Bodeguero / Operador";
            sessionManager.createLoginSession(username, role, username);
        }

        Toast.makeText(this, getString(R.string.welcome_message, sessionManager.getNombre()), Toast.LENGTH_SHORT).show();
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
