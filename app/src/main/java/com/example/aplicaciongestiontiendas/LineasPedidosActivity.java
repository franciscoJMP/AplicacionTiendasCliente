package com.example.aplicaciongestiontiendas;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplicaciongestiontiendas.Adaptadores.AdaptadorLineasPedido;
import com.example.aplicaciongestiontiendas.Modelos.LineasPedidos;
import com.example.aplicaciongestiontiendas.Modelos.Pedidos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LineasPedidosActivity extends AppCompatActivity {

    Pedidos p;
    private ProgressDialog dialog;
    private ArrayList<LineasPedidos> listaLineas = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView rv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineas_pedidos);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            p = (Pedidos) extra.getSerializable("pedido");
        }

        rv = findViewById(R.id.rv_lienas);
        layoutManager=new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        cargarLineas();

    }


    private void cargarLineas() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando lineas del pedido");
        dialog.setCancelable(false);
        dialog.show();
        String url = "http://matfranvictor.atwebpages.com/listarLineasPedido.php?idPedido="+p.getIdPedido();
        Log.e("msg",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                        LineasPedidos lp = new LineasPedidos();
                        lp.setIdLinea(Integer.parseInt(jsonObject.get("idLinea").toString()));
                        lp.setIdPedido(Integer.parseInt(jsonObject.get("idPedido").toString()));
                        lp.setIdProducto(Integer.parseInt(jsonObject.get("idProducto").toString()));
                        lp.setCantidad(Integer.parseInt(jsonObject.get("cantidad").toString()));

                        listaLineas.add(lp);

                    }
                   if(listaLineas.size()>0){
                        adapter = new AdaptadorLineasPedido(listaLineas, getApplicationContext());
                        rv.setAdapter(adapter);
                        dialog.hide();

                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hide();
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}