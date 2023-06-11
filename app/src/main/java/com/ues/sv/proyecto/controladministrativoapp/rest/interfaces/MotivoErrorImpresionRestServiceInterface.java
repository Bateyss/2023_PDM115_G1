package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Impresion;
import com.ues.sv.proyecto.controladministrativoapp.models.MotivoErrorImpresion;

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

public interface MotivoErrorImpresionRestServiceInterface {

    @GET("motivoerrorimpresion/list/impresion")
    Flowable<List<MotivoErrorImpresion>> getListByImpresion(@Body Impresion impresion);

    @GET("motivoerrorimpresion/list")
    Flowable<List<MotivoErrorImpresion>> getList();

    @GET("motivoerrorimpresion/id/{id}")
    Single<MotivoErrorImpresion> getOneById(@Path("id") Long id);

    @DELETE("motivoerrorimpresion/{id}")
    Completable delete(@Path("id") Long id);

    @POST("motivoerrorimpresion")
    Single<MotivoErrorImpresion> create(@Body MotivoErrorImpresion entity);

    @PUT("motivoerrorimpresion")
    Single<MotivoErrorImpresion> update(@Body MotivoErrorImpresion entity);

}
