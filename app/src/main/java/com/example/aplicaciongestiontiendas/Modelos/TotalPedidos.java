package com.example.aplicaciongestiontiendas.Modelos;

import java.io.Serializable;

public class TotalPedidos implements Serializable {
    private int idPedido;
    private double totalPedido;

    public TotalPedidos(int idPedido, double totalPedido) {
        this.idPedido = idPedido;
        this.totalPedido = totalPedido;
    }

    public TotalPedidos() {

    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }
}
