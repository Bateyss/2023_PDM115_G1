package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Alumno;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface AlumnoRestServiceInterface extends CommonRestService<Alumno> {

    String BASE_URL = ApiData.API1_URL.concat("/alumno");

}
