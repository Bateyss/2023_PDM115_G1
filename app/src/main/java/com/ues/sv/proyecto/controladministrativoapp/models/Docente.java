package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.ues.sv.proyecto.controladministrativoapp.utils.Validacion;
import com.ues.sv.proyecto.controladministrativoapp.utils.DateConverter;

import java.util.Date;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "DOCENTE")
public class Docente {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_DOCENTE")
    @SerializedName("idDocente")
    private Long idDocente;

    @Embedded
    @SerializedName("persona")
    private Persona persona;

    @ColumnInfo(name = "FECHA_INGRESO")
    @NonNull
    @TypeConverters({DateConverter.class})
    @Validacion(notNull = true)
    @SerializedName("fechaIngreso")
    private Date fechaIngreso;

    @Ignore
    public Docente() {
    }

    public Docente(Long idDocente, Persona persona, Date fechaIngreso) {
        this.idDocente = idDocente;
        this.persona = persona;
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Docente docente = (Docente) o;

        return idDocente.equals(docente.idDocente);
    }

    @Override
    public int hashCode() {
        return idDocente.hashCode();
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
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
