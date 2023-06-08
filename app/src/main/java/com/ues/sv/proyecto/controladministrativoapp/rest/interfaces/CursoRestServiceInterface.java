package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Curso;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface CursoRestServiceInterface extends CommonRestService<Curso> {

    String BASE_URL = ApiData.API1_URL.concat("/curso");

}
