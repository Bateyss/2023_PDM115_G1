package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.EncargadoImpresion;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface EncargadoImpresionRestServiceInterface extends CommonRestService<EncargadoImpresion> {

    String BASE_URL = ApiData.API1_URL.concat("/encargadoimpresion");

}
