package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Impresion;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface ImpresionRestServiceInterface extends CommonRestService<Impresion> {

    String BASE_URL = ApiData.API1_URL.concat("/impresion");

}
