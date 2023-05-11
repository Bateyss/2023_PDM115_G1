package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "PERSONA")
public class Persona {
    @PrimaryKey
    @ColumnInfo(name = "ID_PERSONA")
    @NonNull
    private long idPersona;

    @ColumnInfo(name = "NOMBRE")
    @NonNull
    private String nombre;

    @ColumnInfo(name = "APELLIDO")
    @NonNull
    private String apellido;

    @ColumnInfo(name = "IDENTIFICACION")
    @NonNull
    private String identificacion;

    @ColumnInfo(name = "SEXO")
    private String sexo;

    @Ignore
    public Persona() {
    }

    public Persona(long idPersona, String nombre, String apellido, String identificacion, String sexo) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.sexo = sexo;
    }

    public long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}