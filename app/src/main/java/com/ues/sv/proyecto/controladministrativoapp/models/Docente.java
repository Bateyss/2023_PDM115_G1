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

@Entity(tableName = "DOCENTE")
public class Docente {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_DOCENTE")
    @NonNull
    private long idDocente;

    @Embedded
    private Persona persona;

    @ColumnInfo(name = "FECHA_INGRESO")
    @NonNull
    @TypeConverters({DateConverter.class})
    private Date fechaIngreso;

    @Ignore
    public Docente() {
    }

    public Docente(long idDocente, Persona persona, Date fechaIngreso) {
        this.idDocente = idDocente;
        this.persona = persona;
        this.fechaIngreso = fechaIngreso;
    }

    public long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(long idDocente) {
        this.idDocente = idDocente;
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
