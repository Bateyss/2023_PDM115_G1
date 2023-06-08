package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ues.sv.proyecto.controladministrativoapp.utils.Validacion;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "PERSONA")
public class Persona {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_PERSONA")
    private Long idPersona;

    @ColumnInfo(name = "NOMBRE")
    @NonNull
    @Validacion(notNull = true)
    private String nombre;

    @ColumnInfo(name = "APELLIDO")
    @NonNull
    @Validacion(notNull = true)
    private String apellido;

    @ColumnInfo(name = "IDENTIFICACION")
    @Validacion()
    private String identificacion;

    @ColumnInfo(name = "SEXO")
    @Validacion()
    private String sexo;

    @Ignore
    public Persona() {
    }

    public Persona(Long idPersona, String nombre, String apellido, String identificacion, String sexo) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.sexo = sexo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Persona persona = (Persona) o;

        return idPersona.equals(persona.idPersona);
    }

    @Override
    public int hashCode() {
        return idPersona.hashCode();
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
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
