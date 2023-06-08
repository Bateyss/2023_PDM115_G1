package com.ues.sv.proyecto.controladministrativoapp.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Curso;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CursoDao {
    @Insert
    Single<Long> insertCurso(Curso curso);

    @Update
    Completable updateCurso(Curso curso);

    @Delete
    Completable deleteCurso(Curso curso);

    @Query("SELECT * FROM CURSO")
    Flowable<List<Curso>> findAll();

    @Query("SELECT * FROM CURSO WHERE ID_CURSO = :id")
    Flowable<Curso> findById(long id);
}
