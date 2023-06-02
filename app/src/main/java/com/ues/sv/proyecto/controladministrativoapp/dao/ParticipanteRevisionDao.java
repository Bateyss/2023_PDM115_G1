package com.ues.sv.proyecto.controladministrativoapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.ParticipanteRevision;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ParticipanteRevisionDao {
    @Insert
    Single<Long> insertParticipanteRevision(ParticipanteRevision participanteRevision);

    @Update
    Completable updateParticipanteRevision(ParticipanteRevision participanteRevision);

    @Delete
    Completable deleteParticipanteRevision(ParticipanteRevision participanteRevision);

    @Query("SELECT * FROM PARTICIPANTEREVISION")
    Flowable<List<ParticipanteRevision>> findAll();

    @Query("SELECT * FROM PARTICIPANTEREVISION WHERE ID_PARTICIPANTE = :id")
    Flowable<ParticipanteRevision> findById(long id);
}
