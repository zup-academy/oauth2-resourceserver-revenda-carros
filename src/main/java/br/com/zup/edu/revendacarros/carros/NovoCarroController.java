package br.com.zup.edu.revendacarros.carros;

import br.com.zup.edu.revendacarros.carros.integrations.StatusDoVeiculoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
public class NovoCarroController {

    @Autowired
    private CarroRepository repository;

    @Autowired
    private WebClient webClient;

    @Transactional
    @PostMapping("/api/carros")
    public ResponseEntity<?> cadastra(@RequestBody @Valid NovoCarroRequest request, UriComponentsBuilder uriBuilder) {

        String uri = "http://localhost:9090/detran-status-veiculos/api/veiculos/{placa}/status"
                .replace("{placa}", request.getPlaca());

        StatusDoVeiculoResponse status = webClient.get()
                                            .uri(uri).retrieve()
                                            .bodyToMono(StatusDoVeiculoResponse.class)
                                            .block();

        if (status.possuiAlgumRestricao()) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "carro possui restrições no DETRAN");
        }

        Carro carro = request.toModel(repository);
        repository.save(carro);

        URI location = uriBuilder.path("/api/carros/{id}")
                                 .buildAndExpand(1)
                                 .toUri();

        return ResponseEntity
                .created(location).build();
    }

}
