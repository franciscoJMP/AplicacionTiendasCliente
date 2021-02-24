package com.example.aplicaciongestiontiendas.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.aplicaciongestiontiendas.DatosLineaPedidoActivity;
import com.example.aplicaciongestiontiendas.Modelos.LineasPedidos;
import com.example.aplicaciongestiontiendas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdaptadorLineasPedido extends RecyclerView.Adapter<AdaptadorLineasPedido.AdaptadorViewHolder> {
    private ArrayList<LineasPedidos> lineasPedidosArrayList = new ArrayList<>();
    private Context contexto;

    public AdaptadorLineasPedido(ArrayList<LineasPedidos> lineasPedidosArrayList, Context contexto) {
        this.lineasPedidosArrayList = lineasPedidosArrayList;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public AdaptadorLineasPedido.AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lienas_pedidos, parent, false);
        AdaptadorLineasPedido.AdaptadorViewHolder avh = new AdaptadorLineasPedido.AdaptadorViewHolder(itemView);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorViewHolder holder, int position) {
        LineasPedidos ln = lineasPedidosArrayList.get(position);

        String url = "http://matfranvictor.atwebpages.com/listarProductoIDProducto.php?producto=" + +ln.getIdProducto();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                        holder.nombreProducto.setText(jsonObject.get("nombre").toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Mensaje de error a la hora de consultar
            }
        });
        Volley.newRequestQueue(contexto).add(stringRequest);

        holder.filaProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contexto, DatosLineaPedidoActivity.class);
                i.putExtra("linea",ln);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contexto.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lineasPedidosArrayList.size();
    }


    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreProducto;
        private ConstraintLayout filaProducto;
        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProducto=itemView.findViewById(R.id.lnProductos);
            filaProducto=itemView.findViewById(R.id.filaProducto);
        }
    }
}
