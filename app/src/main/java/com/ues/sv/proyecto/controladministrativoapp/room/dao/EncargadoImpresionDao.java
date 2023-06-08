package com.ues.sv.proyecto.controladministrativoapp.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.EncargadoImpresion;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface EncargadoImpresionDao {
    @Insert
    Single<Long> insertEncargadoImpresion(EncargadoImpresion encargadoImpresion);

    @Update
    Completable updateEncargadoImpresion(EncargadoImpresion encargadoImpresion);

    @Delete
    Completable deleteEncargadoImpresion(EncargadoImpresion encargadoImpresion);

    @Query("SELECT * FROM ENCARGADOIMPRESION")
    Flowable<List<EncargadoImpresion>> findAll();

    @Query("SELECT * FROM ENCARGADOIMPRESION WHERE ID_ENCARGADO = :id")
    Flowable<EncargadoImpresion> findById(long id);

    @Query("SELECT * FROM ENCARGADOIMPRESION LIMIT 1")
    Flowable<EncargadoImpresion> findFirst();
}
