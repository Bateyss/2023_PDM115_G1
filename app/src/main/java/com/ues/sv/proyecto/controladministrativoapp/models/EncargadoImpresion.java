package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.ues.sv.proyecto.controladministrativoapp.utils.Validacion;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "ENCARGADOIMPRESION")
public class EncargadoImpresion {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_ENCARGADO")
    @SerializedName("idEncargado")
    private Long idEncargado;

    @Embedded
    @SerializedName("persona")
    private Persona persona;

    @ColumnInfo(name = "AREA")
    @NonNull
    @Validacion(notNull = true)
    @SerializedName("area")
    private String area;

    @Ignore
    public EncargadoImpresion() {
    }

    public EncargadoImpresion(Long idEncargado, Persona persona, String area) {
        this.idEncargado = idEncargado;
        this.persona = persona;
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EncargadoImpresion that = (EncargadoImpresion) o;

        return idEncargado.equals(that.idEncargado);
    }

    @Override
    public int hashCode() {
        return idEncargado.hashCode();
    }

    public Long getIdEncargado() {
        return idEncargado;
    }

    public void setIdEncargado(Long idEncargado) {
        this.idEncargado = idEncargado;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
