package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.EncargadoImpresion;

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

public interface EncargadoImpresionRestServiceInterface {

    @GET("encargadoimpresion/list")
    Flowable<List<EncargadoImpresion>> getList();

    @GET("encargadoimpresion/id/{id}")
    Single<EncargadoImpresion> getOneById(@Path("id") Long id);

    @DELETE("encargadoimpresion/{id}")
    Completable delete(@Path("id") Long id);

    @POST("encargadoimpresion")
    Single<EncargadoImpresion> create(@Body EncargadoImpresion entity);

    @PUT("encargadoimpresion")
    Single<EncargadoImpresion> update(@Body EncargadoImpresion entity);
}
