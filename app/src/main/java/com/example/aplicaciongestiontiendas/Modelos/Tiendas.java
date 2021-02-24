package com.example.aplicaciongestiontiendas.Modelos;

import java.io.Serializable;

public class Tiendas implements Serializable {
    private int idTienda;
    private String nombre,descripcion;

    public Tiendas(int idTienda, String nombre, String descripcion) {
        this.idTienda = idTienda;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Tiendas() {
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
