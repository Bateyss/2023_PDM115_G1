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
    @NonNull
    private long idCoordinador;

    @Embedded
    private Persona persona;

    @ColumnInfo(name = "FECHA_INGRESO")
    @NonNull
    @TypeConverters({DateConverter.class})
    private Date fechaIngreso;

    @Ignore
    public Coordinador() {
    }

    public Coordinador(long idCoordinador, Persona persona, Date fechaIngreso) {
        this.idCoordinador = idCoordinador;
        this.persona = persona;
        this.fechaIngreso = fechaIngreso;
    }

    public long getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(long idCoordinador) {
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
