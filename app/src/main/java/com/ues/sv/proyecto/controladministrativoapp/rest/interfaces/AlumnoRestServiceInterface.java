package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Alumno;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlumnoRestServiceInterface {

    @GET("alumno/list")
    Flowable<List<Alumno>> getList();

    @GET("alumno/id/{id}")
    Single<Alumno> getOneById(@Path("id") Long id);

    @DELETE("alumno/{id}")
    Completable delete(@Path("id") Long id);

    @POST("alumno")
    Single<Alumno> create(@Body Alumno entity);

    @PUT("alumno")
    Single<Alumno> update(@Body Alumno entity);
}
