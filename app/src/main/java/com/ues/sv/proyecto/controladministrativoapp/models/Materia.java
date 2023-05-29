package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "MATERIA")
public class Materia {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_MATERIA")
    @NonNull
    private long idMateria;

    @ColumnInfo(name = "NOMBRE_MATERIA")
    @NonNull
    private String nombreMateria;

    @Ignore
    public Materia() {
    }

    public Materia(long idMateria, String nombreMateria) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
    }

    public long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(long idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }
}
