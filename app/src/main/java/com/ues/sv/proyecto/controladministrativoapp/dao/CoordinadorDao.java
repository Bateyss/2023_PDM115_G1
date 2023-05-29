package com.ues.sv.proyecto.controladministrativoapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Coordinador;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface CoordinadorDao {
    @Insert
    Completable insertCoordinador(Coordinador coordinador);

    @Update
    Completable updateCoordinador(Coordinador coordinador);

    @Delete
    Completable deleteCoordinador(Coordinador coordinador);

    @Query("SELECT * FROM COORDINADOR")
    Flowable<List<Coordinador>> findAll();

    @Query("SELECT * FROM COORDINADOR WHERE ID_COORDINADOR = :id")
    Flowable<Coordinador> findById(long id);
}
