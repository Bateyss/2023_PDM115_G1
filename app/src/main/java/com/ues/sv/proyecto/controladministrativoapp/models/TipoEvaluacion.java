package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "TIPOEVALUACION")
public class TipoEvaluacion {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_TIPO_EVALUACION")
    private Long idTipoEvaluacion;

    @ColumnInfo(name = "NOMBRE_TIPO_EVALUACION")
    @NonNull
    private String nombreTipoEvaluacion;

    @Ignore
    public TipoEvaluacion() {
    }

    public TipoEvaluacion(Long idTipoEvaluacion, String nombreTipoEvaluacion) {
        this.idTipoEvaluacion = idTipoEvaluacion;
        this.nombreTipoEvaluacion = nombreTipoEvaluacion;
    }

    public Long getIdTipoEvaluacion() {
        return idTipoEvaluacion;
    }

    public void setIdTipoEvaluacion(Long idTipoEvaluacion) {
        this.idTipoEvaluacion = idTipoEvaluacion;
    }

    public String getNombreTipoEvaluacion() {
        return nombreTipoEvaluacion;
    }

    public void setNombreTipoEvaluacion(String nombreTipoEvaluacion) {
        this.nombreTipoEvaluacion = nombreTipoEvaluacion;
    }
}
