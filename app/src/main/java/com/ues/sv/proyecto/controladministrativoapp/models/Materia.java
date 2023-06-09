package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.ues.sv.proyecto.controladministrativoapp.utils.Validacion;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "MATERIA")
public class Materia {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_MATERIA")
    @SerializedName("idMateria")
    private Long idMateria;

    @ColumnInfo(name = "NOMBRE_MATERIA")
    @NonNull
    @Validacion(notNull = true)
    @SerializedName("nombreMateria")
    private String nombreMateria;

    @SerializedName("idImagen")
    @Validacion
    private Integer idImagen;

    @Ignore
    public Materia() {
    }

    public Materia(Long idMateria, String nombreMateria) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
    }

    @Ignore
    public Materia(Long idMateria, String nombreMateria, Integer idImagen) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.idImagen = idImagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Materia materia = (Materia) o;

        return idMateria.equals(materia.idMateria);
    }

    @Override
    public int hashCode() {
        return idMateria.hashCode();
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public Integer getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

}
