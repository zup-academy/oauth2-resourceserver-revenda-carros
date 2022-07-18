package br.com.zup.edu.revendacarros.carros.integrations;

import java.time.LocalDateTime;

public class StatusDoVeiculoResponse {

    private String placa;
    private String status;
    private LocalDateTime verificadoEm;

    public StatusDoVeiculoResponse(String placa, String status, LocalDateTime verificadoEm) {
        this.placa = placa;
        this.status = status;
        this.verificadoEm = verificadoEm;
    }

    public String getPlaca() {
        return placa;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getVerificadoEm() {
        return verificadoEm;
    }

    public boolean possuiAlgumRestricao() {
        return "COM_RESTRICOES".equals(status);
    }

    @Override
    public String toString() {
        return "StatusDoVeiculoResponse{" +
                "placa='" + placa + '\'' +
                ", status='" + status + '\'' +
                ", verificadoEm=" + verificadoEm +
                '}';
    }
}
