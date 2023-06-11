package com.ues.sv.proyecto.controladministrativoapp.rest.service;

import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.models.Docente;
import com.ues.sv.proyecto.controladministrativoapp.rest.bin.AbsRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.bin.AbsRestServiceImpl;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.OkHttpClientInstance;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.DocenteRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class DocenteRestService extends AbsRestServiceImpl<Docente, DocenteRestServiceInterface, Long> {

    public DocenteRestService() {
        super(new AbsRestService<Docente, Long, DocenteRestServiceInterface>() {
            @Override
            public void registrarEntidad(Docente docente, CallBackDisposableInterface<Docente> disposableInterface, DocenteRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.create(docente);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Docente) object)
                                , throwable -> {
                                    Log.e("CREAR_ENTIDAD", "Error al crear entidad", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });
            }

            @Override
            public void editarEntidad(Docente docente, CallBackDisposableInterface<Docente> disposableInterface, DocenteRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.update(docente);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Docente) object)
                                , throwable -> {
                                    Log.e("EDITAR_ENTIDAD", "Error al editar entidad", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });
            }

            @Override
            public void eliminarEntidad(Docente docente, CallBackVoidInterface voidInterface, DocenteRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return restServiceInterface.delete(docente.getIdDocente());
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
            public void buscarPorId(Long id, CallBackDisposableInterface<Docente> disposableInterface, DocenteRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.getOneById(id);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Docente) object)
                                , throwable -> {
                                    Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });
            }

            @Override
            public void obtenerListaEntidad(CallBackDisposableInterface<List<Docente>> disposableInterface, DocenteRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return restServiceInterface.getList();
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<Docente>) response), throwable -> {
                            Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }
                });
            }
        });
    }

    @Override
    protected DocenteRestServiceInterface getRest() {
        return OkHttpClientInstance.docenteRestServiceInterface();
    }
}
