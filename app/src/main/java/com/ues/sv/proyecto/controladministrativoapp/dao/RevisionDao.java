package com.ues.sv.proyecto.controladministrativoapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Revision;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface RevisionDao {
    @Insert
    Completable insertRevision(Revision revision);

    @Update
    Completable updateRevision(Revision revision);

    @Delete
    Completable deleteRevision(Revision revision);

    @Query("SELECT * FROM REVISION")
    Flowable<List<Revision>> findAll();

    @Query("SELECT * FROM REVISION WHERE ID_REVISION = :id")
    Flowable<Revision> findById(long id);
}
