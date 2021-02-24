package com.example.aplicaciongestiontiendas.Adaptadores;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplicaciongestiontiendas.DatosProductoActivity;
import com.example.aplicaciongestiontiendas.LineasPedidosActivity;
import com.example.aplicaciongestiontiendas.ListaPedidosActivity;
import com.example.aplicaciongestiontiendas.Modelos.Pedidos;
import com.example.aplicaciongestiontiendas.Modelos.Productos;
import com.example.aplicaciongestiontiendas.R;

import java.util.ArrayList;

public class AdaptadorPedidos extends RecyclerView.Adapter<AdaptadorPedidos.AdaptadorViewHolder>{
    private ArrayList<Pedidos> listaPedidos = new ArrayList<>();
    private Context context;
    private ProgressDialog dialog;


    public AdaptadorPedidos(ArrayList<Pedidos> listaPedidos, Context context) {
        this.listaPedidos = listaPedidos;
        this.context = context;
    }


    @NonNull
    @Override
    public AdaptadorPedidos.AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
            if (viewType == 1){
               itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pagado,parent,false);
            }else{
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_pagado,parent,false);
            }

        AdaptadorViewHolder avh=new AdaptadorViewHolder(itemView);
        return avh;

    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorViewHolder holder, int position) {
        Pedidos p = listaPedidos.get(position);
        holder.tvIdPedido.setText("Nº Pedido: "+p.getIdPedido());
        holder.tvFecha.setText("Fecha: " + p.getFechaPedido());


        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, LineasPedidosActivity.class);
                i.putExtra("pedido",p);
                Toast.makeText(context, ""+ p.getIdPedido(), Toast.LENGTH_SHORT).show();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        holder.btEliminarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(context);
                dialogo.setTitle("¿Estas seguro de eliminar este pedido?");
                dialogo.setMessage("El pedido y todas sus lineas desapareceran,¿Esta seguro?");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        eliminarPedido(p);
                    }
                });
                dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        //codigo
                    }
                });
                dialogo.show();
            }
        });
    }

    private void eliminarPedido(Pedidos p) {

        dialog = new ProgressDialog(context);
        dialog.setMessage("Borrando pedido...");
        dialog.show();
        String url = "http://matfranvictor.atwebpages.com/borrarPedido.php?idPedido=" + p.getIdPedido();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("1")) {
                    dialog.hide();
                    Toast.makeText(context, "Pedido eliminado", Toast.LENGTH_LONG).show();
                    listaPedidos.remove(p);
                    notifyDataSetChanged();
                } else {
                    dialog.hide();
                    Toast.makeText(context, "Fallo al eliminar", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hide();
            }
        });
        Volley.newRequestQueue(context).add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    @Override
    public int getItemViewType(int position) {

    return listaPedidos.get(position).getPagado();

    }

    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIdPedido, tvFecha;
        private ImageButton btEliminarPedido;
        private  ConstraintLayout cl;
        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdPedido=itemView.findViewById(R.id.tvIdPedidos);
            tvFecha=itemView.findViewById(R.id.tvFechaPedido);
            btEliminarPedido=itemView.findViewById(R.id.btBorrarPedido);
            cl = itemView.findViewById(R.id.cl);
        }
    }

}
