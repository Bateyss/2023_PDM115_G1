package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "PARTICIPANTEREVISION")
public class ParticipanteRevision {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_PARTICIPANTE")
    private Long idParticipante;

    @Embedded
    private Revision revision;

    @Embedded
    private Persona persona;

    @Ignore
    public ParticipanteRevision() {
    }

    public ParticipanteRevision(Long idParticipante, Revision revision, Persona persona) {
        this.idParticipante = idParticipante;
        this.revision = revision;
        this.persona = persona;
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
