package com.xcheko51x.bibliotecaapp.Adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xcheko51x.bibliotecaapp.Clases.Libro;
import com.xcheko51x.bibliotecaapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorLibros extends RecyclerView.Adapter<AdaptadorLibros.ViewHolder> {

    Context context;
    List<Libro> listaLibros = new ArrayList<>();

    public AdaptadorLibros(Context context, List<Libro> listaLibros) {
        this.context = context;
        this.listaLibros = listaLibros;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_libros, null, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (listaLibros.get(position).getExistencias() == 0) {
            holder.cvLibro.setCardBackgroundColor(context.getResources().getColor(R.color.rojo_oscuro));
        } else {
            holder.cvLibro.setCardBackgroundColor(context.getResources().getColor(R.color.verde_oscuro));
        }

        Glide
                .with(context)
                .load(context.getResources().getString(R.string.url_portadas) + listaLibros.get(position).getPortada())
                .placeholder(context.getResources().getDrawable(R.drawable.icon_falta_foto))
                .into(holder.ivPortada);

        //holder.tvISBN.setText(listaLibros.get(position).getIsbn());
        holder.tvNomLibro.setText(listaLibros.get(position).getNom_libro());
        holder.tvAutor.setText(listaLibros.get(position).getAutor());

        holder.btnInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogInformacion(position);
            }
        });

    }

    private void alertDialogInformacion(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View vista = layoutInflater.inflate(R.layout.alert_dialog_info_libro, null);

        TextView tvISBN = vista.findViewById(R.id.tvISBN);
        TextView tvNomLibro = vista.findViewById(R.id.tvNomLibro);
        TextView tvNomAutor = vista.findViewById(R.id.tvNomAutor);
        TextView tvCategoria = vista.findViewById(R.id.tvCategoria);
        TextView tvDescripcion = vista.findViewById(R.id.tvDescripcion);
        TextView tvEditorial = vista.findViewById(R.id.tvEditorial);
        TextView tvAnioPublicacion = vista.findViewById(R.id.tvAnioPublicacion);
        TextView tvEdicion = vista.findViewById(R.id.tvEdicion);
        TextView tvExistencias = vista.findViewById(R.id.tvExistencias);

        tvISBN.setText("ISBN: " + listaLibros.get(position).getIsbn());
        tvNomLibro.setText("LIBRO: " + listaLibros.get(position).getNom_libro());
        tvNomAutor.setText("AUTOR: " + listaLibros.get(position).getAutor());
        tvCategoria.setText("CATEGORIA: " + listaLibros.get(position).getCategoria());
        tvDescripcion.setText("DESCRIPCIÓN: " + listaLibros.get(position).getDescripcion());
        tvEditorial.setText("EDITORIAL: " + listaLibros.get(position).getEditorial());
        tvAnioPublicacion.setText("AÑO DE PUBLICACIÓN: " + listaLibros.get(position).getAnio_publicacion());
        tvEdicion.setText("EDICIÓN: " + listaLibros.get(position).getEdicion());
        tvExistencias.setText("EXISTENCIAS: " + listaLibros.get(position).getExistencias());

        builder.setView(vista);
        builder.create();
        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return listaLibros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cvLibro;
        ImageView ivPortada;
        TextView tvNomLibro, tvAutor;
        Button btnInformacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvLibro = itemView.findViewById(R.id.cvLibro);
            ivPortada = itemView.findViewById(R.id.ivPortada);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvNomLibro = itemView.findViewById(R.id.tvNomLibro);
            btnInformacion = itemView.findViewById(R.id.btnInformacion);
        }
    }

    public void filtrar(List<Libro> filtroLibros) {
        this.listaLibros = filtroLibros;
        notifyDataSetChanged();
    }
}

