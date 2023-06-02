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

@Entity(tableName = "COORDINADOR")
public class Coordinador {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_COORDINADOR")
    private Long idCoordinador;

    @Embedded
    private Persona persona;

    @ColumnInfo(name = "FECHA_INGRESO")
    @NonNull
    @TypeConverters({DateConverter.class})
    private Date fechaIngreso;

    @Ignore
    public Coordinador() {
    }

    public Coordinador(Long idCoordinador, Persona persona, Date fechaIngreso) {
        this.idCoordinador = idCoordinador;
        this.persona = persona;
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinador that = (Coordinador) o;

        return idCoordinador.equals(that.idCoordinador);
    }

    @Override
    public int hashCode() {
        return idCoordinador.hashCode();
    }

    public Long getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(Long idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
