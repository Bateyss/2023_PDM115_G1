package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.DetalleRevision;

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

public interface DetalleRevisionRestServiceInterface {

    @GET("detallerevision/list")
    Flowable<List<DetalleRevision>> getList();

    @GET("detallerevision/id/{id}")
    Single<DetalleRevision> getOneById(@Path("id") Long id);

    @DELETE("detallerevision")
    Completable delete(@Body DetalleRevision entity);

    @POST("detallerevision")
    Single<DetalleRevision> create(@Body DetalleRevision entity);

    @PUT("detallerevision")
    Single<DetalleRevision> update(@Body DetalleRevision entity);

}
