package com.example.aplicaciongestiontiendas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplicaciongestiontiendas.Adaptadores.AdaptadorPedidos;
import com.example.aplicaciongestiontiendas.Modelos.Pedidos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.example.aplicaciongestiontiendas.MainActivity.u;

public class ListaPedidosActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<Pedidos> listaPedidos=new ArrayList<>();
    private ProgressDialog dialog;
    private SwipeRefreshLayout swiperefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        swiperefresh = findViewById(R.id.swiperefresh2);
        rv = findViewById(R.id.rvPedidos);
        layoutManager=new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        cargarUsuario();


        swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        listaPedidos.clear();
                        cargarUsuario();
                        swiperefresh.setRefreshing(false);
                    }
                }
        );
    }


    private void cargarPedidos(int idUsuario) {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando pedidos usuario");
        dialog.show();
        String url = "http://matfranvictor.atwebpages.com/listarPedidos.php?idUsuario="+idUsuario;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                        Pedidos p = new Pedidos();
                        p.setIdPedido(Integer.parseInt(jsonObject.get("idPedido").toString()));
                        p.setFechaPedido(jsonObject.get("fechaPedido").toString());
                        p.setPagado(jsonObject.getInt("pagado"));

                        listaPedidos.add(p);

                    }
                    if(listaPedidos.size()>0){
                        adapter = new AdaptadorPedidos(listaPedidos, ListaPedidosActivity.this);
                        rv.setAdapter(adapter);
                        dialog.hide();
                    }else{
                        dialog.hide();
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

    public void cargarUsuario() {
        String correoAux = "\"" + u.getCorreo() + "\"";
        String url = "http://matfranvictor.atwebpages.com/consultarUsuario.php?correo=" + correoAux;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                        cargarPedidos(Integer.parseInt(jsonObject.get("idUsuario").toString()));
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
        Volley.newRequestQueue(this).add(stringRequest);


    }
}