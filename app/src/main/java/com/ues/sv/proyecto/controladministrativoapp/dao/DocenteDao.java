package com.ues.sv.proyecto.controladministrativoapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Docente;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface DocenteDao {
    @Insert
    Completable insertDocente(Docente docente);

    @Update
    Completable updateDocente(Docente docente);

    @Delete
    Completable deleteDocente(Docente docente);

    @Query("SELECT * FROM DOCENTE")
    Flowable<List<Docente>> findAll();

    @Query("SELECT * FROM DOCENTE WHERE ID_DOCENTE = :id")
    Flowable<Docente> findById(long id);
}
