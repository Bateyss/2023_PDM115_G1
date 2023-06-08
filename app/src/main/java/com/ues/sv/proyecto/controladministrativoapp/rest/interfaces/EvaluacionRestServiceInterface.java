package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Evaluacion;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface EvaluacionRestServiceInterface extends CommonRestService<Evaluacion> {

    String BASE_URL = ApiData.API1_URL.concat("/evaluacion");

}
