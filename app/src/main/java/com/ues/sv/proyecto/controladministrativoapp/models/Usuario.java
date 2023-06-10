package com.ues.sv.proyecto.controladministrativoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.ues.sv.proyecto.controladministrativoapp.utils.Validacion;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "USUARIO")
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_USUARIO")
    @SerializedName("idUsuario")
    private Long idUsuario;

    @Embedded
    @SerializedName("persona")
    private Persona persona;

    @ColumnInfo(name = "USER_NAME")
    @NonNull
    @Validacion(notNull = true)
    @SerializedName("userName")
    private String userName;

    @ColumnInfo(name = "USER_PASS")
    @NonNull
    @Validacion(notNull = true)
    @SerializedName("userPass")
    private String userPass;

    @Ignore
    public Usuario() {
    }

    public Usuario(Long idUsuario, Persona persona, String userName, String userPass) {
        this.idUsuario = idUsuario;
        this.persona = persona;
        this.userName = userName;
        this.userPass = userPass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return idUsuario.equals(usuario.idUsuario);
    }

    @Override
    public int hashCode() {
        return idUsuario.hashCode();
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
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
