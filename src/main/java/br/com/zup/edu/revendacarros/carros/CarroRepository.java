package br.com.zup.edu.revendacarros.carros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    public boolean existsByPlaca(String placa);
}
