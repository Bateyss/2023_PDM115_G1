package com.ues.sv.proyecto.controladministrativoapp.room.service;

import android.content.Context;
import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.room.dao.InscripcionDao;
import com.ues.sv.proyecto.controladministrativoapp.models.Inscripcion;
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

public class InscripcionService extends AbsServiceImpl<Inscripcion, InscripcionDao, Long> {
    public InscripcionService(Context context) {
        super(context, new AbsService<Inscripcion, Long, InscripcionDao>() {

            @Override
            public void registrarEntidad(Inscripcion inscripcion, CallBackDisposableInterface disposableInterface, InscripcionDao inscripcionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        inscripcion.setIdInscripcion(null);
                        return inscripcionDao.insertInscripcion(inscripcion);
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
            public void editarEntidad(Inscripcion inscripcion, CallBackVoidInterface voidInterface, InscripcionDao inscripcionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return inscripcionDao.updateInscripcion(inscripcion);
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
            public void eliminarEntidad(Inscripcion inscripcion, CallBackVoidInterface voidInterface, InscripcionDao inscripcionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return inscripcionDao.deleteInscripcion(inscripcion);
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
            public void buscarPorId(Long id, CallBackDisposableInterface<Inscripcion> disposableInterface, InscripcionDao inscripcionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return inscripcionDao.findById(id);
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((Inscripcion) response), throwable -> {
                            Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });
            }

            @Override
            public void obtenerListaEntidad(CallBackDisposableInterface<List<Inscripcion>> disposableInterface, InscripcionDao inscripcionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return inscripcionDao.findAll();
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<Inscripcion>) response), throwable -> {
                            Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });
            }
        });
    }

    @Override
    protected InscripcionDao getDao(DatabaseHandler handler) {
        return handler.inscripcionDao();
    }
}
