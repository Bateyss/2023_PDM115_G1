package com.ues.sv.proyecto.controladministrativoapp.service.interfaces;

/**
 * Interface generica que ayuda a manifestar la conaccion asincrona de un callBack
 */
public interface CallBackVoidInterface {
    /**
     *  Accion al recivir una respuesta asincrona
     */
    void onCallBack();

    /**
     * Error al recivir un error
     * @param throwable {@link Throwable}
     */
    void onThrow(Throwable throwable);
}
