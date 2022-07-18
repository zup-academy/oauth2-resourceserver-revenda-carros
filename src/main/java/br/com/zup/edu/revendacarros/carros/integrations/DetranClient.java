package br.com.zup.edu.revendacarros.carros.integrations;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "detranClient",
    url  = "http://localhost:9090/detran-status-veiculos"
)
public interface DetranClient {

    @GetMapping("/api/veiculos/{placa}/status")
    StatusDoVeiculoResponse getStatusDoVeiculo(@PathVariable("placa") String placa);

}
