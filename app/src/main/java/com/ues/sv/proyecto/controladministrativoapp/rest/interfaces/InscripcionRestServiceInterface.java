package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Inscripcion;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface InscripcionRestServiceInterface extends CommonRestService<Inscripcion> {

    String BASE_URL = ApiData.API1_URL.concat("/inscripcion");

}
