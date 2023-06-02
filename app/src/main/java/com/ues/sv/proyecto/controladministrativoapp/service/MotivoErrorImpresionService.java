package com.ues.sv.proyecto.controladministrativoapp.service;

import android.content.Context;
import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.dao.MotivoErrorImpresionDao;
import com.ues.sv.proyecto.controladministrativoapp.models.Impresion;
import com.ues.sv.proyecto.controladministrativoapp.models.MotivoErrorImpresion;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.AbsService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.AbsServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.sqlite.DatabaseHandler;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MotivoErrorImpresionService extends AbsService<MotivoErrorImpresion, MotivoErrorImpresionDao, Long> {
    public MotivoErrorImpresionService(Context context) {
        super(context, new AbsServiceInterface<MotivoErrorImpresion, Long, MotivoErrorImpresionDao>() {
            @Override
            public void registrarEntidad(MotivoErrorImpresion motivoErrorImpresion, CallBackVoidInterface voidInterface, MotivoErrorImpresionDao motivoErrorImpresionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        motivoErrorImpresion.setIdMotivo(null);
                        return motivoErrorImpresionDao.insertMotivoErrorImpresion(motivoErrorImpresion);
                    }

                    @Override
                    public void onCallback() {
                        voidInterface.onCallBack();
                    }

                    @Override
                    public void onThrow(Throwable throwable) {
                        Log.e("CREAR_ENTIDAD", "Error al crear entidad", throwable);
                        voidInterface.onThrow(throwable);
                    }
                });
            }

            @Override
            public void editarEntidad(MotivoErrorImpresion motivoErrorImpresion, CallBackVoidInterface voidInterface, MotivoErrorImpresionDao motivoErrorImpresionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return motivoErrorImpresionDao.updateMotivoErrorImpresion(motivoErrorImpresion);
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
            public void eliminarEntidad(MotivoErrorImpresion motivoErrorImpresion, CallBackVoidInterface voidInterface, MotivoErrorImpresionDao motivoErrorImpresionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return motivoErrorImpresionDao.deleteMotivoErrorImpresion(motivoErrorImpresion);
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
            public void buscarPorId(Long id, CallBackDisposableInterface<MotivoErrorImpresion> disposableInterface, MotivoErrorImpresionDao motivoErrorImpresionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return motivoErrorImpresionDao.findById(id);
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((MotivoErrorImpresion) response), throwable -> {
                            Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });
            }

            @Override
            public void obtenerListaEntidad(CallBackDisposableInterface<List<MotivoErrorImpresion>> disposableInterface, MotivoErrorImpresionDao motivoErrorImpresionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return motivoErrorImpresionDao.findAll();
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<MotivoErrorImpresion>) response), throwable -> {
                            Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });
            }
        });
    }

    @Override
    protected MotivoErrorImpresionDao getDao(DatabaseHandler handler) {
        return handler.motivoErrorImpresionDao();
    }

    public void obtenerListaPorImpresion(Impresion impresion, CallBackDisposableInterface<List<MotivoErrorImpresion>> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return obtenerDao().findAllByIdImpresion(impresion.getIdImpresion());
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<MotivoErrorImpresion>) response), throwable -> {
                    Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }
}
