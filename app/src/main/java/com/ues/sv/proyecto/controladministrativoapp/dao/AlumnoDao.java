package com.ues.sv.proyecto.controladministrativoapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ues.sv.proyecto.controladministrativoapp.models.Alumno;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface AlumnoDao {
    @Insert
    Single<Long> insertAlumno(Alumno alumno);

    @Update
    Completable updateAlumno(Alumno alumno);

    @Delete
    Completable deleteAlumno(Alumno alumno);

    @Query("SELECT * FROM ALUMNO")
    Flowable<List<Alumno>> findAll();

    @Query("SELECT * FROM ALUMNO WHERE ID_ALUMNO = :id")
    Flowable<Alumno> findById(long id);
}
