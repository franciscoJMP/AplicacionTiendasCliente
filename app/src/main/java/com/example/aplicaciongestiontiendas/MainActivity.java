package com.example.aplicaciongestiontiendas;



import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.aplicaciongestiontiendas.Modelos.LineasPedidos;
import com.example.aplicaciongestiontiendas.Modelos.Usuarios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import com.example.aplicaciongestiontiendas.ui.main.SectionsPagerAdapter;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {
    public static Usuarios u;
    public static int lineaPedido=1;
    private Toolbar toolbar;
    public static ArrayList<LineasPedidos> listalineasPedidos = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        //Toolbar de ajustes y opciones
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_title);
        toolbar.setSubtitle(R.string.subtitle_toolbar);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),verCarritoActivity.class);
                startActivity(i);
            }
        });

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            u = (Usuarios) extra.getSerializable("usuario");
        }

    }

    //------------------------------------INFLAR EL TOOLBAR---------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            //Para salir de sesion y limpiar los datos de las preferencias
            case R.id.itemLogout:
                Intent i = new Intent(this,LoginActivity.class);
                i.putExtra("borrar",true);
                startActivity(i);
                break;

            case R.id.itemListarPedido:
                Intent intent = new Intent(this,ListaPedidosActivity.class);
                startActivity(intent);



        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

    }



}