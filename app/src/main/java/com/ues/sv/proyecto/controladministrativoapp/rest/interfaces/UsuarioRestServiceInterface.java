package com.ues.sv.proyecto.controladministrativoapp.rest.interfaces;

import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.CommonRestService;

public interface UsuarioRestServiceInterface extends CommonRestService<Usuario> {

    String BASE_URL = ApiData.API1_URL.concat("/usuario");

}
