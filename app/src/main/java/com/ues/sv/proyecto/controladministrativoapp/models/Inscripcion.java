package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "INSCRIPCION")
public class Inscripcion {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_INSCRIPCION")
    private Long idInscripcion;

    @Embedded
    private Alumno alumno;

    @Embedded
    private Curso curso;

    @Ignore
    public Inscripcion() {
    }

    public Inscripcion(Long idInscripcion, Alumno alumno, Curso curso) {
        this.idInscripcion = idInscripcion;
        this.alumno = alumno;
        this.curso = curso;
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
