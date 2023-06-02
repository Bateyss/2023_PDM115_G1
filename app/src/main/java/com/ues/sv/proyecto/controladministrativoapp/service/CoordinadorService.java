package com.ues.sv.proyecto.controladministrativoapp.service;

import android.content.Context;
import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.dao.CoordinadorDao;
import com.ues.sv.proyecto.controladministrativoapp.models.Coordinador;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.ServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.sqlite.DatabaseHandler;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class CoordinadorService implements ServiceInterface<Coordinador, Long> {

    private final CoordinadorDao coordinadorDao;


    public CoordinadorService(Context context) {
        DatabaseHandler handler = DatabaseHandler.getInstance(context);
        this.coordinadorDao = handler.coordinadorDao();
    }

    @Override
    public void registrarEntidad(Coordinador coordinador, CallBackDisposableInterface callBackDisposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
            @Override

            public Single<?> singleAction() {
                coordinador.setIdCoordinador(null);
                return coordinadorDao.insertCoordinador(coordinador);
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
    public void editarEntidad(Coordinador coordinador, CallBackVoidInterface voidInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {
                return coordinadorDao.updateCoordinador(coordinador);
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
    public void eliminarEntidad(Coordinador coordinador, CallBackVoidInterface voidInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {
                return coordinadorDao.deleteCoordinador(coordinador);
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
    public void buscarPorId(Long id, CallBackDisposableInterface<Coordinador> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return coordinadorDao.findById(id);
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((Coordinador) response), throwable -> {
                    Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }

    @Override
    public void obtenerListaEntidad(CallBackDisposableInterface<List<Coordinador>> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return coordinadorDao.findAll();
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<Coordinador>) response), throwable -> {
                    Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }
}
