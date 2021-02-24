package com.example.aplicaciongestiontiendas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class ModificarPerfil extends AppCompatActivity {

    EditText e1, e2, e3;
    private Usuarios usu;
    private String nombre, apellidos, saldo;
    private Button b1;
    private String saldoAux, apellidosAux,nombreAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_perfil);

        e1 = this.findViewById(R.id.et_modificar_nombre);
        e2 = this.findViewById(R.id.et_modificar_apellido);
        e3 = this.findViewById(R.id.et_modificar_saldo);

        usu = (Usuarios) getIntent().getSerializableExtra("usu");

        b1 = this.findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPerfil();
            }
        });
        cargarUsuario();

    }

    private void actualizarPerfil() {
        b1.setEnabled(false);
        nombre = e1.getText().toString();
        apellidos = e2.getText().toString();
        saldo = e3.getText().toString();

        if (nombre.isEmpty()) {
            nombreAux = null;
        }else{
            nombreAux = "\"" + nombre + "\"";
        }
        if (apellidos.isEmpty()) {
            apellidosAux = null;
        }else{
            apellidosAux = "\"" + apellidos + "\"";
        }
        if (saldo.isEmpty()) {
            saldoAux = null;
        }else{
            saldoAux = "\"" + saldo + "\"";
        }



        saldoAux = "\"" + saldo + "\"";
        String cooreoAux = "\"" + usu.getCorreo() + "\"";
        String url = "http://matfranvictor.atwebpages.com/actualizarUsuario.php?correo="+cooreoAux+"&nombre="+nombreAux+"&apellidos=" + apellidosAux + "&saldo=" + saldoAux;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                b1.setEnabled(true);
                if (response.contains("Correcto")) {
                    Toast.makeText(getBaseContext(),"Correcto",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ModificarPerfil.this,MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getBaseContext(),"Fallido",Toast.LENGTH_SHORT).show();
                   // acceso.setEnabled(true);
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
                        u.setTipo(jsonObject.get("tipo").toString());
                        u.setNombre(jsonObject.get("nombre").toString());
                        u.setApellidos(jsonObject.get("apellidos").toString());
                        u.setSaldo(Float.parseFloat(jsonObject.get("saldo").toString()));
                        e1.setText(u.getNombre());
                        e2.setText(u.getApellidos());
                        e3.setText(String.valueOf(u.getSaldo()));


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