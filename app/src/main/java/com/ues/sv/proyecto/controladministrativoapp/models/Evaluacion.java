package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "EVALUACION")
public class Evaluacion {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_EVALUACION")
    @NonNull
    private long idEvaluacion;

    @Embedded
    private Curso curso;

    @Embedded
    private TipoEvaluacion tipoEvaluacion;

    @ColumnInfo(name = "NUMERO_EVALUACION")
    @NonNull
    private Integer numeroEvaluacion;

    @Ignore
    public Evaluacion() {
    }

    public Evaluacion(long idEvaluacion, Curso curso, TipoEvaluacion tipoEvaluacion, Integer numeroEvaluacion) {
        this.idEvaluacion = idEvaluacion;
        this.curso = curso;
        this.tipoEvaluacion = tipoEvaluacion;
        this.numeroEvaluacion = numeroEvaluacion;
    }

    public long getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(long idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public TipoEvaluacion getTipoEvaluacion() {
        return tipoEvaluacion;
    }

    public void setTipoEvaluacion(TipoEvaluacion tipoEvaluacion) {
        this.tipoEvaluacion = tipoEvaluacion;
    }

    public Integer getNumeroEvaluacion() {
        return numeroEvaluacion;
    }

    public void setNumeroEvaluacion(Integer numeroEvaluacion) {
        this.numeroEvaluacion = numeroEvaluacion;
    }
}
