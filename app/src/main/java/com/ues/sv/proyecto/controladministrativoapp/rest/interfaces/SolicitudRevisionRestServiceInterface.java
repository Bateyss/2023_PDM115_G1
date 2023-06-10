package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.SolicitudRevision;

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

public interface SolicitudRevisionRestServiceInterface {

    @GET("solicitudrevision/list/evaluacion/{id}")
    Flowable<List<SolicitudRevision>> getListByIdEvaluacion(@Path("id") Long id);

    @GET("solicitudrevision/list")
    Flowable<List<SolicitudRevision>> getList();

    @GET("solicitudrevision/id/{id}")
    Single<SolicitudRevision> getOneById(@Path("id") Long id);

    @DELETE("solicitudrevision")
    Completable delete(@Body SolicitudRevision entity);

    @POST("solicitudrevision")
    Single<SolicitudRevision> create(@Body SolicitudRevision entity);

    @PUT("solicitudrevision")
    Single<SolicitudRevision> update(@Body SolicitudRevision entity);

}
