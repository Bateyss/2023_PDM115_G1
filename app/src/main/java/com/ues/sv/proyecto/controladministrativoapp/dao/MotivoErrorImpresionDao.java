package com.ues.sv.proyecto.controladministrativoapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.MotivoErrorImpresion;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MotivoErrorImpresionDao {
    @Insert
    Single<Long> insertMotivoErrorImpresion(MotivoErrorImpresion motivoErrorImpresion);

    @Update
    Completable updateMotivoErrorImpresion(MotivoErrorImpresion motivoErrorImpresion);

    @Delete
    Completable deleteMotivoErrorImpresion(MotivoErrorImpresion motivoErrorImpresion);

    @Query("SELECT * FROM MOTIVOERRORIMPRESION")
    Flowable<List<MotivoErrorImpresion>> findAll();

    @Query("SELECT * FROM MOTIVOERRORIMPRESION WHERE ID_IMPRESION = :id")
    Flowable<List<MotivoErrorImpresion>> findAllByIdImpresion(long id);

    @Query("SELECT * FROM MOTIVOERRORIMPRESION WHERE ID_MOTIVO = :id")
    Flowable<MotivoErrorImpresion> findById(long id);
}
