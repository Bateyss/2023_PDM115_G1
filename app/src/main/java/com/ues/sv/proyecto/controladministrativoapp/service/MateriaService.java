package com.ues.sv.proyecto.controladministrativoapp.service;

import android.content.Context;
import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.dao.MateriaDao;
import com.ues.sv.proyecto.controladministrativoapp.models.Materia;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.ServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.sqlite.DatabaseHandler;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MateriaService implements ServiceInterface<Materia, Long> {

    private final MateriaDao materiaDao;


    public MateriaService(Context context) {
        DatabaseHandler handler = DatabaseHandler.getInstance(context);
        this.materiaDao = handler.materiaDao();
    }

    @Override
    public void registrarEntidad(Materia materia, CallBackVoidInterface voidInterface) {
        materia.setIdMateria(0L);
        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {
                return materiaDao.insertMateria(materia);
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
    public void editarEntidad(Materia materia, CallBackVoidInterface voidInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {
                return materiaDao.updateMateria(materia);
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
    public void eliminarEntidad(Materia materia, CallBackVoidInterface voidInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {
                return materiaDao.deleteMateria(materia);
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
    public void buscarPorId(Long id, CallBackDisposableInterface<Materia> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return materiaDao.findById(id);
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((Materia) response), throwable -> {
                    Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }

    @Override
    public void obtenerListaEntidad(CallBackDisposableInterface<List<Materia>> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return materiaDao.findAll();
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<Materia>) response), throwable -> {
                    Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }

        });
    }
}
