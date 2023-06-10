package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Evaluacion;

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

public interface EvaluacionRestServiceInterface {

    @GET("evaluacion/list")
    Flowable<List<Evaluacion>> getList();

    @GET("evaluacion/id/{id}")
    Single<Evaluacion> getOneById(@Path("id") Long id);

    @DELETE("evaluacion")
    Completable delete(@Body Evaluacion entity);

    @POST("evaluacion")
    Single<Evaluacion> create(@Body Evaluacion entity);

    @PUT("evaluacion")
    Single<Evaluacion> update(@Body Evaluacion entity);

}
