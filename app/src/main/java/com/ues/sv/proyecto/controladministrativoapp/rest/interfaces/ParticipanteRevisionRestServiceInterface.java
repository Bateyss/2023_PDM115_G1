package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.ParticipanteRevision;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface ParticipanteRevisionRestServiceInterface extends CommonRestService<ParticipanteRevision> {

    String BASE_URL = ApiData.API1_URL.concat("/participanterevision");

}
