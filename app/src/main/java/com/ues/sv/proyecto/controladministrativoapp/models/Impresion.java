package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.ues.sv.proyecto.controladministrativoapp.utils.Validacion;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "IMPRESION")
public class Impresion {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_IMPRESION")
    @SerializedName("idImpresion")
    private Long idImpresion;

    @Embedded
    @SerializedName("evaluacion")
    private Evaluacion evaluacion;

    @Embedded
    @SerializedName("encargadoImpresion")
    private EncargadoImpresion encargadoImpresion;

    @ColumnInfo(name = "FORMATO")
    @NonNull
    @Validacion(notNull = true)
    @SerializedName("formato")
    private String formato;

    @ColumnInfo(name = "OBSERVACIONES_IMPRESION")
    @Validacion()
    @SerializedName("observacionesImpresion")
    private String observacionesImpresion;

    @ColumnInfo(name = "ESTADO_IMPRESION")
    @Validacion()
    @SerializedName("estadoImpresion")
    private Integer estadoImpresion;

    @Ignore
    public Impresion() {
    }

    public Impresion(Long idImpresion, Evaluacion evaluacion, EncargadoImpresion encargadoImpresion, String formato, String observacionesImpresion, Integer estadoImpresion) {
        this.idImpresion = idImpresion;
        this.evaluacion = evaluacion;
        this.encargadoImpresion = encargadoImpresion;
        this.formato = formato;
        this.observacionesImpresion = observacionesImpresion;
        this.estadoImpresion = estadoImpresion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Impresion impresion = (Impresion) o;

        return idImpresion.equals(impresion.idImpresion);
    }

    @Override
    public int hashCode() {
        return idImpresion.hashCode();
    }

    public Long getIdImpresion() {
        return idImpresion;
    }

    public void setIdImpresion(Long idImpresion) {
        this.idImpresion = idImpresion;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public EncargadoImpresion getEncargadoImpresion() {
        return encargadoImpresion;
    }

    public void setEncargadoImpresion(EncargadoImpresion encargadoImpresion) {
        this.encargadoImpresion = encargadoImpresion;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getObservacionesImpresion() {
        return observacionesImpresion;
    }

    public void setObservacionesImpresion(String observacionesImpresion) {
        this.observacionesImpresion = observacionesImpresion;
    }

    public Integer getEstadoImpresion() {
        return estadoImpresion;
    }

    public void setEstadoImpresion(Integer estadoImpresion) {
        this.estadoImpresion = estadoImpresion;
    }
}
