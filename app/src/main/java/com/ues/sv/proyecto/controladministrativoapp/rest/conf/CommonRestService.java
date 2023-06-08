package com.ues.sv.proyecto.controladministrativoapp.rest.conf;

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

public interface CommonRestService<ENTITY> {

    @GET("/list")
    Flowable<List<ENTITY>> getList();

    @GET("/id/{id}")
    Single<ENTITY> getOneById(@Path("id") Long id);

    @DELETE()
    Completable delete(@Body ENTITY entity);

    @POST()
    Single<ENTITY> create(@Body ENTITY entity);

    @PUT()
    Single<ENTITY> update(@Body ENTITY entity);

}
