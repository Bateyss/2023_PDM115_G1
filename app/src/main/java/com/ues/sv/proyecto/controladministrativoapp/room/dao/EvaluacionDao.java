package com.ues.sv.proyecto.controladministrativoapp.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Evaluacion;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface EvaluacionDao {
    @Insert
    Single<Long> insertEvaluacion(Evaluacion evaluacion);

    @Update
    Completable updateEvaluacion(Evaluacion evaluacion);

    @Delete
    Completable deleteEvaluacion(Evaluacion evaluacion);

    @Query("SELECT * FROM EVALUACION")
    Flowable<List<Evaluacion>> findAll();

    @Query("SELECT * FROM EVALUACION WHERE ID_EVALUACION = :id")
    Flowable<Evaluacion> findById(long id);
}
