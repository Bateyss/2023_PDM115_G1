package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface PersonaRestServiceInterface extends CommonRestService<Persona> {

    String BASE_URL = ApiData.API1_URL.concat("/persona");

}
