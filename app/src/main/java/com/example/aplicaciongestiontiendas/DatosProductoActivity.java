package com.example.aplicaciongestiontiendas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicaciongestiontiendas.Modelos.LineasPedidos;
import com.example.aplicaciongestiontiendas.Modelos.Productos;
import com.example.aplicaciongestiontiendas.Modelos.Tiendas;
import com.example.aplicaciongestiontiendas.R;

import static com.example.aplicaciongestiontiendas.MainActivity.lineaPedido;
import static com.example.aplicaciongestiontiendas.MainActivity.listalineasPedidos;

public class DatosProductoActivity extends AppCompatActivity {

    private Productos p;
    private TextView  tvCantidadProd, tvPrecioProd, tvNombreProd;
    private ImageButton btCarrito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_producto);
        cargarViews();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            p = (Productos) extra.getSerializable("producto");
        }

        tvNombreProd.setText(p.getNombre());
        tvPrecioProd.setText(String.valueOf(p.getPrecio()));
        tvCantidadProd.setText(String.valueOf(p.getCantidad()));

        btCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Cantidad");
                final EditText cantidad = new EditText(getBaseContext());
                cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
                cantidad.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(cantidad);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(p.getCantidad()<Integer.parseInt(cantidad.getText().toString())){
                            AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                            alert.setTitle("Cantidad excedida");
                            alert.setMessage("No hay cantidad suficiente en tienda");
                            alert.setPositiveButton("De acuerdo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {}
                            });
                            alert.show();

                        }else{

                            LineasPedidos ln = new LineasPedidos();
                            ln.setCantidad(Integer.parseInt(cantidad.getText().toString()));
                            ln.setIdLinea(lineaPedido);
                            ln.setIdProducto(p.getIdProducto());
                            ln.setPrecio(p.getPrecio());
                            lineaPedido++;
                            listalineasPedidos.add(ln);
                            Toast.makeText(DatosProductoActivity.this,"Añadido al carrito",Toast.LENGTH_LONG).show();

                        }
                    }
                });
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }
        });

    }

    private void cargarViews(){
        tvCantidadProd = findViewById(R.id.tvCantidad);
        tvNombreProd = findViewById(R.id.tvNombreProducto);
        tvPrecioProd = findViewById(R.id.tvPrecioProducto);

        btCarrito = findViewById(R.id.btCarrito);

    }
}