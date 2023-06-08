package com.ues.sv.proyecto.controladministrativoapp.models;

import java.util.Objects;

public class Imagen {
    private Long idImagen;
    private String nombre;
    private String ubicacion;

    public Imagen() {
    }

    public Imagen(Long idImagen, String nombre, String ubicacion) {
        this.idImagen = idImagen;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idImagen);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Imagen other = (Imagen) obj;
        return Objects.equals(idImagen, other.idImagen);
    }
}
