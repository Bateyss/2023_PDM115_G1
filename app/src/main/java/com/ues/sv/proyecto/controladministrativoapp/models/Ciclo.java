package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "CICLO")
public class Ciclo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_CICLO")
    private Long idCiclo;

    @ColumnInfo(name = "NUMERO_CICLO")
    @NonNull
    private String numeroCiclo;

    @ColumnInfo(name = "NUMERO_ANIO")
    @NonNull
    private String numeroAnio;

    @Ignore
    public Ciclo() {
    }

    public Ciclo(Long idCiclo, String numeroCiclo, String numeroAnio) {
        this.idCiclo = idCiclo;
        this.numeroCiclo = numeroCiclo;
        this.numeroAnio = numeroAnio;
    }

    public Long getIdCiclo() {
        return idCiclo;
    }

    public void setIdCiclo(Long idCiclo) {
        this.idCiclo = idCiclo;
    }

    public String getNumeroCiclo() {
        return numeroCiclo;
    }

    public void setNumeroCiclo(String numeroCiclo) {
        this.numeroCiclo = numeroCiclo;
    }

    public String getNumeroAnio() {
        return numeroAnio;
    }

    public void setNumeroAnio(String numeroAnio) {
        this.numeroAnio = numeroAnio;
    }
}
