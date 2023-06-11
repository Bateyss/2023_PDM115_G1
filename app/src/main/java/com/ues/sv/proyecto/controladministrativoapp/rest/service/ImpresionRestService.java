package com.ues.sv.proyecto.controladministrativoapp.rest.service;

import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.models.Impresion;
import com.ues.sv.proyecto.controladministrativoapp.rest.bin.AbsRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.bin.AbsRestServiceImpl;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.OkHttpClientInstance;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.ImpresionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class ImpresionRestService extends AbsRestServiceImpl<Impresion, ImpresionRestServiceInterface, Long> {

    public ImpresionRestService() {
        super(new AbsRestService<Impresion, Long, ImpresionRestServiceInterface>() {
            @Override
            public void registrarEntidad(Impresion impresion, CallBackDisposableInterface<Impresion> disposableInterface, ImpresionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.create(impresion);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Impresion) object), throwable -> {
                            Log.e("CREAR_ENTIDAD", "Error al crear entidad", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }
                });
            }

            @Override
            public void editarEntidad(Impresion impresion, CallBackDisposableInterface<Impresion> disposableInterface, ImpresionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.update(impresion);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Impresion) object), throwable -> {
                            Log.e("EDITAR_ENTIDAD", "Error al editar entidad", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }
                });
            }

            @Override
            public void eliminarEntidad(Impresion impresion, CallBackVoidInterface voidInterface, ImpresionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return restServiceInterface.delete(impresion.getIdImpresion());
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
            public void buscarPorId(Long id, CallBackDisposableInterface<Impresion> disposableInterface, ImpresionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.getOneById(id);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Impresion) object), throwable -> {
                            Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }
                });
            }

            @Override
            public void obtenerListaEntidad(CallBackDisposableInterface<List<Impresion>> disposableInterface, ImpresionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return restServiceInterface.getList();
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
    protected ImpresionRestServiceInterface getRest() {
        return OkHttpClientInstance.impresionRestServiceInterface();
    }

    public void buscarPorIdEvaluacion(Long idEvaluacion, CallBackDisposableInterface<Impresion> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
            @Override
            public Single<?> singleAction() {
                return OkHttpClientInstance.impresionRestServiceInterface().getOneByIdEvaluacion(idEvaluacion);
            }

            @Override
            public Disposable completableCallBack(Single<?> applySubscribe) {
                return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Impresion) object), throwable -> {
                    Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }
        });
    }
}
