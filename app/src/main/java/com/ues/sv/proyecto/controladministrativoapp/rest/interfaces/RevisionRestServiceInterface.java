package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Revision;

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

public interface RevisionRestServiceInterface {

    @GET("revision/list")
    Flowable<List<Revision>> getList();

    @GET("revision/id/{id}")
    Single<Revision> getOneById(@Path("id") Long id);

    @DELETE("revision/{id}")
    Completable delete(@Path("id") Long id);

    @POST("revision")
    Single<Revision> create(@Body Revision entity);

    @PUT("revision")
    Single<Revision> update(@Body Revision entity);

}
