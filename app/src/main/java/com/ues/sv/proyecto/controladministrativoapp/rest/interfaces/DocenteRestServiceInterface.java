package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Docente;

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

public interface DocenteRestServiceInterface {

    @GET("docente/list")
    Flowable<List<Docente>> getList();

    @GET("docente/id/{id}")
    Single<Docente> getOneById(@Path("id") Long id);

    @DELETE("docente")
    Completable delete(@Body Docente entity);

    @POST("docente")
    Single<Docente> create(@Body Docente entity);

    @PUT("docente")
    Single<Docente> update(@Body Docente entity);

}
