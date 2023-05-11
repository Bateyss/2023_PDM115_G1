package com.ues.sv.proyecto.controladministrativoapp.service.interfaces;

import java.util.List;

/**
 * Interfae generica que ayuda a crear metodos principales del crud en un service
 * @param <ENTITY> generic {@link androidx.room.Entity} class
 * @param <IDTYPE> generic {@link androidx.room.PrimaryKey} fieldType of the {@literal ENTITY}
 */
public interface ServiceInterface<ENTITY, IDTYPE> {

    void registrarEntidad(ENTITY entity, CallBackVoidInterface voidInterface);

    void editarEntidad(ENTITY entity, CallBackVoidInterface voidInterface);

    void eliminarEntidad(ENTITY entity, CallBackVoidInterface voidInterface);

    void buscarPorId(IDTYPE id, CallBackDisposableInterface<ENTITY> disposableInterface);

    void obtenerListaEntidad(CallBackDisposableInterface<List<ENTITY>> disposableInterface);

}
