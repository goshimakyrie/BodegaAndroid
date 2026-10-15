package com.example.bodegeishon;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private boolean activo;
    private String nombre;

    public User() {
    }

    public User(int id, String username, String password, String role, boolean activo, String nombre) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.activo = activo;
        this.nombre = nombre;
    }

    public User(String username, String password, String role, boolean activo, String nombre) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.activo = activo;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
