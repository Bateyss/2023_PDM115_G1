package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Materia;

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

public interface MateriaRestServiceInterface {

    @GET("materia/list")
    Flowable<List<Materia>> getList();

    @GET("materia/id/{id}")
    Single<Materia> getOneById(@Path("id") Long id);

    @DELETE("materia/{id}")
    Completable delete(@Path("id") Long id);

    @POST("materia")
    Single<Materia> create(@Body Materia entity);

    @PUT("materia")
    Single<Materia> update(@Body Materia entity);

}
