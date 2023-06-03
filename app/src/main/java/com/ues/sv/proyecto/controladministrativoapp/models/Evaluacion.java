package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ues.sv.proyecto.controladministrativoapp.sqlite.Validacion;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "EVALUACION")
public class Evaluacion {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_EVALUACION")
    private Long idEvaluacion;

    @Embedded
    private Curso curso;

    @Embedded
    private TipoEvaluacion tipoEvaluacion;

    @ColumnInfo(name = "NUMERO_EVALUACION")
    @NonNull
    @Validacion(notNull = true)
    private Integer numeroEvaluacion;

    @Ignore
    public Evaluacion() {
    }

    public Evaluacion(Long idEvaluacion, Curso curso, TipoEvaluacion tipoEvaluacion, Integer numeroEvaluacion) {
        this.idEvaluacion = idEvaluacion;
        this.curso = curso;
        this.tipoEvaluacion = tipoEvaluacion;
        this.numeroEvaluacion = numeroEvaluacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Evaluacion that = (Evaluacion) o;

        return idEvaluacion.equals(that.idEvaluacion);
    }

    @Override
    public int hashCode() {
        return idEvaluacion.hashCode();
    }

    public Long getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Long idEvaluacion) {
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
