package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Revision;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface RevisionRestServiceInterface extends CommonRestService<Revision> {

    String BASE_URL = ApiData.API1_URL.concat("/revision");

}
