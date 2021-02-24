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

import com.example.aplicaciongestiontiendas.Modelos.Productos;
import com.example.aplicaciongestiontiendas.R;
import com.example.aplicaciongestiontiendas.DatosProductoActivity;

import java.util.ArrayList;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.AdaptadorViewHolder> {
    private ArrayList<Productos> listaProductos = new ArrayList<>();
    private Context context;



    public AdaptadorProductos(ArrayList<Productos> listaProductos, Context context) {
        this.listaProductos = listaProductos;
        this.context = context;
    }


    @NonNull
    @Override
    public AdaptadorProductos.AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos,parent,false);
        AdaptadorViewHolder avh=new AdaptadorViewHolder(itemView);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProductos.AdaptadorViewHolder holder, int position) {
        Productos p = listaProductos.get(position);
        holder.nombreProducto.setText(p.getNombre());
        holder.cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DatosProductoActivity.class);
                i.putExtra("producto",p);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cs;
        private TextView nombreProducto;
        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProducto=itemView.findViewById(R.id.nombreProducto);
            cs=itemView.findViewById(R.id.filaProducto);
        }
    }

}
