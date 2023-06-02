package com.ues.sv.proyecto.controladministrativoapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Alumno;
import com.ues.sv.proyecto.controladministrativoapp.models.DetalleRevision;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface DetalleRevisionDao {
    @Insert
    Single<Long> insertDetalleRevision(DetalleRevision detalleRevision);

    @Update
    Completable updateDetalleRevision(DetalleRevision detalleRevision);

    @Delete
    Completable deleteDetalleRevision(DetalleRevision detalleRevision);

    @Query("SELECT * FROM DetalleRevision")
    Flowable<List<DetalleRevision>> findAll();

    @Query("SELECT * FROM DETALLEREVISION WHERE ID_DETALLE = :id")
    Flowable<DetalleRevision> findById(long id);
}
