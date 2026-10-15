package com.example.bodegeishon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "BodegeishonSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private static final String KEY_NOMBRE = "nombre";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String username, String role, String nombre) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_NOMBRE, nombre);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "");
    }

    public String getRole() {
        return pref.getString(KEY_ROLE, "");
    }

    public String getNombre() {
        return pref.getString(KEY_NOMBRE, "");
    }

    public void logoutUser() {
        editor.clear();
        editor.apply();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
