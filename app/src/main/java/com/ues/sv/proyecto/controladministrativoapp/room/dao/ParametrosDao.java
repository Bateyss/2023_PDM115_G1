package com.ues.sv.proyecto.controladministrativoapp.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Parametros;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ParametrosDao {
    @Insert
    Single<Long> insertParametros(Parametros parametros);

    @Update
    Completable updateParametros(Parametros parametros);

    @Delete
    Completable deleteParametros(Parametros parametros);

    @Query("SELECT * FROM PARAMETROS")
    Flowable<List<Parametros>> findAll();

    @Query("SELECT * FROM PARAMETROS WHERE ID_PARAMETRO = :id")
    Flowable<Parametros> findById(long id);

    @Query("SELECT * FROM PARAMETROS WHERE ID_HISTORICO = :id")
    Flowable<Parametros> findByIdHist(int id);
}
