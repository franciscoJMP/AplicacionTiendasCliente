package com.example.aplicaciongestiontiendas.Modelos;

import java.io.Serializable;
import java.util.Date;

public class Pedidos implements Serializable {
    private int idPedido,idUsuario;
    private int pagado;
    private String fechaPedido;

    public Pedidos(int idPedido, int idUsuario, int pagado, String fechaPedido) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.pagado = pagado;
        this.fechaPedido = fechaPedido;
    }

    public Pedidos() {
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {this.idUsuario = idUsuario;}

    public int getPagado() {
        return pagado;
    }

    public int isPagado() {
        return pagado;
    }

    public void setPagado(int pagado) {
        this.pagado = pagado;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }
}
