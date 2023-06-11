package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Impresion;

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

public interface ImpresionRestServiceInterface {

    @GET("impresion/evaluacion/{idEvaluacion}")
    Single<Impresion> getOneByIdEvaluacion(@Path("idEvaluacion") Long idEvaluacion);

    @GET("impresion/list")
    Flowable<List<Impresion>> getList();

    @GET("impresion/id/{id}")
    Single<Impresion> getOneById(@Path("id") Long id);

    @DELETE("impresion/{id}")
    Completable delete(@Path("id") Long id);

    @POST("impresion")
    Single<Impresion> create(@Body Impresion entity);

    @PUT("impresion")
    Single<Impresion> update(@Body Impresion entity);
}
