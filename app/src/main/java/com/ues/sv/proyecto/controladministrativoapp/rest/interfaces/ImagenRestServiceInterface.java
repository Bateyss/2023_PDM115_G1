package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Imagen;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ImagenRestServiceInterface {

    String BASE_URL = ApiData.API1_URL.concat("/imagen");

    @GET("/list")
    Flowable<List<Imagen>> getList();

    @GET("/id/{id}")
    Single<Imagen> getOneById(@Path("id") Long id);

    @DELETE()
    Completable delete(@Body Imagen imagen);

    @POST()
    Single<Imagen> create(@Body Imagen imagen);

}
