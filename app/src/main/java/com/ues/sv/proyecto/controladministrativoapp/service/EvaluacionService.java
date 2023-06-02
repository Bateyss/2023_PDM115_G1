package com.ues.sv.proyecto.controladministrativoapp.service;

import android.content.Context;
import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.dao.EvaluacionDao;
import com.ues.sv.proyecto.controladministrativoapp.models.EncargadoImpresion;
import com.ues.sv.proyecto.controladministrativoapp.models.Evaluacion;
import com.ues.sv.proyecto.controladministrativoapp.models.Impresion;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.ServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.sqlite.DatabaseHandler;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class EvaluacionService implements ServiceInterface<Evaluacion, Long> {

    private final EvaluacionDao evaluacionDao;
    private final ImpresionService impresionService;
    private final EncargadoImpresionService encargadoImpresionService;


    public EvaluacionService(Context context) {
        DatabaseHandler handler = DatabaseHandler.getInstance(context);
        this.evaluacionDao = handler.evaluacionDao();
        this.impresionService = new ImpresionService(context);
        this.encargadoImpresionService = new EncargadoImpresionService(context);
    }

    @Override
    public void registrarEntidad(Evaluacion evaluacion, CallBackDisposableInterface callBackDisposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
            @Override

            public Single<?> singleAction() {
                evaluacion.setIdEvaluacion(null);
                return evaluacionDao.insertEvaluacion(evaluacion);
            }

            @Override
            public Disposable completableCallBack(Single<?> applySubscribe) {
                return applySubscribe.subscribe(id -> {
                            callBackDisposableInterface.onCallBack(id);
                            encargadoImpresionService.findFirst(new CallBackDisposableInterface<EncargadoImpresion>() {
                                @Override
                                public void onCallBack(EncargadoImpresion encargadoImpresion) {
                                    Impresion impresion = new Impresion();
                                    impresion.setIdImpresion(0L);
                                    impresion.setEstadoImpresion(0);
                                    evaluacion.setIdEvaluacion((long) id);
                                    impresion.setEvaluacion(evaluacion);
                                    impresion.setObservacionesImpresion("");
                                    impresion.setEncargadoImpresion(encargadoImpresion);
                                    impresion.setFormato("");
                                    impresionService.registrarEntidad(impresion, new CallBackDisposableInterface() {
                                        @Override
                                        public void onCallBack(Object o) {

                                        }

                                        @Override
                                        public void onThrow(Throwable throwable) {

                                        }
                                    });
                                }

                                @Override
                                public void onThrow(Throwable throwable) {

                                }
                            });

                        }
                        , throwable -> {
                            Log.e("CREAR_ENTIDAD", "Error al crear entidad", throwable);
                            callBackDisposableInterface.onThrow(throwable);
                        });
            }
        });

    }

    @Override
    public void editarEntidad(Evaluacion evaluacion, CallBackVoidInterface voidInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {
                return evaluacionDao.updateEvaluacion(evaluacion);
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
    public void eliminarEntidad(Evaluacion evaluacion, CallBackVoidInterface voidInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {
                return evaluacionDao.deleteEvaluacion(evaluacion);
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
    public void buscarPorId(Long id, CallBackDisposableInterface<Evaluacion> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return evaluacionDao.findById(id);
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((Evaluacion) response), throwable -> {
                    Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }

    @Override
    public void obtenerListaEntidad(CallBackDisposableInterface<List<Evaluacion>> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return evaluacionDao.findAll();
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<Evaluacion>) response), throwable -> {
                    Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }
}
