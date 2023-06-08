package com.ues.sv.proyecto.controladministrativoapp.rest.bin;

import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;

import java.util.List;

/**
 * Interfae generica que ayuda a crear metodos principales del crud en un service
 * @param <ENTITY> generic for rest dto class
 * @param <IDTYPE> generic for rest dto id fieldType
 */
public interface AbsRestService<ENTITY, IDTYPE, REST> {

    void registrarEntidad(ENTITY entity, CallBackDisposableInterface<ENTITY> disposableInterface, REST restServiceInterface);

    void editarEntidad(ENTITY entity, CallBackDisposableInterface<ENTITY> disposableInterface, REST restServiceInterface);

    void eliminarEntidad(ENTITY entity, CallBackVoidInterface voidInterface,REST restServiceInterface);

    void buscarPorId(IDTYPE id, CallBackDisposableInterface<ENTITY> disposableInterface,REST restServiceInterface);

    void obtenerListaEntidad(CallBackDisposableInterface<List<ENTITY>> disposableInterface,REST restServiceInterface);

}
