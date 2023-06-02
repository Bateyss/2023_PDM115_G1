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

@Entity(tableName = "SOLICITUDREVISION")
public class SolicitudRevision {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_SOLICITUD_REVISION")
    private Long idSolicitudRevision;

    @Embedded(prefix = "SOL_REV_")
    private Inscripcion inscripcion;

    @Embedded(prefix = "SOL_REV_INSC_")
    private Evaluacion evaluacion;

    @ColumnInfo(name = "MOTIVO")
    @NonNull
    private String motivo;

    @ColumnInfo(name = "FECHA_CREACION")
    @NonNull
    @TypeConverters(DateConverter.class)
    private Date fechaCreacion;

    @ColumnInfo(name = "ESTADO_SOLICITUD")
    @NonNull
    private Integer estadoSolicitud;

    @Ignore
    public SolicitudRevision() {
    }

    public SolicitudRevision(Long idSolicitudRevision, Inscripcion inscripcion, Evaluacion evaluacion, String motivo, Date fechaCreacion, Integer estadoSolicitud) {
        this.idSolicitudRevision = idSolicitudRevision;
        this.inscripcion = inscripcion;
        this.evaluacion = evaluacion;
        this.motivo = motivo;
        this.fechaCreacion = fechaCreacion;
        this.estadoSolicitud = estadoSolicitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SolicitudRevision that = (SolicitudRevision) o;

        return idSolicitudRevision.equals(that.idSolicitudRevision);
    }

    @Override
    public int hashCode() {
        return idSolicitudRevision.hashCode();
    }

    public Long getIdSolicitudRevision() {
        return idSolicitudRevision;
    }

    public void setIdSolicitudRevision(Long idSolicitudRevision) {
        this.idSolicitudRevision = idSolicitudRevision;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(Integer estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }
}
