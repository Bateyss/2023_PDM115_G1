package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.ParticipanteRevision;

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

public interface ParticipanteRevisionRestServiceInterface {

    @GET("participanterevision/list")
    Flowable<List<ParticipanteRevision>> getList();

    @GET("participanterevision/id/{id}")
    Single<ParticipanteRevision> getOneById(@Path("id") Long id);

    @DELETE("participanterevision/{id}")
    Completable delete(@Path("id") Long id);

    @POST("participanterevision")
    Single<ParticipanteRevision> create(@Body ParticipanteRevision entity);

    @PUT("participanterevision")
    Single<ParticipanteRevision> update(@Body ParticipanteRevision entity);

}
