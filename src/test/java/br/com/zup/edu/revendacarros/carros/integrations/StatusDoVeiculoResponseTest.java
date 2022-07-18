package br.com.zup.edu.revendacarros.carros.integrations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatusDoVeiculoResponseTest {

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void t1() throws JsonProcessingException {

        StatusDoVeiculoResponse s = mapper.readValue(
                "{\"placa\":\"XYZ4567\",\"status\":\"SEM_RESTRICOES\",\"verificadoEm\":\"2022-07-18T18:31:20.8285907\"}",
                StatusDoVeiculoResponse.class
        );

        System.out.println(s);
    }

}