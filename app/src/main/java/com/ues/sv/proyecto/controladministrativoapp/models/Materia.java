package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ues.sv.proyecto.controladministrativoapp.utils.Validacion;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "MATERIA")
public class Materia {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_MATERIA")
    private Long idMateria;

    @ColumnInfo(name = "NOMBRE_MATERIA")
    @NonNull
    @Validacion(notNull = true)
    private String nombreMateria;

    @Ignore
    public Materia() {
    }

    public Materia(Long idMateria, String nombreMateria) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
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
}
