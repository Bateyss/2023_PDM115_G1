package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ues.sv.proyecto.controladministrativoapp.sqlite.Validacion;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "ALUMNO")
public class Alumno {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_ALUMNO")
    private Long idAlumno;

    @Embedded
    private Persona persona;

    @ColumnInfo(name = "CARNET")
    @NonNull
    @Validacion(notNull = true)
    private String carnet;

    @Ignore
    public Alumno() {
    }

    public Alumno(Long idAlumno, Persona persona, String carnet) {
        this.idAlumno = idAlumno;
        this.persona = persona;
        this.carnet = carnet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alumno alumno = (Alumno) o;

        return idAlumno.equals(alumno.idAlumno);
    }

    @Override
    public int hashCode() {
        return idAlumno.hashCode();
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }
}
