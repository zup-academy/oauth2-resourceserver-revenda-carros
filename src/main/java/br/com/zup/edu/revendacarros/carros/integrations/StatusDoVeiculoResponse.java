package br.com.zup.edu.revendacarros.carros.integrations;

import java.time.LocalDateTime;

public class StatusDoVeiculoResponse {

    private String status;
    private final LocalDateTime verificadoEm;

    public StatusDoVeiculoResponse(String status, LocalDateTime verificadoEm) {
        this.status = status;
        this.verificadoEm = verificadoEm;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getVerificadoEm() {
        return verificadoEm;
    }

    @Override
    public String toString() {
        return "StatusDoVeiculoResponse{" +
                "status='" + status + '\'' +
                '}';
    }

    public boolean possuiRestricoes() {
        return "COM_RESTRICOES".equals(status);
    }
}
