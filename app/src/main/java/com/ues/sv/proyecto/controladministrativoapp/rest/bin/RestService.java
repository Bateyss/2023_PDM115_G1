package com.ues.sv.proyecto.controladministrativoapp.rest.bin;

import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;

/**
 * Interfae generica que ayuda a crear metodos principales del crud en un service
 * @param <ENTITY> generic for rest dto class
 * @param <IDTYPE> generic for rest dto id fieldType
 */
public interface RestService<ENTITY, IDTYPE> {

    void registrarEntidad(ENTITY entity, CallBackDisposableInterface disposableInterface);

    void editarEntidad(ENTITY entity, CallBackDisposableInterface disposableInterface);

    void eliminarEntidad(ENTITY entity, CallBackVoidInterface voidInterface);

    void buscarPorId(IDTYPE id, CallBackDisposableInterface disposableInterface);

    void obtenerListaEntidad(CallBackDisposableInterface disposableInterface);

}
