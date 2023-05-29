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
    @NonNull
    private long idTipoEvaliacion;

    @ColumnInfo(name = "NOMBRE_TIPO_EVALUACION")
    @NonNull
    private String nombreTipoEvaluacion;

    @Ignore
    public TipoEvaluacion() {
    }

    public TipoEvaluacion(long idTipoEvaliacion, String nombreTipoEvaluacion) {
        this.idTipoEvaliacion = idTipoEvaliacion;
        this.nombreTipoEvaluacion = nombreTipoEvaluacion;
    }

    public long getIdTipoEvaliacion() {
        return idTipoEvaliacion;
    }

    public void setIdTipoEvaliacion(long idTipoEvaliacion) {
        this.idTipoEvaliacion = idTipoEvaliacion;
    }

    public String getNombreTipoEvaluacion() {
        return nombreTipoEvaluacion;
    }

    public void setNombreTipoEvaluacion(String nombreTipoEvaluacion) {
        this.nombreTipoEvaluacion = nombreTipoEvaluacion;
    }
}
