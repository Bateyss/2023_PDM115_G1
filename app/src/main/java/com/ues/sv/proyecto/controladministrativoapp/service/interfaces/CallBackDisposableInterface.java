package com.ues.sv.proyecto.controladministrativoapp.service.interfaces;

public interface CallBackDisposableInterface<RESPONSETYPE> {
    void onCallBack(RESPONSETYPE responsetype);

    void onThrow(Throwable throwable);
}
