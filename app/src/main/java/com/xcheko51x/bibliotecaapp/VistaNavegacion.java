package com.xcheko51x.bibliotecaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xcheko51x.bibliotecaapp.Adaptadores.AdaptadorLibros;
import com.xcheko51x.bibliotecaapp.Clases.Libro;
import com.xcheko51x.bibliotecaapp.Clases.Prestamo;
import com.xcheko51x.bibliotecaapp.Clases.Usuario;
import com.xcheko51x.bibliotecaapp.databinding.ActivityVistaNavegacionBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class VistaNavegacion extends AppCompatActivity {

    ActivityVistaNavegacionBinding binding;

    String idUsuario;
    List<Libro> listaLibros;
    AdaptadorLibros adaptadorLibros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_vista_navegacion);
        binding = ActivityVistaNavegacionBinding.inflate(getLayoutInflater());
        View vista = binding.getRoot();
        setContentView(vista);

        binding.rvLibros.setLayoutManager(new GridLayoutManager(this, 2));

        obtenerLibros();

        binding.etLibroBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrar(editable.toString());
            }
        });

        binding.tvLibros.setBackgroundColor(getResources().getColor(R.color.gris_oscuro));

        binding.tvLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llLibros.setVisibility(View.VISIBLE);
                binding.llMisPrestamos.setVisibility(View.GONE);
                binding.tvLibros.setBackgroundColor(getResources().getColor(R.color.gris_oscuro));
                binding.tvMisPrestamos.setBackgroundColor(getResources().getColor(R.color.morado_oscuro));
            }
        });

        binding.tvMisPrestamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llLibros.setVisibility(View.GONE);
                binding.llMisPrestamos.setVisibility(View.VISIBLE);
                binding.tvLibros.setBackgroundColor(getResources().getColor(R.color.morado_oscuro));
                binding.tvMisPrestamos.setBackgroundColor(getResources().getColor(R.color.gris_oscuro));

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c.getTime());
                binding.tvFechaActual.setText(formattedDate);

                leerShared();
                obtenerPrestamo();
            }
        });

        binding.ibtnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarShared();
            }
        });
    }

    public void borrarShared() {
        SharedPreferences preferences = getSharedPreferences("data_usuario", MODE_PRIVATE);
        preferences.edit().clear().commit();

        Intent intent = new Intent(VistaNavegacion.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }

    private void obtenerPrestamo() {
        Prestamo prestamo = new Prestamo();

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, VistaNavegacion.this.getString(R.string.url_api),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(VistaNavegacion.this, response, Toast.LENGTH_SHORT).show();
                        //System.out.println("RESPONSE: "+response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //System.out.println(""+jsonObject.getString("codigo"));
                            if(jsonObject.getString("codigo").equals("OK")) {
                                //System.out.println(""+jsonObject.getString("libros"));
                                JSONArray jsonArray = jsonObject.getJSONArray("prestamos");

                                for (int i = 0 ; i < jsonArray.length() ; i++) {
                                    JSONObject item = jsonArray.getJSONObject(i);

                                    prestamo.setIsbn(item.getString("isbn"));
                                    prestamo.setNomLibro(item.getString("nom_libro"));
                                    prestamo.setNomAutor(item.getString("autor"));
                                    prestamo.setNomEditorial(item.getString("editorial"));
                                    prestamo.setAnioPublicacion(item.getString("anio_publicacion"));
                                    prestamo.setEdicion(item.getString("edicion"));
                                    prestamo.setFechaPrestamo(item.getString("fecha_prestamo"));
                                    prestamo.setFechaDevolucion(item.getString("fecha_devolucion"));
                                }

                            }else if(jsonObject.getString("codigo").equals("ERROR")) {
                                Toast.makeText(VistaNavegacion.this, jsonObject.getString("mensaje"), Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                        binding.tvISBN.setText("ISBN: "+prestamo.getIsbn());
                        binding.tvNomLibro.setText("LIBRO: "+prestamo.getNomLibro());
                        binding.tvNomAutor.setText("AUTOR: "+prestamo.getNomAutor());
                        binding.tvEditorial.setText("EDITORIAL: "+prestamo.getNomEditorial());
                        binding.tvAnioPublicacion.setText("AÑO DE PUBLICACIÓN: "+prestamo.getAnioPublicacion());
                        binding.tvEdicion.setText("EDICIÓN: "+prestamo.getEdicion());
                        binding.tvFechaPrestamo.setText("FECHA DEL PRESTAMO: "+prestamo.getFechaPrestamo());
                        binding.tvFechaDevolucion.setText("FECHA DE DEVOLUCIÓN: "+prestamo.getFechaDevolucion());

                        binding.tvNota.setText(
                                "SI TIENES UN PRESTAMO RECUERDA REGRESAR EL LIBRO EN LAS CONDICIONES EN LAS QUE TE LO PRESTAMOS " +
                                        "Y EN EL TIEMPO MARCADO O DE LO CONTRARIO SERAS ACREEDOR A UNA MULTA."
                        );
                        binding.tvNota.setGravity(Gravity.CENTER);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                Toast.makeText(VistaNavegacion.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // En este metodo se hace el envio de valores de la aplicacion al servidor
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("accion", "702");
                parametros.put("id_usuario", idUsuario);

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(VistaNavegacion.this);
        requestQueue.add(stringRequest);
    }

    private void filtrar(String texto) {
        List<Libro> filtrarLibros = new ArrayList<>();

        for (Libro libro : listaLibros) {
            if(
                    libro.getIsbn().toLowerCase().contains(texto.toLowerCase()) ||
                            libro.getNom_libro().toLowerCase().contains(texto.toLowerCase()) ||
                            libro.getAutor().toLowerCase().contains(texto.toLowerCase())
            ) {
                filtrarLibros.add(libro);
            }
        }

        adaptadorLibros.filtrar(filtrarLibros);
    }

    private void obtenerLibros() {
        listaLibros = new ArrayList<>();

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, VistaNavegacion.this.getString(R.string.url_api),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(VistaLibros.this, response, Toast.LENGTH_SHORT).show();
                        //System.out.println("RESPONSE: "+response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //System.out.println(""+jsonObject.getString("codigo"));
                            if(jsonObject.getString("codigo").equals("OK")) {
                                //System.out.println(""+jsonObject.getString("libros"));
                                JSONArray jsonArray = jsonObject.getJSONArray("libros");

                                for (int i = 0 ; i < jsonArray.length() ; i++) {
                                    JSONObject item = jsonArray.getJSONObject(i);
                                    listaLibros.add(
                                            new Libro(
                                                    item.getString("isbn"),
                                                    item.getString("portada"),
                                                    item.getString("nom_libro"),
                                                    item.getString("autor"),
                                                    item.getString("descripcion"),
                                                    item.getString("editorial"),
                                                    item.getString("anio_publicacion"),
                                                    item.getString("edicion"),
                                                    item.getInt("existencias"),
                                                    item.getString("categoria")
                                            )
                                    );
                                }

                            }else if(jsonObject.getString("codigo").equals("ERROR")) {
                                Toast.makeText(VistaNavegacion.this, jsonObject.getString("mensaje"), Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adaptadorLibros = new AdaptadorLibros(VistaNavegacion.this, listaLibros);
                        binding.rvLibros.setAdapter(adaptadorLibros);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                Toast.makeText(VistaNavegacion.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // En este metodo se hace el envio de valores de la aplicacion al servidor
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("accion", "601");

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(VistaNavegacion.this);
        requestQueue.add(stringRequest);
    }

    private void leerShared() {
        SharedPreferences preferences = this.
                getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
        idUsuario = preferences.getString("idUsuario", "").trim();

    }
}