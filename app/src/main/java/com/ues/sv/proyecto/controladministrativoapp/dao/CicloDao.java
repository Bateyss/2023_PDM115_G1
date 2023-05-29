package com.ues.sv.proyecto.controladministrativoapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface CicloDao {
    @Insert
    Completable insertCiclo(Ciclo ciclo);

    @Update
    Completable updateCiclo(Ciclo ciclo);

    @Delete
    Completable deleteCiclo(Ciclo ciclo);

    @Query("SELECT * FROM CICLO")
    Flowable<List<Ciclo>> findAll();

    @Query("SELECT * FROM CICLO WHERE ID_CICLO = :id")
    Flowable<Ciclo> findById(long id);
}
