package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Coordinador;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface CoordinadorRestServiceInterface extends CommonRestService<Coordinador> {

    String BASE_URL = ApiData.API1_URL.concat("/coordinador");

}
