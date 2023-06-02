package com.ues.sv.proyecto.controladministrativoapp.service.interfaces;

import android.content.Context;

import androidx.annotation.NonNull;

import com.ues.sv.proyecto.controladministrativoapp.sqlite.DatabaseHandler;

import java.util.List;

public abstract class AbsService<MODELO, DAO, IDTYPE> implements ServiceInterface<MODELO, IDTYPE> {
    public final DAO dao;
    public final AbsServiceInterface<MODELO, IDTYPE, DAO> absServiceInterface;

    private DatabaseHandler handler;

    protected AbsService(Context context, AbsServiceInterface<MODELO, IDTYPE, DAO> absServiceInterface) {
        handler = DatabaseHandler.getInstance(context);
        dao = getDao(handler);
        this.absServiceInterface = absServiceInterface;
    }

    protected abstract DAO getDao(DatabaseHandler handler);

    protected DAO obtenerDao() {
        return this.dao;
    }

    @Override
    public void registrarEntidad(MODELO modelo,@NonNull CallBackVoidInterface voidInterface) {
        this.absServiceInterface.registrarEntidad(modelo, voidInterface, this.dao);
    }

    @Override
    public void editarEntidad(MODELO modelo,@NonNull CallBackVoidInterface voidInterface) {
        this.absServiceInterface.editarEntidad(modelo, voidInterface, this.dao);
    }

    @Override
    public void eliminarEntidad(MODELO modelo,@NonNull CallBackVoidInterface voidInterface) {
        this.absServiceInterface.eliminarEntidad(modelo, voidInterface, this.dao);
    }

    @Override
    public void buscarPorId(IDTYPE id,@NonNull CallBackDisposableInterface<MODELO> disposableInterface) {
        this.absServiceInterface.buscarPorId(id, disposableInterface, this.dao);
    }

    @Override
    public void obtenerListaEntidad(@NonNull CallBackDisposableInterface<List<MODELO>> disposableInterface) {
        this.absServiceInterface.obtenerListaEntidad(disposableInterface, this.dao);
    }

    protected DatabaseHandler getHandler() {
        return this.handler;
    }

}
