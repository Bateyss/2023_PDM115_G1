package com.ues.sv.proyecto.controladministrativoapp.rest.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ues.sv.proyecto.controladministrativoapp.models.Imagen;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.OkHttpClientInstance;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.ImagenRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagenRestService {

    public final ImagenRestServiceInterface imagenRestService;
    public final Context context;

    public ImagenRestService(Context context) {
        imagenRestService = OkHttpClientInstance.imagenRestServiceInterface();
        this.context = context;
    }

    public void registrarEntidad(Uri fileUri, CallBackDisposableInterface<Imagen> disposableInterface) {
        try {
            File file = new File(fileUri.getPath());

            RequestBody requestBody = RequestBody.create(MediaType.parse(context.getContentResolver().getType(fileUri)), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("fotillo", file.getName(), requestBody);

            Call<Imagen> call = imagenRestService.create(body);
            call.enqueue(new Callback<Imagen>() {
                @Override
                public void onResponse(Call<Imagen> call, Response<Imagen> response) {
                    disposableInterface.onCallBack(response.body());
                }

                @Override
                public void onFailure(Call<Imagen> call, Throwable t) {
                    Log.e("CREAR_ENTIDAD", "Error al crear entidad", t);
                    disposableInterface.onThrow(t);
                }
            });
        } catch (Exception e) {
            Log.e("CREAR_ENTIDAD", "Error al crear entidad", e.getCause());
        }
    }

    public void editarEntidad(Uri fileUri, Imagen imagen, CallBackDisposableInterface<Imagen> disposableInterface) {
        try {
            File file = new File(fileUri.getPath());

            RequestBody requestBody = RequestBody.create(MediaType.parse(context.getContentResolver().getType(fileUri)), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("fotillo", file.getName(), requestBody);

            Call<Imagen> call = imagenRestService.update(imagen, body);
            call.enqueue(new Callback<Imagen>() {
                @Override
                public void onResponse(Call<Imagen> call, Response<Imagen> response) {
                    disposableInterface.onCallBack(response.body());
                }

                @Override
                public void onFailure(Call<Imagen> call, Throwable t) {
                    Log.e("CREAR_ENTIDAD", "Error al crear entidad", t);
                    disposableInterface.onThrow(t);
                }
            });
        } catch (Exception e) {
            Log.e("CREAR_ENTIDAD", "Error al crear entidad", e.getCause());
        }
    }

    public void eliminarEntidad(Imagen imagen, CallBackVoidInterface voidInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeCompletableCallback() {
            @Override
            public Completable completableAction() {
                return imagenRestService.delete(imagen);
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

    public void buscarPorId(Long id, CallBackDisposableInterface<Imagen> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeSingleCallbac() {
            @Override

            public Single<?> singleAction() {
                return imagenRestService.getOneById(id);
            }

            @Override
            public Disposable completableCallBack(Single<?> applySubscribe) {
                return applySubscribe.subscribe(object -> disposableInterface.onCallBack((Imagen) object)
                        , throwable -> {
                            Log.e("BUSCAR_POR_ID", "Error al buscar por id", throwable);
                            disposableInterface.onThrow(throwable);
                        });
            }
        });
    }

    public void obtenerListaEntidad(CallBackDisposableInterface<List<Imagen>> disposableInterface) {
        DisposableUtils.addComposite(new DisposableUtils.CompositeFlowableCallback() {
            @Override
            public Flowable<?> flowableAction() {
                return imagenRestService.getList();
            }

            @Override
            public Disposable completableCallBack(Flowable<?> applySubscribe) {
                return applySubscribe.subscribe(response -> disposableInterface.onCallBack((List<Imagen>) response), throwable -> {
                    Log.e("OBTENER_lISTA", "Error al obtener lista", throwable);
                    disposableInterface.onThrow(throwable);
                });
            }
        });
    }

    public void descargarImagen(Imagen imagen, ImageView imageView) {
        String urlImagen = imagenRestService.BASE_URL.concat("/download/").concat(imagen.getNombre());
        Picasso.get().load(urlImagen).resize(50, 50).centerCrop().into(imageView);
    }

    public void uploadImagen(int requestCode, int resultCode, Intent data, int resultOkConstant, CallBackDisposableInterface<Imagen> disposableInterface) {
        try {
            if (requestCode == 0 && resultCode == resultOkConstant && null != data) {
                Uri selectedImage = data.getData();
                registrarEntidad(selectedImage, disposableInterface);
            }
        } catch (Exception e) {

        }
    }

}
