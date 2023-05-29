package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "USUARIO")
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_USUARIO")
    @NonNull
    private long idUsuario;

    @Embedded
    private Persona persona;

    @ColumnInfo(name = "USER_NAME")
    @NonNull
    private String userName;

    @ColumnInfo(name = "USER_PASS")
    @NonNull
    private String userPass;

    @Ignore
    public Usuario() {
    }

    public Usuario(long idUsuario, Persona persona, String userName, String userPass) {
        this.idUsuario = idUsuario;
        this.persona = persona;
        this.userName = userName;
        this.userPass = userPass;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
