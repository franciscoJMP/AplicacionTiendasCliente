package com.example.aplicaciongestiontiendas.Modelos;

import java.io.Serializable;

public class Usuarios implements Serializable {
    private int idUsuario;
    float saldo;
    private String correo,password,nombre,apellidos,tipo;

    public Usuarios(int idUsuario, float saldo, String correo, String password, String nombre, String apellidos, String tipo) {
        this.idUsuario = idUsuario;
        this.saldo = saldo;
        this.correo = correo;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipo = tipo;
    }

    public Usuarios() {
        
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
