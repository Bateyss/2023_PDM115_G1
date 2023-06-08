package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface TipoEvaluacionRestServiceInterface extends CommonRestService<TipoEvaluacion> {

    String BASE_URL = ApiData.API1_URL.concat("/tipoevaluacion");

}
