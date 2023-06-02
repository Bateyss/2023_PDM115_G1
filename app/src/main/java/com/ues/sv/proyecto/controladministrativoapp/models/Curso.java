package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "CURSO")
public class Curso {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_CURSO")
    private Long idCurso;

    @Embedded(prefix = "CURSO_")
    private Ciclo ciclo;

    @Embedded(prefix = "CURSO_")
    private Materia materia;

    @Embedded(prefix = "CURSO_DOCENTE_")
    private Docente docente;

    @Embedded(prefix = "CURSO_COORDINADOR_")
    private Coordinador coordinador;

    @Ignore
    public Curso() {
    }

    public Curso(Long idCurso, Ciclo ciclo, Materia materia, Docente docente, Coordinador coordinador) {
        this.idCurso = idCurso;
        this.ciclo = ciclo;
        this.materia = materia;
        this.docente = docente;
        this.coordinador = coordinador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Curso curso = (Curso) o;

        return idCurso.equals(curso.idCurso);
    }

    @Override
    public int hashCode() {
        return idCurso.hashCode();
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Coordinador getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}
