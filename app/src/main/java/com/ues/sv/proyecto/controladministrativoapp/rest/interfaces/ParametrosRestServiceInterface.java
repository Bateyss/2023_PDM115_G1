package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Parametros;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface ParametrosRestServiceInterface extends CommonRestService<Parametros> {

    String BASE_URL = ApiData.API1_URL.concat("/parametros");

}
