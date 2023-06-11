package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;

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

public interface UsuarioRestServiceInterface {

    @GET("usuario/login/{user}/{pass}")
    Single<Usuario> buscarByUserAndPass(@Path("user") String user, @Path("pass") String pass);

    @GET("usuario/list")
    Flowable<List<Usuario>> getList();

    @GET("usuario/id/{id}")
    Single<Usuario> getOneById(@Path("id") Long id);

    @DELETE("usuario/{id}")
    Completable delete(@Path("id") Long id);

    @POST("usuario")
    Single<Usuario> create(@Body Usuario entity);

    @PUT("usuario")
    Single<Usuario> update(@Body Usuario entity);

}
