package com.ues.sv.proyecto.controladministrativoapp.rest.bin;

import androidx.annotation.NonNull;

import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;

public abstract class AbsRestServiceImpl<MODELO, REST, IDTYPE> implements RestService<MODELO, IDTYPE> {
    public final REST rest;
    public final AbsRestService<MODELO, IDTYPE, REST> absServiceInterface;

    protected AbsRestServiceImpl(AbsRestService<MODELO, IDTYPE, REST> absServiceInterface) {
        rest = getRest();
        this.absServiceInterface = absServiceInterface;
    }

    protected abstract REST getRest();

    protected REST obtenerDao() {
        return this.rest;
    }

    @Override
    public void registrarEntidad(MODELO modelo, @NonNull CallBackDisposableInterface disposableInterface) {
        this.absServiceInterface.registrarEntidad(modelo, disposableInterface, this.rest);
    }

    @Override
    public void editarEntidad(MODELO modelo, @NonNull CallBackDisposableInterface disposableInterface) {
        this.absServiceInterface.editarEntidad(modelo, disposableInterface, this.rest);
    }

    @Override
    public void eliminarEntidad(MODELO modelo, @NonNull CallBackVoidInterface voidInterface) {
        this.absServiceInterface.eliminarEntidad(modelo, voidInterface, this.rest);
    }

    @Override
    public void buscarPorId(IDTYPE id, @NonNull CallBackDisposableInterface disposableInterface) {
        this.absServiceInterface.buscarPorId(id, disposableInterface, this.rest);
    }

    @Override
    public void obtenerListaEntidad(@NonNull CallBackDisposableInterface disposableInterface) {
        this.absServiceInterface.obtenerListaEntidad(disposableInterface, this.rest);
    }

}
