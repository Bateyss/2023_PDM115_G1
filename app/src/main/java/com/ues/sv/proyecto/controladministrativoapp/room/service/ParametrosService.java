package com.ues.sv.proyecto.controladministrativoapp.room.service;

import android.content.Context;
import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.room.dao.ParametrosDao;
import com.ues.sv.proyecto.controladministrativoapp.models.Parametros;
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

public class ParametrosService extends AbsServiceImpl<Parametros, ParametrosDao, Long> {
    public ParametrosService(Context context) {
        super(context, new AbsService<Parametros, Long, ParametrosDao>() {

            @Override
            public void registrarEntidad(Parametros parametros, CallBackDisposableInterface disposableInterface, ParametrosDao parametrosDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        parametros.setIdParametro(null);
                        return parametrosDao.insertParametros(parametros);
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
            public void editarEntidad(Parametros parametros, CallBackVoidInterface voidInterface, ParametrosDao parametrosDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return parametrosDao.updateParametros(parametros);
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
            public void eliminarEntidad(Parametros parametros, CallBackVoidInterface voidInterface, ParametrosDao parametrosDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return parametrosDao.deleteParametros(parametros);
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
            public void buscarPorId(Long id, CallBackDisposableInterface<Parametros> disposableInterface, ParametrosDao parametrosDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return parametrosDao.findById(id);
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((Parametros) response), throwable -> {
                            Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });
            }

            @Override
            public void obtenerListaEntidad(CallBackDisposableInterface<List<Parametros>> disposableInterface, ParametrosDao parametrosDao) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return parametrosDao.findAll();
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<Parametros>) response), throwable -> {
                            Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }

                });
            }
        });
    }

    @Override
    protected ParametrosDao getDao(DatabaseHandler handler) {
        return handler.parametrosDao();
    }

    public void buscarPorIdHistorico(Integer idHistorico, CallBackDisposableInterface<Parametros> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return obtenerDao().findById(idHistorico);
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((Parametros) response), throwable -> {
                    Log.e("BUSCAR_POR_ID", "Error al buscar por id historico", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }

    public void truncarBase() {

        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {

                return obtenerDao().deleteParametros(null);
            }

            @Override
            public void onCallback() {
                getHandler().clearAllTables();
            }

            @Override
            public void onThrow(Throwable throwable) {
                Log.e("TRUNCAR", "Error al truncar base", throwable);
            }
        });
    }
}
