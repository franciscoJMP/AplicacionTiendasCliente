package com.example.aplicaciongestiontiendas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplicaciongestiontiendas.Modelos.Usuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.aplicaciongestiontiendas.MainActivity.u;


public class AjustesFragment extends Fragment {

    private Usuarios user;
    private TextView t1, t2, t3, t4, t5;
    private Button button;
    private Usuarios usuCompleto;
    private SwipeRefreshLayout swiperefresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarUsuario();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_ajustes, container, false);
        user = u;
        usuCompleto = new Usuarios();

        t1 = root.findViewById(R.id.et_nombre_ajustes);
        t2 = root.findViewById(R.id.et_apellidos_ajustes);
        t3 = root.findViewById(R.id.et_email_ajustes);
        t4 = root.findViewById(R.id.et_saldo_ajustes);
        button = root.findViewById(R.id.bt_modificar_perfil);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ModificarPerfil.class);
                i.putExtra("usu",u);
                startActivity(i);

            }
        });

        swiperefresh = root.findViewById(R.id.swiperefresh);

        swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        cargarUsuario();
                        swiperefresh.setRefreshing(false);
                    }
                }
        );

        // Inflate the layout for this fragment
        return root;
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
                        usuCompleto.setTipo(jsonObject.get("tipo").toString());
                        usuCompleto.setNombre(jsonObject.get("nombre").toString());
                        usuCompleto.setApellidos(jsonObject.get("apellidos").toString());
                        usuCompleto.setSaldo(Float.parseFloat(jsonObject.get("saldo").toString()));
                        usuCompleto.setTipo(jsonObject.get("tipo").toString());

                        t1.setText(jsonObject.get("nombre").toString());
                        t2.setText(jsonObject.get("apellidos").toString());
                        t3.setText(jsonObject.get("correo").toString());
                        t4.setText(""+Float.parseFloat(jsonObject.get("saldo").toString()));

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
        Volley.newRequestQueue(getContext()).add(stringRequest);


    }


}