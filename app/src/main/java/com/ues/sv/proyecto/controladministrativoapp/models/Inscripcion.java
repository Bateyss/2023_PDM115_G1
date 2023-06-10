package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "INSCRIPCION")
public class Inscripcion {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_INSCRIPCION")
    @SerializedName("idInscripcion")
    private Long idInscripcion;

    @Embedded
    @SerializedName("alumno")
    private Alumno alumno;

    @Embedded
    @SerializedName("curso")
    private Curso curso;

    @Ignore
    public Inscripcion() {
    }

    public Inscripcion(Long idInscripcion, Alumno alumno, Curso curso) {
        this.idInscripcion = idInscripcion;
        this.alumno = alumno;
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inscripcion that = (Inscripcion) o;

        return idInscripcion.equals(that.idInscripcion);
    }

    @Override
    public int hashCode() {
        return idInscripcion.hashCode();
    }

    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
