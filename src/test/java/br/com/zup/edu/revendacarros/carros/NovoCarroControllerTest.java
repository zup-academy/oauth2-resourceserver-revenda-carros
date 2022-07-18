package br.com.zup.edu.revendacarros.carros;

import br.com.zup.edu.revendacarros.carros.integrations.DetranClient;
import br.com.zup.edu.revendacarros.carros.integrations.StatusDoVeiculoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class NovoCarroControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CarroRepository repository;

    @MockBean
    private DetranClient detranClient;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        when(detranClient.getStatusDoVeiculo(anyString()))
                .thenReturn(new StatusDoVeiculoResponse("SEM_RESTRICOES", now()));
    }

    @Test
    @DisplayName("deve cadastrar novo carro")
    public void t1() throws Exception {
        // cenário
        NovoCarroRequest novoCarro = new NovoCarroRequest("Palio 2001", "HPK2045");

        // ação
        mockMvc.perform(post("/api/carros")
                            .contentType(APPLICATION_JSON)
                            .content(toJson(novoCarro))
                                .with(jwt()
                                    .authorities(new SimpleGrantedAuthority("SCOPE_carros:write"))
                            ))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("**/api/carros/*"))
        ;

        // validação
        assertEquals(1, repository.count(), "total de carros");
    }

    @Test
    @DisplayName("nao deve cadastrar novo carro quando parametros de entrada invalidos")
    public void t2() throws Exception {
        // cenário
        NovoCarroRequest novoCarro = new NovoCarroRequest(null, null);

        // ação
        mockMvc.perform(post("/api/carros")
                        .contentType(APPLICATION_JSON)
                        .content(toJson(novoCarro))
                        .with(jwt()
                                .authorities(new SimpleGrantedAuthority("SCOPE_carros:write"))
                        ))
                .andExpect(status().isBadRequest())
        ;

        // validação
        assertEquals(0, repository.count(), "total de carros");
    }

    @Test
    @DisplayName("nao deve cadastrar novo carro quando houver outro carro cadastrado com mesma placa")
    public void t3() throws Exception {
        // cenário
        Carro existente = repository.save(new Carro("Palio 2001", "HPK2045"));
        NovoCarroRequest novoCarro = new NovoCarroRequest(existente.getModelo(), existente.getPlaca());

        // ação
        mockMvc.perform(post("/api/carros")
                        .contentType(APPLICATION_JSON)
                        .content(toJson(novoCarro))
                        .with(jwt()
                                .authorities(new SimpleGrantedAuthority("SCOPE_carros:write"))
                        ))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(status().reason("carro com placa 'HPK2045' já existente"))
        ;

        // validação
        assertEquals(1, repository.count(), "total de carros");
    }

    @Test
    @DisplayName("nao deve cadastrar novo carro quando token não enviado")
    public void t4() throws Exception {
        // cenário
        NovoCarroRequest novoCarro = new NovoCarroRequest("Palio 2001", "HPK2045");

        // ação
        mockMvc.perform(post("/api/carros")
                        .contentType(APPLICATION_JSON)
                        .content(toJson(novoCarro)))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    @DisplayName("nao deve cadastrar novo carro quando token nao possuir scope apropriado")
    public void t5() throws Exception {
        // cenário
        NovoCarroRequest novoCarro = new NovoCarroRequest("Palio 2001", "HPK2045");

        // ação
        mockMvc.perform(post("/api/carros")
                        .contentType(APPLICATION_JSON)
                        .content(toJson(novoCarro))
                        .with(jwt()))
                .andExpect(status().isForbidden())
        ;
    }

    private String toJson(NovoCarroRequest payload) throws JsonProcessingException {
        return mapper.writeValueAsString(payload);
    }
}