package com.example.aplicaciongestiontiendas.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicaciongestiontiendas.Modelos.Tiendas;
import com.example.aplicaciongestiontiendas.ProductosActivity;
import com.example.aplicaciongestiontiendas.R;

import java.util.ArrayList;

public class AdaptadorTiendas extends RecyclerView.Adapter<AdaptadorTiendas.AdaptadorViewHolder> {
    private ArrayList<Tiendas> listaTiendas = new ArrayList<>();
    private Context context;

    public AdaptadorTiendas(ArrayList<Tiendas> listaTiendas, Context context) {
        this.listaTiendas = listaTiendas;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorTiendas.AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tiendas,parent,false);
        AdaptadorViewHolder avh=new AdaptadorViewHolder(itemView);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorViewHolder holder, int position) {
        Tiendas t = listaTiendas.get(position);
        holder.nombreTienda.setText(t.getNombre());
        holder.descripcionTienda.setText(t.getDescripcion());
        holder.cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProductosActivity.class);
                i.putExtra("tienda",t);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaTiendas.size();
    }

    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cs;
        private TextView nombreTienda,descripcionTienda;
        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTienda=itemView.findViewById(R.id.nombreTienda);
            descripcionTienda=itemView.findViewById(R.id.descripcionTienda);
            cs = itemView.findViewById(R.id.filaTienda);
        }
    }
}
