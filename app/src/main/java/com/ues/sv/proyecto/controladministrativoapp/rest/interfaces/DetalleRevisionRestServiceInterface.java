package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.DetalleRevision;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface DetalleRevisionRestServiceInterface extends CommonRestService<DetalleRevision> {

    String BASE_URL = ApiData.API1_URL.concat("/detallerevision");

}
