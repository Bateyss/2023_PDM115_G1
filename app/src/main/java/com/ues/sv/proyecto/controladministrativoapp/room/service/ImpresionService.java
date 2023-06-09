package com.ues.sv.proyecto.controladministrativoapp.room.service;

import android.content.Context;
import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.room.dao.ImpresionDao;
import com.ues.sv.proyecto.controladministrativoapp.models.Impresion;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.AbsServiceImpl;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.AbsService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.conf.DatabaseHandler;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class ImpresionService extends AbsServiceImpl<Impresion, ImpresionDao, Long> {

    public ImpresionService(Context context) {
        super(context, new AbsService<Impresion, Long, ImpresionDao>() {

            @Override
            public void registrarEntidad(Impresion impresion, CallBackDisposableInterface disposableInterface, ImpresionDao impresionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        impresion.setIdImpresion(null);
                        return impresionDao.insertImpresion(impresion);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(id -> disposableInterface.onCallBack(id)
                                , throwable -> {
                                    Log.e("CREAR_ENTIDAD", "Error al crear entidad", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });

            }

            @Override
            public void editarEntidad(Impresion impresion, CallBackVoidInterface voidInterface, ImpresionDao impresionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return impresionDao.updateImpresion(impresion);
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
            public void eliminarEntidad(Impresion impresion, CallBackVoidInterface voidInterface, ImpresionDao impresionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return impresionDao.deleteImpresion(impresion);
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
            public void buscarPorId(Long id, CallBackDisposableInterface<Impresion> disposableInterface, ImpresionDao impresionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return impresionDao.findById(id);
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((Impresion) response), throwable -> {
                            Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });
            }

            @Override
            public void obtenerListaEntidad(CallBackDisposableInterface<List<Impresion>> disposableInterface, ImpresionDao impresionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return impresionDao.findAll();
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<Impresion>) response), throwable -> {
                            Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });
            }
        });
    }

    @Override
    protected ImpresionDao getDao(DatabaseHandler handler) {
        return handler.impresionDao();
    }

    public void buscarPorIdEvaluacion(Long id, CallBackDisposableInterface<Impresion> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return obtenerDao().findByIdEvaluacion(id);
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((Impresion) response), throwable -> {
                    Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }
}
