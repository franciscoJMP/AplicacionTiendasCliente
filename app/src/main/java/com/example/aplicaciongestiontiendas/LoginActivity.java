package com.example.aplicaciongestiontiendas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button acceso, registrar;
    private EditText email, password;
    private ProgressDialog dialog;
    private SharedPreferences propiedades;
    private SharedPreferences.Editor editor;
    private boolean borrarPreferencias=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        propiedades=getPreferences(Context.MODE_PRIVATE);
        editor=propiedades.edit();
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            borrarPreferencias =  extra.getBoolean("borrar");

            if(borrarPreferencias){
                editor.clear().commit();
            }
        }
        comprobarPreferencias();
        cargarComponentes();
    }

    private void comprobarPreferencias() {
        String correo = propiedades.getString("correo","");
        String password = propiedades.getString("password","");
        if(!correo.equals("") && !password.equals("")){
            comprobarCredenciales(correo,password);
        }

    }


    private void cargarComponentes() {
        acceso = findViewById(R.id.bt_loginLogin);
        registrar = findViewById(R.id.bt_loginRegistrar);
        email = findViewById(R.id.usuCorreo);
        password = findViewById(R.id.usuPass);
        acceso.setOnClickListener(this);
        registrar.setOnClickListener(this);

    }

    private void navegar(int opcion) {
        Intent i;
        switch (opcion) {
            case 1:
                i = new Intent(this, RegisterActivity.class);
                startActivity(i);
                break;
            case 2:
                Usuarios u = new Usuarios();
                u.setCorreo(propiedades.getString("correo",""));
                u.setPassword(propiedades.getString("password",""));
                i = new Intent(this, MainActivity.class);
                i.putExtra("usuario",u);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_loginRegistrar:
                navegar(1);
                break;
            case R.id.bt_loginLogin:
                acceso.setEnabled(false);
                String correo = email.getText().toString();
                String pass = getMD5(password.getText().toString());
                comprobarCredenciales(correo,pass);
                break;
        }
    }





    private void comprobarCredenciales(String correo, String pass) {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Accediendo...");
        String correoAux = "\"" +correo+ "\"";
        String passAux = "\""+pass+"\"";
        String url = "http://matfranvictor.atwebpages.com/login.php?correo=" + correoAux+"&pass="+passAux;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.contains("1")){
                    editor.putString("correo",correo);
                    editor.putString("password",pass);
                    editor.commit();
                    navegar(2);
                    dialog.hide();
                }else{
                    Toast.makeText(LoginActivity.this,"Fallo en las creedenciales",Toast.LENGTH_LONG).show();
                    acceso.setEnabled(true);
                    dialog.hide();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Mensaje de error a la hora de consultar
                dialog.hide();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    //Funcion para convertir un String a md5(Nos sirve tambien para el registrar)

    private String getMD5(final String s){
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5", "md5() NoSuchAlgorithmException: " + e.getMessage());
        }
        return "";
    }

}