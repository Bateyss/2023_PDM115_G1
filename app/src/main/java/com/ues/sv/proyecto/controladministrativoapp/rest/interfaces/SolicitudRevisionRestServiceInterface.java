package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.SolicitudRevision;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface SolicitudRevisionRestServiceInterface extends CommonRestService<SolicitudRevision> {

    String BASE_URL = ApiData.API1_URL.concat("/solicitudrevision");

}
