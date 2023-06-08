package com.ues.sv.proyecto.controladministrativoapp.room.bin;

import android.content.Context;

import androidx.annotation.NonNull;

import com.ues.sv.proyecto.controladministrativoapp.room.conf.DatabaseHandler;

import java.util.List;

public abstract class AbsServiceImpl<MODELO, DAO, IDTYPE> implements ServiceInterface<MODELO, IDTYPE> {
    public final DAO dao;
    public final AbsService<MODELO, IDTYPE, DAO> absServiceInterface;

    private DatabaseHandler handler;

    protected AbsServiceImpl(Context context, AbsService<MODELO, IDTYPE, DAO> absServiceInterface) {
        handler = DatabaseHandler.getInstance(context);
        dao = getDao(handler);
        this.absServiceInterface = absServiceInterface;
    }

    protected abstract DAO getDao(DatabaseHandler handler);

    protected DAO obtenerDao() {
        return this.dao;
    }

    @Override
    public void registrarEntidad(MODELO modelo, @NonNull CallBackDisposableInterface callBackDisposableInterface) {
        this.absServiceInterface.registrarEntidad(modelo, callBackDisposableInterface, this.dao);
    }

    @Override
    public void editarEntidad(MODELO modelo, @NonNull CallBackVoidInterface voidInterface) {
        this.absServiceInterface.editarEntidad(modelo, voidInterface, this.dao);
    }

    @Override
    public void eliminarEntidad(MODELO modelo, @NonNull CallBackVoidInterface voidInterface) {
        this.absServiceInterface.eliminarEntidad(modelo, voidInterface, this.dao);
    }

    @Override
    public void buscarPorId(IDTYPE id, @NonNull CallBackDisposableInterface<MODELO> disposableInterface) {
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
