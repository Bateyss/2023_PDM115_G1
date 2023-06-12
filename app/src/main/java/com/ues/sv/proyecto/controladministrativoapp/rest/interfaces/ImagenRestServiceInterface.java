package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Imagen;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ImagenRestServiceInterface {

    @GET("imagen/list")
    Flowable<List<Imagen>> getList();

    @GET("imagen/id/{id}")
    Single<Imagen> getOneById(@Path("id") Long id);

    @DELETE("imagen/{id}")
    Completable delete(@Path("id") Long id);

    @Multipart
    @POST("imagen")
    Call<Imagen> create(@Part MultipartBody.Part file);

    @Multipart
    @PUT("imagen/{id}")
    Call<Imagen> update(@Path("id") Long id, @Part MultipartBody.Part file);

}
