package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ues.sv.proyecto.controladministrativoapp.utils.DateConverter;

import java.util.Date;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "REVISION")
public class Revision {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_REVISION")
    private Long idRevision;

    @Embedded
    private Evaluacion evaluacion;

    @ColumnInfo(name = "NUMERO_REVISION")
    @NonNull
    private String numeroRevision;

    @ColumnInfo(name = "OBSERVACIONES_REVISION")
    private String observacionesRevision;

    @ColumnInfo(name = "ESTADO_REVISION")
    @NonNull
    private Integer estadoRevision;

    @ColumnInfo(name = "NOTA_REVISION")
    @NonNull
    private Double notaRevision;

    @ColumnInfo(name = "FECHA_REVISION")
    @TypeConverters(value = {DateConverter.class})
    private Date fechaRevision;

    @Ignore
    public Revision() {
    }

    public Revision(Long idRevision, Evaluacion evaluacion, String numeroRevision, String observacionesRevision, Integer estadoRevision, Double notaRevision, Date fechaRevision) {
        this.idRevision = idRevision;
        this.evaluacion = evaluacion;
        this.numeroRevision = numeroRevision;
        this.observacionesRevision = observacionesRevision;
        this.estadoRevision = estadoRevision;
        this.notaRevision = notaRevision;
        this.fechaRevision = fechaRevision;
    }

    public Long getIdRevision() {
        return idRevision;
    }

    public void setIdRevision(Long idRevision) {
        this.idRevision = idRevision;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getNumeroRevision() {
        return numeroRevision;
    }

    public void setNumeroRevision(String numeroRevision) {
        this.numeroRevision = numeroRevision;
    }

    public String getObservacionesRevision() {
        return observacionesRevision;
    }

    public void setObservacionesRevision(String observacionesRevision) {
        this.observacionesRevision = observacionesRevision;
    }

    public Integer getEstadoRevision() {
        return estadoRevision;
    }

    public void setEstadoRevision(Integer estadoRevision) {
        this.estadoRevision = estadoRevision;
    }

    public Double getNotaRevision() {
        return notaRevision;
    }

    public void setNotaRevision(Double notaRevision) {
        this.notaRevision = notaRevision;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }
}
