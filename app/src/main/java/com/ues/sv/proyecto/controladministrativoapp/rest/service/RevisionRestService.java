package com.ues.sv.proyecto.controladministrativoapp.rest.service;

import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.models.Revision;
import com.ues.sv.proyecto.controladministrativoapp.rest.bin.AbsRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.bin.AbsRestServiceImpl;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.OkHttpClientInstance;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.RevisionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class RevisionRestService extends AbsRestServiceImpl<Revision, RevisionRestServiceInterface, Long> {

    public RevisionRestService() {
        super(new AbsRestService<Revision, Long, RevisionRestServiceInterface>() {
            @Override
            public void registrarEntidad(Revision revision, CallBackDisposableInterface<Revision> disposableInterface, RevisionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        revision.setIdRevision(null);
                        return restServiceInterface.create(revision);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Revision) object)
                                , throwable -> {
                                    Log.e("CREAR_ENTIDAD", "Error al crear entidad", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });
            }

            @Override
            public void editarEntidad(Revision revision, CallBackDisposableInterface<Revision> disposableInterface, RevisionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.update(revision);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Revision) object)
                                , throwable -> {
                                    Log.e("EDITAR_ENTIDAD", "Error al editar entidad", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });
            }

            @Override
            public void eliminarEntidad(Revision revision, CallBackVoidInterface voidInterface, RevisionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return restServiceInterface.delete(revision);
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
            public void buscarPorId(Long id, CallBackDisposableInterface<Revision> disposableInterface, RevisionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.getOneById(id);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Revision) object)
                                , throwable -> {
                                    Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });
            }

            @Override
            public void obtenerListaEntidad(CallBackDisposableInterface<List<Revision>> disposableInterface, RevisionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return restServiceInterface.getList();
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<Revision>) response), throwable -> {
                            Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }
                });
            }
        });
    }

    @Override
    protected RevisionRestServiceInterface getRest() {
        return OkHttpClientInstance.revisionRestServiceInterface();
    }
}
