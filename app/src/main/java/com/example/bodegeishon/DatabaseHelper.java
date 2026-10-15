package com.example.bodegeishon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bodegeishon.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_ACTIVO = "activo";
    public static final String COLUMN_NOMBRE = "nombre";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT UNIQUE NOT NULL, "
                + COLUMN_PASSWORD + " TEXT NOT NULL, "
                + COLUMN_ROLE + " TEXT NOT NULL, "
                + COLUMN_ACTIVO + " INTEGER NOT NULL DEFAULT 1, "
                + COLUMN_NOMBRE + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);

        // Seed initial users for testing
        seedUsers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void seedUsers(SQLiteDatabase db) {
        insertUserWithDb(db, new User("admin", "admin123", "Administrador", true, "Ángel Administrador"));
        insertUserWithDb(db, new User("bodeguero", "user123", "Bodeguero", true, "Juan Bodeguero"));
        insertUserWithDb(db, new User("operador", "user123", "Operador", true, "Carlos Operador"));
        insertUserWithDb(db, new User("inactivo", "user123", "Operador", false, "Pedro Inactivo"));
    }

    private void insertUserWithDb(SQLiteDatabase db, User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_ROLE, user.getRole());
        values.put(COLUMN_ACTIVO, user.isActivo() ? 1 : 0);
        values.put(COLUMN_NOMBRE, user.getNombre());
        db.insert(TABLE_USERS, null, values);
    }

    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_ROLE, COLUMN_ACTIVO, COLUMN_NOMBRE},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String userStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            String passStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            String roleStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE));
            int activoInt = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACTIVO));
            String nombreStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));

            cursor.close();
            return new User(id, userStr, passStr, roleStr, activoInt == 1, nombreStr);
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }
}
