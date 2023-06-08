package com.ues.sv.proyecto.controladministrativoapp.room.service;

import android.content.Context;
import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.room.dao.ParticipanteRevisionDao;
import com.ues.sv.proyecto.controladministrativoapp.models.ParticipanteRevision;
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

public class ParticipanteRevisionService extends AbsServiceImpl<ParticipanteRevision, ParticipanteRevisionDao, Long> {
    public ParticipanteRevisionService(Context context) {
        super(context, new AbsService<ParticipanteRevision, Long, ParticipanteRevisionDao>() {

            @Override
            public void registrarEntidad(ParticipanteRevision participanteRevision, CallBackDisposableInterface disposableInterface, ParticipanteRevisionDao participanteRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        participanteRevision.setIdParticipante(null);
                        return participanteRevisionDao.insertParticipanteRevision(participanteRevision);
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
            public void editarEntidad(ParticipanteRevision participanteRevision, CallBackVoidInterface voidInterface, ParticipanteRevisionDao participanteRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return participanteRevisionDao.updateParticipanteRevision(participanteRevision);
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
            public void eliminarEntidad(ParticipanteRevision participanteRevision, CallBackVoidInterface voidInterface, ParticipanteRevisionDao participanteRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return participanteRevisionDao.deleteParticipanteRevision(participanteRevision);
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
            public void buscarPorId(Long id, CallBackDisposableInterface<ParticipanteRevision> disposableInterface, ParticipanteRevisionDao participanteRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return participanteRevisionDao.findById(id);
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((ParticipanteRevision) response), throwable -> {
                            Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });
            }

            @Override
            public void obtenerListaEntidad(CallBackDisposableInterface<List<ParticipanteRevision>> disposableInterface, ParticipanteRevisionDao participanteRevisionDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return participanteRevisionDao.findAll();
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<ParticipanteRevision>) response), throwable -> {
                            Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });
            }
        });
    }

    @Override
    protected ParticipanteRevisionDao getDao(DatabaseHandler handler) {
        return handler.participanteRevisionDao();
    }
}
