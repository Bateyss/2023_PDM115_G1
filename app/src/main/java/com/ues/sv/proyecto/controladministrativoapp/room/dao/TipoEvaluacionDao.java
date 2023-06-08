package com.ues.sv.proyecto.controladministrativoapp.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface TipoEvaluacionDao {
    @Insert
    Single<Long> insertTipoEvaluacion(TipoEvaluacion tipoEvaluacion);

    @Update
    Completable updateTipoEvaluacion(TipoEvaluacion tipoEvaluacion);

    @Delete
    Completable deleteTipoEvaluacion(TipoEvaluacion tipoEvaluacion);

    @Query("SELECT * FROM TIPOEVALUACION")
    Flowable<List<TipoEvaluacion>> findAll();

    @Query("SELECT * FROM TIPOEVALUACION WHERE ID_TIPO_EVALUACION = :id")
    Flowable<TipoEvaluacion> findById(long id);
}
