package com.xcheko51x.bibliotecaapp.Clases;

public class Prestamo {

    private String isbn;
    private String nomLibro;
    private String nomAutor;
    private String nomEditorial;
    private String anioPublicacion;
    private String edicion;
    private String fechaPrestamo;
    private String fechaDevolucion;

    public Prestamo () {}

    public Prestamo(String isbn,
                    String nomLibro,
                    String nomAutor,
                    String nomEditorial,
                    String anioPublicacion,
                    String edicion,
                    String fechaPrestamo,
                    String fechaDevolucion) {
        this.isbn = isbn;
        this.nomLibro = nomLibro;
        this.nomAutor = nomAutor;
        this.nomEditorial = nomEditorial;
        this.anioPublicacion = anioPublicacion;
        this.edicion = edicion;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNomLibro() {
        return nomLibro;
    }

    public void setNomLibro(String nomLibro) {
        this.nomLibro = nomLibro;
    }

    public String getNomAutor() {
        return nomAutor;
    }

    public void setNomAutor(String nomAutor) {
        this.nomAutor = nomAutor;
    }

    public String getNomEditorial() {
        return nomEditorial;
    }

    public void setNomEditorial(String nomEditorial) {
        this.nomEditorial = nomEditorial;
    }

    public String getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(String anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}
