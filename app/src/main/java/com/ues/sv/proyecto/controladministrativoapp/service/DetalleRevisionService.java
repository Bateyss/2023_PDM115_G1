package com.ues.sv.proyecto.controladministrativoapp.service;

import android.content.Context;
import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.dao.DetalleRevisionDao;
import com.ues.sv.proyecto.controladministrativoapp.models.DetalleRevision;
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

public class DetalleRevisionService extends AbsService<DetalleRevision, DetalleRevisionDao, Long> {


    public DetalleRevisionService(Context context) {
        super(context, new AbsServiceInterface<DetalleRevision, Long, DetalleRevisionDao>() {
            @Override
            public void registrarEntidad(DetalleRevision detalleRevision, CallBackVoidInterface voidInterface, DetalleRevisionDao detalleRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        detalleRevision.setIdDetalle(null);
                        return detalleRevisionDao.insertDetalleRevision(detalleRevision);
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
            public void editarEntidad(DetalleRevision detalleRevision, CallBackVoidInterface voidInterface, DetalleRevisionDao detalleRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return detalleRevisionDao.updateDetalleRevision(detalleRevision);
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
            public void eliminarEntidad(DetalleRevision detalleRevision, CallBackVoidInterface voidInterface, DetalleRevisionDao detalleRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return detalleRevisionDao.deleteDetalleRevision(detalleRevision);
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
            public void buscarPorId(Long id, CallBackDisposableInterface<DetalleRevision> disposableInterface, DetalleRevisionDao detalleRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return detalleRevisionDao.findById(id);
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((DetalleRevision) response), throwable -> {
                            Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });
            }

            @Override
            public void obtenerListaEntidad(CallBackDisposableInterface<List<DetalleRevision>> disposableInterface, DetalleRevisionDao detalleRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return detalleRevisionDao.findAll();
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<DetalleRevision>) response), throwable -> {
                            Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });
            }
        });
    }

    @Override
    protected DetalleRevisionDao getDao(DatabaseHandler handler) {
        return handler.detalleRevisionDao();
    }
}
