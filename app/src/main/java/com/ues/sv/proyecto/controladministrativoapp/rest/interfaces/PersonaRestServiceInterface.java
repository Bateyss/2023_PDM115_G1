package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Persona;

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

public interface PersonaRestServiceInterface {

    @GET("persona/list")
    Flowable<List<Persona>> getList();

    @GET("persona/id/{id}")
    Single<Persona> getOneById(@Path("id") Long id);

    @DELETE("persona")
    Completable delete(@Body Persona entity);

    @POST("persona")
    Single<Persona> create(@Body Persona entity);

    @PUT("persona")
    Single<Persona> update(@Body Persona entity);

}
