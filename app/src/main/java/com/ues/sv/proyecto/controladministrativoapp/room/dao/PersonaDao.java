package com.ues.sv.proyecto.controladministrativoapp.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PersonaDao {
    @Insert
    Single<Long> insertPersona(Persona persona);

    @Update
    Completable updatePersona(Persona persona);

    @Delete
    Completable deletePersona(Persona persona);

    @Query("SELECT * FROM PERSONA")
    Flowable<List<Persona>> findAll();

    @Query("SELECT * FROM PERSONA WHERE ID_PERSONA = :id")
    Flowable<Persona> findById(long id);
}
