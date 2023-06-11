package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Curso;

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

public interface CursoRestServiceInterface {

    @GET("curso/list")
    Flowable<List<Curso>> getList();

    @GET("curso/id/{id}")
    Single<Curso> getOneById(@Path("id") Long id);

    @DELETE("curso/{id}")
    Completable delete(@Path("id") Long id);

    @POST("curso")
    Single<Curso> create(@Body Curso entity);

    @PUT("curso")
    Single<Curso> update(@Body Curso entity);

}
