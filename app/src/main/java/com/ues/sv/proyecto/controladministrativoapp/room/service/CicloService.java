package com.ues.sv.proyecto.controladministrativoapp.room.service;

import android.content.Context;
import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.room.dao.CicloDao;
import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.ServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.conf.DatabaseHandler;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class CicloService implements ServiceInterface<Ciclo, Long> {

    private final CicloDao cicloDao;

    public CicloService(Context context) {
        DatabaseHandler handler = DatabaseHandler.getInstance(context);
        this.cicloDao = handler.cicloDao();
    }

    @Override
    public void registrarEntidad(Ciclo ciclo, CallBackDisposableInterface callBackDisposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
            @Override

            public Single<?> singleAction() {
                ciclo.setIdCiclo(null);
                return cicloDao.insertCiclo(ciclo);
            }

            @Override
            public Disposable completableCallBack(Single<?> applySubscribe) {
                return applySubscribe.subscribe(id -> callBackDisposableInterface.onCallBack(id)
                        , throwable -> {
                            Log.e("CREAR_ENTIDAD", "Error al crear entidad", throwable);
                            callBackDisposableInterface.onThrow(throwable);
                        });
            }
        });
    }

    @Override
    public void editarEntidad(Ciclo ciclo, CallBackVoidInterface voidInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {
                return cicloDao.updateCiclo(ciclo);
            }

            @Override
            public void onCallback() {
                voidInterface.onCallBack();
            }

            @Override
            public void onThrow(Throwable throwable) {
                Log.e("EDITAR_ENTIDAD", "Error al editar entidad", throwable);
                voidInterface.onThrow(throwable);
            }
        });
    }

    @Override
    public void eliminarEntidad(Ciclo ciclo, CallBackVoidInterface voidInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {
                return cicloDao.deleteCiclo(ciclo);
            }

            @Override
            public void onCallback() {
                voidInterface.onCallBack();
            }

            @Override
            public void onThrow(Throwable throwable) {
                Log.e("ELIMINAR_ENTIDAD", "Error al eliminar entidad", throwable);
                voidInterface.onThrow(throwable);
            }
        });
    }

    @Override
    public void buscarPorId(Long id, CallBackDisposableInterface<Ciclo> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return cicloDao.findById(id);
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((Ciclo) response), throwable -> {
                    Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }

    @Override
    public void obtenerListaEntidad(CallBackDisposableInterface<List<Ciclo>> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return cicloDao.findAll();
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<Ciclo>) response), throwable -> {
                    Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }
}
