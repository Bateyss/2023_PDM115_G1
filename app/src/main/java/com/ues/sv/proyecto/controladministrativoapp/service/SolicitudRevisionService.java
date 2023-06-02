package com.ues.sv.proyecto.controladministrativoapp.service;

import android.content.Context;
import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.dao.SolicitudRevisionDao;
import com.ues.sv.proyecto.controladministrativoapp.models.SolicitudRevision;
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

public class SolicitudRevisionService extends AbsService<SolicitudRevision, SolicitudRevisionDao, Long> {
    public SolicitudRevisionService(Context context) {
        super(context, new AbsServiceInterface<SolicitudRevision, Long, SolicitudRevisionDao>() {
            @Override
            public void registrarEntidad(SolicitudRevision solicitudRevision, CallBackVoidInterface voidInterface, SolicitudRevisionDao solicitudRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        solicitudRevision.setIdSolicitudRevision(null);
                        return solicitudRevisionDao.insertSolicitudRevision(solicitudRevision);
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
            public void editarEntidad(SolicitudRevision solicitudRevision, CallBackVoidInterface voidInterface, SolicitudRevisionDao solicitudRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return solicitudRevisionDao.updateSolicitudRevision(solicitudRevision);
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
            public void eliminarEntidad(SolicitudRevision solicitudRevision, CallBackVoidInterface voidInterface, SolicitudRevisionDao solicitudRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return solicitudRevisionDao.deleteSolicitudRevision(solicitudRevision);
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
            public void buscarPorId(Long id, CallBackDisposableInterface<SolicitudRevision> disposableInterface, SolicitudRevisionDao solicitudRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return solicitudRevisionDao.findById(id);
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((SolicitudRevision) response), throwable -> {
                            Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });

            }

            @Override
            public void obtenerListaEntidad(CallBackDisposableInterface<List<SolicitudRevision>> disposableInterface, SolicitudRevisionDao solicitudRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return solicitudRevisionDao.findAll();
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<SolicitudRevision>) response), throwable -> {
                            Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });

            }
        });
    }

    @Override
    protected SolicitudRevisionDao getDao(DatabaseHandler handler) {
        return handler.solicitudRevisionDao();
    }

    public void obtenerListaPorEvaluacionId(Long idEvaluacion,CallBackDisposableInterface<List<SolicitudRevision>> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return obtenerDao().findAllByIdEvaluacion(idEvaluacion);
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<SolicitudRevision>) response), throwable -> {
                    Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });

    }
}
