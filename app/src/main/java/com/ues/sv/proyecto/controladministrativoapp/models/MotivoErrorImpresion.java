package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.ues.sv.proyecto.controladministrativoapp.utils.Validacion;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "MOTIVOERRORIMPRESION")
public class MotivoErrorImpresion {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_MOTIVO")
    @SerializedName("idMotivo")
    private Long idMotivo;

    @Embedded
    @SerializedName("impresion")
    private Impresion impresion;

    @ColumnInfo(name = "DESCRIPCION_MOTIVO")
    @NonNull
    @Validacion(notNull = true)
    @SerializedName("descripcionMotivo")
    private String descripcionMotivo;

    @Ignore
    public MotivoErrorImpresion() {
    }

    public MotivoErrorImpresion(Long idMotivo, Impresion impresion, String descripcionMotivo) {
        this.idMotivo = idMotivo;
        this.impresion = impresion;
        this.descripcionMotivo = descripcionMotivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MotivoErrorImpresion that = (MotivoErrorImpresion) o;

        return idMotivo.equals(that.idMotivo);
    }

    @Override
    public int hashCode() {
        return idMotivo.hashCode();
    }

    public Long getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(Long idMotivo) {
        this.idMotivo = idMotivo;
    }

    public Impresion getImpresion() {
        return impresion;
    }

    public void setImpresion(Impresion impresion) {
        this.impresion = impresion;
    }

    public String getDescripcionMotivo() {
        return descripcionMotivo;
    }

    public void setDescripcionMotivo(String descripcionMotivo) {
        this.descripcionMotivo = descripcionMotivo;
    }
}
