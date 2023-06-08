package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface CicloRestServiceInterface extends CommonRestService<Ciclo> {

    String BASE_URL = ApiData.API1_URL.concat("/ciclo");

}
