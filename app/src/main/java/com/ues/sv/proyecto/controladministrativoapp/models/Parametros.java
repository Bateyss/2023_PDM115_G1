package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.ues.sv.proyecto.controladministrativoapp.utils.Validacion;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "PARAMETROS")
public class Parametros {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_PARAMETRO")
    @SerializedName("idParametro")
    private Long idParametro;

    @ColumnInfo(name = "NOMBRE")
    @NonNull
    @Validacion(notNull = true)
    @SerializedName("nombre")
    private String nombre;

    @ColumnInfo(name = "VALOR")
    @NonNull
    @Validacion(notNull = true)
    @SerializedName("valor")
    private String valor;

    @ColumnInfo(name = "ID_HISTORICO")
    @NonNull
    @Validacion(notNull = true)
    @SerializedName("idHistorico")
    private Integer idHistorico;

    @Ignore
    public Parametros() {
    }

    public Parametros(Long idParametro, String nombre, String valor, Integer idHistorico) {
        this.idParametro = idParametro;
        this.nombre = nombre;
        this.valor = valor;
        this.idHistorico = idHistorico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parametros that = (Parametros) o;

        return idParametro.equals(that.idParametro);
    }

    @Override
    public int hashCode() {
        return idParametro.hashCode();
    }

    public Long getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Long idParametro) {
        this.idParametro = idParametro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Integer idHistorico) {
        this.idHistorico = idHistorico;
    }
}
