package br.com.zup.edu.revendacarros.carros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class NovoCarroController {

    @Autowired
    private CarroRepository repository;

    @Transactional
    @PostMapping("/api/carros")
    public ResponseEntity<?> cadastra(@RequestBody @Valid NovoCarroRequest request, UriComponentsBuilder uriBuilder) {

        Carro carro = request.toModel(repository);
        repository.save(carro);

        URI location = uriBuilder.path("/api/carros/{id}")
                                 .buildAndExpand(1)
                                 .toUri();

        return ResponseEntity
                .created(location).build();
    }

}
