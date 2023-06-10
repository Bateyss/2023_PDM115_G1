package com.ues.sv.proyecto.controladministrativoapp.rest.service;

import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.models.Impresion;
import com.ues.sv.proyecto.controladministrativoapp.models.MotivoErrorImpresion;
import com.ues.sv.proyecto.controladministrativoapp.rest.bin.AbsRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.bin.AbsRestServiceImpl;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.OkHttpClientInstance;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.MotivoErrorImpresionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class MotivoErrorImpresionRestService extends AbsRestServiceImpl<MotivoErrorImpresion, MotivoErrorImpresionRestServiceInterface, Long> {

    public MotivoErrorImpresionRestService() {
        super(new AbsRestService<MotivoErrorImpresion, Long, MotivoErrorImpresionRestServiceInterface>() {
            @Override
            public void registrarEntidad(MotivoErrorImpresion motivoErrorImpresion, CallBackDisposableInterface<MotivoErrorImpresion> disposableInterface, MotivoErrorImpresionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        motivoErrorImpresion.setIdMotivo(null);
                        return restServiceInterface.create(motivoErrorImpresion);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((MotivoErrorImpresion) object)
                                , throwable -> {
                                    Log.e("CREAR_ENTIDAD", "Error al crear entidad", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });
            }

            @Override
            public void editarEntidad(MotivoErrorImpresion motivoErrorImpresion, CallBackDisposableInterface<MotivoErrorImpresion> disposableInterface, MotivoErrorImpresionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.update(motivoErrorImpresion);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((MotivoErrorImpresion) object)
                                , throwable -> {
                                    Log.e("EDITAR_ENTIDAD", "Error al editar entidad", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });
            }

            @Override
            public void eliminarEntidad(MotivoErrorImpresion motivoErrorImpresion, CallBackVoidInterface voidInterface, MotivoErrorImpresionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return restServiceInterface.delete(motivoErrorImpresion);
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
            public void buscarPorId(Long id, CallBackDisposableInterface<MotivoErrorImpresion> disposableInterface, MotivoErrorImpresionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.getOneById(id);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((MotivoErrorImpresion) object)
                                , throwable -> {
                                    Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });
            }

            @Override
            public void obtenerListaEntidad(CallBackDisposableInterface<List<MotivoErrorImpresion>> disposableInterface, MotivoErrorImpresionRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return restServiceInterface.getList();
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
    protected MotivoErrorImpresionRestServiceInterface getRest() {
        return OkHttpClientInstance.motivoErrorImpresionRestServiceInterface();
    }

    public void obtenerListaPorImpresion(Impresion impresion, CallBackDisposableInterface<List<MotivoErrorImpresion>> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override

            public Flowable<?> flowableAction() {
                return OkHttpClientInstance.motivoErrorImpresionRestServiceInterface().getListByImpresion(impresion);
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(object -> disposableInterface.onCallBack((List<MotivoErrorImpresion>) object)
                        , throwable -> {
                            Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                            disposableInterface.onThrow(throwable);
                        });
            }
        });
    }
}
