package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Inscripcion;

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

public interface InscripcionRestServiceInterface {

    @GET("inscripcion/list")
    Flowable<List<Inscripcion>> getList();

    @GET("inscripcion/id/{id}")
    Single<Inscripcion> getOneById(@Path("id") Long id);

    @DELETE("inscripcion/{id}")
    Completable delete(@Path("id") Long id);

    @POST("inscripcion")
    Single<Inscripcion> create(@Body Inscripcion entity);

    @PUT("inscripcion")
    Single<Inscripcion> update(@Body Inscripcion entity);
}
