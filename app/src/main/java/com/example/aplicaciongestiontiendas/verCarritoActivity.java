package com.example.aplicaciongestiontiendas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplicaciongestiontiendas.Adaptadores.AdaptadorLineasPedido;
import com.example.aplicaciongestiontiendas.Adaptadores.AdaptadorProductos;
import com.example.aplicaciongestiontiendas.Modelos.LineasPedidos;
import com.example.aplicaciongestiontiendas.Modelos.Productos;
import com.example.aplicaciongestiontiendas.Modelos.TotalPedidos;
import com.example.aplicaciongestiontiendas.Modelos.Usuarios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.aplicaciongestiontiendas.MainActivity.lineaPedido;
import static com.example.aplicaciongestiontiendas.MainActivity.listalineasPedidos;
import static com.example.aplicaciongestiontiendas.MainActivity.u;

public class verCarritoActivity extends AppCompatActivity{
    private RecyclerView rv_lineasPedidos;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Button bt_tramitarPedido;


    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_carrito);
        bt_tramitarPedido=findViewById(R.id.bt_tramitarPedido);
        if (listalineasPedidos.size() > 0) {
            rv_lineasPedidos = findViewById(R.id.rv_lineasPedidos);
            layoutManager = new LinearLayoutManager(this);
            rv_lineasPedidos.setLayoutManager(layoutManager);
            adapter = new AdaptadorLineasPedido(listalineasPedidos, this);
            rv_lineasPedidos.setAdapter(adapter);
            bt_tramitarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bt_tramitarPedido.setEnabled(false);
                    cargarUsuario();
                }
            });

        } else{
            bt_tramitarPedido.setVisibility(View.INVISIBLE);
        }

    }

    private void cargarUsuario() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Tramitando pedido...");
        dialog.show();
        String correoAux = "\"" + u.getCorreo() + "\"";

        String url = "http://matfranvictor.atwebpages.com/consultarUsuario.php?correo=" + correoAux;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                        Usuarios usu = new Usuarios();
                        usu.setTipo(jsonObject.get("tipo").toString());
                        usu.setNombre(jsonObject.get("nombre").toString());
                        usu.setApellidos(jsonObject.get("apellidos").toString());
                        usu.setSaldo(Float.parseFloat(jsonObject.get("saldo").toString()));
                        usu.setIdUsuario(Integer.parseInt(jsonObject.get("idUsuario").toString()));

                        tramitarPedido(usu);
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


    private void tramitarPedido(Usuarios usuario) {

        int saldoPositivo=isPossitiveSaldo(usuario.getSaldo());
        modificarSaldoUsuario(usuario);
        String url = "http://matfranvictor.atwebpages.com/crearPedido.php?pagado="+saldoPositivo+"&idUsuario="+usuario.getIdUsuario();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.contains("-1")){
                    int numPedido=Integer.parseInt(response);
                    insertarLineas(numPedido);
                    calcularTotalPedido(numPedido);
                }else{

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hide();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void calcularTotalPedido(int numPedido) {
        float total=0;
        for(LineasPedidos ln:listalineasPedidos){
            total+=ln.getCantidad()*ln.getPrecio();
        }
        TotalPedidos tl = new TotalPedidos(numPedido,total);
        String url = "http://matfranvictor.atwebpages.com/insertarTotalPedido.php?idPedido="+tl.getIdPedido()+"&total="+tl.getTotalPedido();
        insertarTotalPedido(url);

    }

    private void insertarTotalPedido(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.contains("1")){

                }else{

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void modificarSaldoUsuario(Usuarios usuario) {
        String cooreoAux = "\"" + u.getCorreo() + "\"";
        float total=0;
        for(LineasPedidos ln:listalineasPedidos){
            total+=ln.getCantidad()*ln.getPrecio();
        }

        double saldo = usuario.getSaldo()-total;
        if(saldo>0){
            saldo = Math.round(saldo*100.0)/100.0;
            String url = "http://matfranvictor.atwebpages.com/actualizarUsuario.php?correo="+cooreoAux+"&nombre="+null+"&apellidos=" + null + "&saldo=" + saldo;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.hide();
                }
            });
            Volley.newRequestQueue(this).add(stringRequest);
        }


    }

    private void insertarLineas(int numPedido) {
        for(LineasPedidos ln:listalineasPedidos){
            ln.setIdPedido(numPedido);
            addLine(ln);
        }

    }

    private void addLine(LineasPedidos ln) {
        String url = "http://matfranvictor.atwebpages.com/insertarLinea.php?idLinea="+
                ln.getIdLinea()
                +"&idPedido="
                +ln.getIdPedido()
                +"&idProducto="
                +ln.getIdProducto()
                +"&cantidad="
                +ln.getCantidad()
                +"&precio="+ln.getPrecio();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("1")){
                        bt_tramitarPedido.setEnabled(false);
                        listalineasPedidos.clear();
                        lineaPedido=1;
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hide();
            }
        });
        dialog.dismiss();
        dialog.hide();
        Volley.newRequestQueue(this).add(stringRequest);



    }

    private int isPossitiveSaldo(double saldo) {
        float sumatoria=0;
        for(LineasPedidos ln:listalineasPedidos){
            sumatoria+=ln.getPrecio()*ln.getCantidad();
        }
        if(sumatoria>saldo){
            return 0;
        }else{
            return 1;
        }

    }

}