package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;

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

public interface CicloRestServiceInterface {

    @GET("ciclo/list")
    Flowable<List<Ciclo>> getList();

    @GET("ciclo/id/{id}")
    Single<Ciclo> getOneById(@Path("id") Long id);

    @DELETE("ciclo/{id}")
    Completable delete(@Path("id") Long id);

    @POST("ciclo")
    Single<Ciclo> create(@Body Ciclo entity);

    @PUT("ciclo")
    Single<Ciclo> update(@Body Ciclo entity);

}
