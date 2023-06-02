package com.ues.sv.proyecto.controladministrativoapp.service.interfaces;

import java.util.List;

/**
 * Interfae generica que ayuda a crear metodos principales del crud en un service
 * @param <ENTITY> generic {@link androidx.room.Entity} class
 * @param <IDTYPE> generic {@link androidx.room.PrimaryKey} fieldType of the {@literal ENTITY}
 */
public interface AbsServiceInterface<ENTITY, IDTYPE, DAO> {

    void registrarEntidad(ENTITY entity, CallBackDisposableInterface<IDTYPE> disposableInterface,DAO dao);

    void editarEntidad(ENTITY entity, CallBackVoidInterface voidInterface,DAO dao);

    void eliminarEntidad(ENTITY entity, CallBackVoidInterface voidInterface,DAO dao);

    void buscarPorId(IDTYPE id, CallBackDisposableInterface<ENTITY> disposableInterface,DAO dao);

    void obtenerListaEntidad(CallBackDisposableInterface<List<ENTITY>> disposableInterface,DAO dao);

}
