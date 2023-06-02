package com.ues.sv.proyecto.controladministrativoapp.service;

import android.content.Context;
import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.dao.TipoEvaluacionDao;
import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;
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

public class TipoEvaluacionService implements ServiceInterface<TipoEvaluacion, Long> {

    private final TipoEvaluacionDao tipoEvaluacionDao;


    public TipoEvaluacionService(Context context) {
        DatabaseHandler handler = DatabaseHandler.getInstance(context);
        this.tipoEvaluacionDao = handler.tipoEvaluacionDao();
    }

    @Override
    public void registrarEntidad(TipoEvaluacion tipoEvaluacion, CallBackDisposableInterface callBackDisposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
            @Override

            public Single<?> singleAction() {
                tipoEvaluacion.setIdTipoEvaluacion(null);
                return tipoEvaluacionDao.insertTipoEvaluacion(tipoEvaluacion);
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
    public void editarEntidad(TipoEvaluacion tipoEvaluacion, CallBackVoidInterface voidInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {
                return tipoEvaluacionDao.updateTipoEvaluacion(tipoEvaluacion);
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
    public void eliminarEntidad(TipoEvaluacion tipoEvaluacion, CallBackVoidInterface voidInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {
                return tipoEvaluacionDao.deleteTipoEvaluacion(tipoEvaluacion);
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
    public void buscarPorId(Long id, CallBackDisposableInterface<TipoEvaluacion> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return tipoEvaluacionDao.findById(id);
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((TipoEvaluacion) response), throwable -> {
                    Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }

    @Override
    public void obtenerListaEntidad(CallBackDisposableInterface<List<TipoEvaluacion>> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return tipoEvaluacionDao.findAll();
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<TipoEvaluacion>) response), throwable -> {
                    Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }
}
