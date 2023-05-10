package com.ues.sv.proyecto.controladministrativoapp.service.interfaces;

import java.util.List;

public interface ServiceInterface<ENTITY, IDTYPE> {

    void registrarEntidad(ENTITY entity, CallBackVoidInterface voidInterface);

    void editarEntidad(ENTITY entity, CallBackVoidInterface voidInterface);

    void eliminarEntidad(ENTITY entity, CallBackVoidInterface voidInterface);

    void buscarPorId(IDTYPE id, CallBackDisposableInterface<ENTITY> disposableInterface);

    void obtenerListaEntidad(CallBackDisposableInterface<List<ENTITY>> disposableInterface);

}
