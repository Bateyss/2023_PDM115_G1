package com.ues.sv.proyecto.controladministrativoapp.rest.service;

import android.util.Log;

import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;
import com.ues.sv.proyecto.controladministrativoapp.rest.bin.AbsRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.bin.AbsRestServiceImpl;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.OkHttpClientInstance;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.UsuarioRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;


public class UsuarioRestService extends AbsRestServiceImpl<Usuario, UsuarioRestServiceInterface, Long> {

    public UsuarioRestService() {
        super(new AbsRestService<Usuario, Long, UsuarioRestServiceInterface>() {
            @Override
            public void registrarEntidad(Usuario usuario, CallBackDisposableInterface<Usuario> disposableInterface, UsuarioRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.create(usuario);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Usuario) object)
                                , throwable -> {
                                    Log.e("CREAR_ENTIDAD", "Error al crear entidad", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });
            }

            @Override
            public void editarEntidad(Usuario usuario, CallBackDisposableInterface<Usuario> disposableInterface, UsuarioRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.update(usuario);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Usuario) object)
                                , throwable -> {
                                    Log.e("EDITAR_ENTIDAD", "Error al editar entidad", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });
            }

            @Override
            public void eliminarEntidad(Usuario usuario, CallBackVoidInterface voidInterface, UsuarioRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
                    @Override
                    public Completable completableAction() {
                        return restServiceInterface.delete(usuario.getIdUsuario());
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
            public void buscarPorId(Long id, CallBackDisposableInterface<Usuario> disposableInterface, UsuarioRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
                    @Override

                    public Single<?> singleAction() {
                        return restServiceInterface.getOneById(id);
                    }

                    @Override
                    public Disposable completableCallBack(Single<?> applySubscribe) {
                        return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Usuario) object)
                                , throwable -> {
                                    Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                                    disposableInterface.onThrow(throwable);
                                });
                    }
                });
            }

            @Override
            public void obtenerListaEntidad(CallBackDisposableInterface<List<Usuario>> disposableInterface, UsuarioRestServiceInterface restServiceInterface) {
                DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
                    @Override
                    public Flowable<?> flowableAction() {
                        return restServiceInterface.getList();
                    }

                    @Override
                    public Disposable completableCallBack(Flowable<?> applySubscribe) {
                        return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<Usuario>) response), throwable -> {
                            Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                            disposableInterface.onThrow(throwable);
                        });
                    }
                });
            }
        });
    }

    @Override
    protected UsuarioRestServiceInterface getRest() {
        return OkHttpClientInstance.usuarioRestServiceInterface();
    }

    public void buscarUserNameAndPass(String username, String userpass, CallBackDisposableInterface<Usuario> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
            @Override

            public Single<?> singleAction() {
                return OkHttpClientInstance.usuarioRestServiceInterface().buscarByUserAndPass(username, userpass);
            }

            @Override
            public Disposable completableCallBack(Single<?> applySubscribe) {
                return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Usuario) object)
                        , throwable -> {
                            Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                            disposableInterface.onThrow(throwable);
                        });
            }
        });
    }
}
