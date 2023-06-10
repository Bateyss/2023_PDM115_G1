package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "PARTICIPANTEREVISION")
public class ParticipanteRevision {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_PARTICIPANTE")
    @SerializedName("idParticipante")
    private Long idParticipante;

    @Embedded
    @SerializedName("revision")
    private Revision revision;

    @Embedded
    @SerializedName("persona")
    private Persona persona;

    @Ignore
    public ParticipanteRevision() {
    }

    public ParticipanteRevision(Long idParticipante, Revision revision, Persona persona) {
        this.idParticipante = idParticipante;
        this.revision = revision;
        this.persona = persona;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParticipanteRevision that = (ParticipanteRevision) o;

        return idParticipante.equals(that.idParticipante);
    }

    @Override
    public int hashCode() {
        return idParticipante.hashCode();
    }

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
    }

    public Revision getRevision() {
        return revision;
    }

    public void setRevision(Revision revision) {
        this.revision = revision;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
