package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Coordinador;

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

public interface CoordinadorRestServiceInterface {

    @GET("coordinador/list")
    Flowable<List<Coordinador>> getList();

    @GET("coordinador/id/{id}")
    Single<Coordinador> getOneById(@Path("id") Long id);

    @DELETE("coordinador")
    Completable delete(@Body Coordinador entity);

    @POST("coordinador")
    Single<Coordinador> create(@Body Coordinador entity);

    @PUT("coordinador")
    Single<Coordinador> update(@Body Coordinador entity);

}
