package br.com.zup.edu.revendacarros.carros;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class NovoCarroRequest {

    @NotBlank
    private String modelo;

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}")
    private String placa;

    public NovoCarroRequest(String modelo, String placa) {
        this.modelo = modelo;
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    @Override
    public String toString() {
        return "NovoCarroRequest{" +
                "modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                '}';
    }

    public Carro toModel(CarroRepository repository) {

        if (repository.existsByPlaca(placa)) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY,
                            "carro com placa '%s' já existente".formatted(placa));
        }

        return new Carro(modelo, placa);
    }
}
