package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ues.sv.proyecto.controladministrativoapp.sqlite.Validacion;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "DETALLEREVISION")
public class DetalleRevision {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_DETALLE")
    private Long idDetalle;

    @Embedded
    private Revision revision;

    @ColumnInfo(name = "NOTA_ANTERIOR")
    @NonNull
    @Validacion(notNull = true)
    private Double notaAnterior;

    @ColumnInfo(name = "NOTA_ACTUAL")
    @NonNull
    @Validacion(notNull = true)
    private Double notaActual;

    @Ignore
    public DetalleRevision() {
    }

    public DetalleRevision(Long idDetalle, Revision revision, Double notaAnterior, Double notaActual) {
        this.idDetalle = idDetalle;
        this.revision = revision;
        this.notaAnterior = notaAnterior;
        this.notaActual = notaActual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetalleRevision that = (DetalleRevision) o;

        return idDetalle.equals(that.idDetalle);
    }

    @Override
    public int hashCode() {
        return idDetalle.hashCode();
    }

    public Long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Revision getRevision() {
        return revision;
    }

    public void setRevision(Revision revision) {
        this.revision = revision;
    }

    public Double getNotaAnterior() {
        return notaAnterior;
    }

    public void setNotaAnterior(Double notaAnterior) {
        this.notaAnterior = notaAnterior;
    }

    public Double getNotaActual() {
        return notaActual;
    }

    public void setNotaActual(Double notaActual) {
        this.notaActual = notaActual;
    }
}
