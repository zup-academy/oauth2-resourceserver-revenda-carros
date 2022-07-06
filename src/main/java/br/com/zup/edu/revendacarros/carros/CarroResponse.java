package br.com.zup.edu.revendacarros.carros;

import java.time.LocalDateTime;

public class CarroResponse {

    private final String modelo;
    private final String placa;
    private final LocalDateTime criadoEm;

    public CarroResponse(Carro carro) {
        this.modelo = carro.getModelo();
        this.placa = carro.getPlaca();
        this.criadoEm = carro.getCriadoEm();
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    @Override
    public String toString() {
        return "CarroResponse{" +
                "modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                ", criadoEm=" + criadoEm +
                '}';
    }
}
