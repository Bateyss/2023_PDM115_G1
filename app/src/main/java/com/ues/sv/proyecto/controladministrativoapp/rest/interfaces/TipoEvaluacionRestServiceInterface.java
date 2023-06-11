package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;

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

public interface TipoEvaluacionRestServiceInterface {

    @GET("tipoevaluacion/list")
    Flowable<List<TipoEvaluacion>> getList();

    @GET("tipoevaluacion/id/{id}")
    Single<TipoEvaluacion> getOneById(@Path("id") Long id);
    
    @DELETE("tipoevaluacion/{id}")
    Completable delete(@Path("id") Long id);

    @POST("tipoevaluacion")
    Single<TipoEvaluacion> create(@Body TipoEvaluacion entity);

    @PUT("tipoevaluacion")
    Single<TipoEvaluacion> update(@Body TipoEvaluacion entity);

}
