package com.example.bodegeishon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);

        // Security check: if user is not logged in, redirect to LoginActivity
        if (!sessionManager.isLoggedIn()) {
            redirectToLogin();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvWelcome = findViewById(R.id.tv_welcome);
        TextView tvUsername = findViewById(R.id.tv_username);
        TextView tvRole = findViewById(R.id.tv_role);
        TextView tvPermissionsDescription = findViewById(R.id.tv_permissions_description);
        Button btnLogout = findViewById(R.id.btn_logout);

        String username = sessionManager.getUsername();
        String role = sessionManager.getRole();
        String nombre = sessionManager.getNombre();

        tvWelcome.setText(getString(R.string.welcome_message, nombre));
        tvUsername.setText(getString(R.string.user_label, username));
        tvRole.setText(getString(R.string.role_label, role));

        if ("Administrador".equalsIgnoreCase(role)) {
            tvPermissionsDescription.setText(getString(R.string.admin_permissions_desc));
        } else {
            tvPermissionsDescription.setText(getString(R.string.operator_permissions_desc));
        }

        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, getString(R.string.logout_toast), Toast.LENGTH_SHORT).show();
            sessionManager.logoutUser();
        });
    }

    private void redirectToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
