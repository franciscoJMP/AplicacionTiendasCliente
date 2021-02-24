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

public class RegisterActivity extends AppCompatActivity {
    private Usuarios usuario;
    private EditText tEmail, tPassword, tNombre, tApellidos, tTipo;
    private Button bRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usuario = new Usuarios();
        tEmail = findViewById(R.id.tEmail);
        tPassword = findViewById(R.id.tPassword);
        tNombre = findViewById(R.id.tNombre);
        tApellidos = findViewById(R.id.tApellidos);
        tTipo = findViewById(R.id.tTipo);
        bRegistrarse = findViewById(R.id.bRegistrarse);

        bRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarEmail();
                //registrarse();
            }
        });

    }

    private void comprobarEmail() {
        if (!tEmail.getText().toString().isEmpty() && !tPassword.getText().toString().isEmpty() && !tNombre.getText().toString().isEmpty() && !tApellidos.getText().toString().isEmpty()) {
            String correoAux = "\"" + tEmail.getText().toString() + "\"";
            String url = "http://matfranvictor.atwebpages.com/comprobarUsuario.php?correo=" + correoAux;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    bRegistrarse.setEnabled(false);
                    if (response.contains("1")) {
                        Toast.makeText(RegisterActivity.this, "El email existe", Toast.LENGTH_LONG).show();
                        bRegistrarse.setEnabled(true);
                    } else if (response.contains("0")) {
                        registrarse();
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


    private void registrarse() {


        //Toast.makeText(RegisterActivity.this, ""+comprobarEmail()[0], Toast.LENGTH_LONG).show();


        String correoAux = tEmail.getText().toString();
        String passAux =  tPassword.getText().toString();
        String nombreAux =  tNombre.getText().toString() ;
        String apellidosAux = tApellidos.getText().toString() ;
        String tipoAux =tTipo.getText().toString();
        String url = "http://matfranvictor.atwebpages.com/registrarse.php?correo=" + correoAux + "&password=" + passAux + "&nombre=" + nombreAux + "&apellidos=" + apellidosAux + "&tipo=" + tipoAux;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                bRegistrarse.setEnabled(false);
                if (response.contains("Correcto")) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    bRegistrarse.setEnabled(true);
                } else {
                    Toast.makeText(RegisterActivity.this, "Fallo en las creedenciales", Toast.LENGTH_LONG).show();
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
