package com.example.aplicaciongestiontiendas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplicaciongestiontiendas.Adaptadores.AdaptadorProductos;
import com.example.aplicaciongestiontiendas.Adaptadores.AdaptadorTiendas;
import com.example.aplicaciongestiontiendas.Modelos.LineasPedidos;
import com.example.aplicaciongestiontiendas.Modelos.Productos;
import com.example.aplicaciongestiontiendas.Modelos.Tiendas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DatosLineaPedidoActivity extends AppCompatActivity {
    private LineasPedidos ln;
    private TextView tienda,producto,cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_linea_pedido);
        cargarView();
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            ln = (LineasPedidos) extra.getSerializable("linea");
        }

        cantidad.setText(""+ln.getCantidad());
        cargarProducto();



    }

    private void cargarTienda(Productos p) {
        String url = "http://matfranvictor.atwebpages.com/tiendaProducto.php?tienda="+p.getIdTienda();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Tiendas t = new Tiendas();
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                        t.setNombre(jsonObject.get("Local").toString());
                        t.setDescripcion(jsonObject.get("Descripcion").toString());
                    }
                    tienda.setText(t.getNombre());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void cargarProducto() {
        String url = "http://matfranvictor.atwebpages.com/listarProductoIDProducto.php?producto="+ln.getIdProducto();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Productos p = new Productos();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                        p.setIdProducto(Integer.parseInt(jsonObject.get("idProducto").toString()));
                        p.setNombre(jsonObject.get("nombre").toString());
                        p.setCantidad(Integer.parseInt(jsonObject.get("cantidad").toString()));
                        p.setPrecio(Float.parseFloat(jsonObject.get("precio").toString()));
                        p.setIdTienda(Integer.parseInt(jsonObject.get("idTienda").toString()));


                    }
                    cargarTienda(p);
                    producto.setText(p.getNombre());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);


    }

    private void cargarView() {
        tienda=findViewById(R.id.tvNombreTienda);
        producto=findViewById(R.id.tvNombreProducto);
        cantidad=findViewById(R.id.tvCantidadProducto);
    }
}