package br.com.zup.edu.revendacarros.carros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListaCarrosController {

    @Autowired
    private CarroRepository repository;

    @GetMapping("/api/carros")
    public ResponseEntity<?> lista() {
        return ResponseEntity.ok(
            repository.findAll().stream()
                                .map(CarroResponse::new)
                                .toList()
        );
    }
}
