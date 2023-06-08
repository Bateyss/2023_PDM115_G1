package com.ues.sv.proyecto.controladministrativoapp.room.bin;

/**
 * Interface generica que ayuda a manifestar la conaccion asincrona de un callBack
 * @param <RESPONSETYPE> es el Tipo de dato que se recive en asincrono
 */
public interface CallBackDisposableInterface<RESPONSETYPE> {

    /**
     * Accion al recivir una respuesta asincrona
     * @param responsetype es el Tipo de dato que se recive en asincrono
     */
    void onCallBack(RESPONSETYPE responsetype);

    /**
     * Error al recivir un error
     * @param throwable {@link Throwable}
     */
    void onThrow(Throwable throwable);
}
