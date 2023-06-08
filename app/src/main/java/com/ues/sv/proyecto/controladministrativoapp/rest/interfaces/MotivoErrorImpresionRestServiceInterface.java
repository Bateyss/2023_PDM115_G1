package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.MotivoErrorImpresion;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface MotivoErrorImpresionRestServiceInterface extends CommonRestService<MotivoErrorImpresion> {

    String BASE_URL = ApiData.API1_URL.concat("/motivoerrorimpresion");

}
