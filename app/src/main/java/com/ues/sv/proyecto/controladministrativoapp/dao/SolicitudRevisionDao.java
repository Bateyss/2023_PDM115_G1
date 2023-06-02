package com.ues.sv.proyecto.controladministrativoapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.SolicitudRevision;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface SolicitudRevisionDao {
    @Insert
    Completable insertSolicitudRevision(SolicitudRevision solicitudRevision);

    @Update
    Completable updateSolicitudRevision(SolicitudRevision solicitudRevision);

    @Delete
    Completable deleteSolicitudRevision(SolicitudRevision solicitudRevision);

    @Query("SELECT * FROM SOLICITUDREVISION")
    Flowable<List<SolicitudRevision>> findAll();

    @Query("SELECT * FROM SOLICITUDREVISION WHERE ID_SOLICITUD_REVISION = :id")
    Flowable<SolicitudRevision> findById(long id);

    @Query("SELECT * FROM SOLICITUDREVISION WHERE SOL_REV_INSC_ID_EVALUACION = :id")
    Flowable<List<SolicitudRevision>> findAllByIdEvaluacion(long id);
}
