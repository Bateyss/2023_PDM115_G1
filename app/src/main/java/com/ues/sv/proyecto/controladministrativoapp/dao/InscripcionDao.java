package com.ues.sv.proyecto.controladministrativoapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Inscripcion;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface InscripcionDao {
    @Insert
    Single<Long> insertInscripcion(Inscripcion inscripcion);

    @Update
    Completable updateInscripcion(Inscripcion inscripcion);

    @Delete
    Completable deleteInscripcion(Inscripcion inscripcion);

    @Query("SELECT * FROM INSCRIPCION")
    Flowable<List<Inscripcion>> findAll();

    @Query("SELECT * FROM INSCRIPCION WHERE ID_INSCRIPCION = :id")
    Flowable<Inscripcion> findById(long id);
}
