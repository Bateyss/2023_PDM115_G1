package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Parametros;

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

public interface ParametrosRestServiceInterface {

    @GET("parametros/historico/{idHistorico}")
    Single<Parametros> getOneByIdHistorico(@Path("idHistorico") Integer idHistorico);

    @GET("parametros/list")
    Flowable<List<Parametros>> getList();

    @GET("parametros/id/{id}")
    Single<Parametros> getOneById(@Path("id") Long id);

    @DELETE("parametros")
    Completable delete(@Body Parametros entity);

    @POST("parametros")
    Single<Parametros> create(@Body Parametros entity);

    @PUT("parametros")
    Single<Parametros> update(@Body Parametros entity);

}
