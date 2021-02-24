package com.example.aplicaciongestiontiendas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.aplicaciongestiontiendas.Adaptadores.AdaptadorProductos;
import com.example.aplicaciongestiontiendas.Modelos.Productos;
import com.example.aplicaciongestiontiendas.Modelos.Tiendas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductosActivity extends AppCompatActivity {
   private Tiendas t;

    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<Productos> listaProductos =new ArrayList<>();
    private ProgressDialog dialog;
    private TextView nombreTienda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            t = (Tiendas) extra.getSerializable("tienda");
        }
        nombreTienda=findViewById(R.id.tituloTienda);
        nombreTienda.setText(t.getNombre());

        rv = findViewById(R.id.rvProductos);

        layoutManager=new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        cargarProductos();

    }


    private void cargarProductos() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando Productos");
        dialog.show();
        String url = "http://matfranvictor.atwebpages.com/listarProductos.php?tienda=" + t.getIdTienda();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                        Productos p = new Productos();
                        p.setIdProducto(Integer.parseInt(jsonObject.get("idProducto").toString()));
                        p.setNombre(jsonObject.get("nombre").toString());
                        p.setCantidad(Integer.parseInt(jsonObject.get("cantidad").toString()));
                        p.setPrecio(Float.parseFloat(jsonObject.get("precio").toString()));
                        p.setIdTienda(Integer.parseInt(jsonObject.get("idTienda").toString()));
                        listaProductos.add(p);

                    }
                    if(listaProductos.size()>0){
                        adapter = new AdaptadorProductos(listaProductos, getBaseContext());
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
        Volley.newRequestQueue(getBaseContext()).add(stringRequest);
    }



}