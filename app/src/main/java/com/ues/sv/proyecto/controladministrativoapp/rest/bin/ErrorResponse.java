package com.ues.sv.proyecto.controladministrativoapp.rest.bin;

public class ErrorResponse {
    private String codigo;
    private String mensaje;
    private String error;

    public ErrorResponse(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public ErrorResponse(String codigo, String mensaje, String error) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.error = error;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
