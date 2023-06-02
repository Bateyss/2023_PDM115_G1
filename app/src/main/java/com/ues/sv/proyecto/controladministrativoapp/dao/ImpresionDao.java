package com.ues.sv.proyecto.controladministrativoapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Impresion;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ImpresionDao {
    @Insert
    Single<Long> insertImpresion(Impresion impresion);

    @Update
    Completable updateImpresion(Impresion impresion);

    @Delete
    Completable deleteImpresion(Impresion impresion);

    @Query("SELECT * FROM IMPRESION")
    Flowable<List<Impresion>> findAll();

    @Query("SELECT * FROM IMPRESION WHERE ID_IMPRESION = :id")
    Flowable<Impresion> findById(long id);

    @Query("SELECT * FROM IMPRESION WHERE ID_EVALUACION = :id")
    Flowable<Impresion> findByIdEvaluacion(long id);
}
