package br.com.zup.edu.revendacarros.carros;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class ListaCarrosControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CarroRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("deve listar todos os carros cadastrados")
    public void t1() throws Exception {
        // cenário
        List.of(
            new Carro("Gol 1998", "HPK2045"),
            new Carro("Palio 2001", "ABC0987"),
            new Carro("HB20 2018", "XYZ5432")
        ).forEach(carro -> {
            repository.save(carro);
        });

        // ação e validação
        mockMvc.perform(get("/api/carros")
                            .contentType(APPLICATION_JSON)
                            .with(jwt()
                                .authorities(new SimpleGrantedAuthority("SCOPE_carros:read"))
                            ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].placa").value("HPK2045"))
                .andExpect(jsonPath("$[0].modelo").value("Gol 1998"))
                .andExpect(jsonPath("$[1].placa").value("ABC0987"))
                .andExpect(jsonPath("$[1].modelo").value("Palio 2001"))
                .andExpect(jsonPath("$[2].placa").value("XYZ5432"))
                .andExpect(jsonPath("$[2].modelo").value("HB20 2018"))
        ;
    }

}