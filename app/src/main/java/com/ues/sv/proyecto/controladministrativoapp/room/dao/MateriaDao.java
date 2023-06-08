package com.ues.sv.proyecto.controladministrativoapp.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Materia;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MateriaDao {
    @Insert
    Single<Long> insertMateria(Materia materia);

    @Update
    Completable updateMateria(Materia materia);

    @Delete
    Completable deleteMateria(Materia materia);

    @Query("SELECT * FROM MATERIA")
    Flowable<List<Materia>> findAll();

    @Query("SELECT * FROM MATERIA WHERE ID_MATERIA = :id")
    Flowable<Materia> findById(long id);
}
