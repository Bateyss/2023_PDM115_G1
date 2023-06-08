package com.ues.sv.proyecto.controladministrativoapp.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UsuarioDao {

    @Insert
    Single<Long> insertUsuario(Usuario usuario);

    @Update
    Completable updateUsuario(Usuario usuario);

    @Delete
    Completable deleteUsuario(Usuario usuario);

    @Query("SELECT * FROM USUARIO")
    Flowable<List<Usuario>> findAll();

    @Query("SELECT * FROM USUARIO WHERE ID_USUARIO = :id")
    Flowable<Usuario> findById(long id);

    @Query("SELECT * FROM USUARIO WHERE USER_NAME = :username AND USER_PASS = :userpass")
    Flowable<Usuario> findByUserAndPass(String username, String userpass);
}
