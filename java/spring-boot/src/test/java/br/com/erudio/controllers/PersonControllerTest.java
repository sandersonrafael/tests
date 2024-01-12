package br.com.erudio.controllers;

import static org.mockito.BDDMockito.*;

// import os métodos http para utilizar no MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc; // Utilizado para mockar as requisições HTTP

    @Autowired
    private ObjectMapper objectMapper; // Utilizado para serializar e desserializar JSONs

    @MockBean
    private PersonServices services; // Utilizado para mockar os beans da aplicação, como os services

    private Person person0;
    private Person person1;

    @BeforeEach
    public void setup() {
        // Given / Arrange
        person0 = new Person("Leandro", "Costa", "leandro@hotmail.com", "Uberlandia", "Male");
        person1 = new Person("Luane", "Silva", "lulu@hotmail.com", "São Paulo", "Female");
    }

    @Test
    void testGivenPersonObject_whenCreatePerson_thenReturnSavedPerson() throws Exception {
        // Given / Arrange
        given(services.create(any(Person.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));
            // Retorna o argumento passado no given, de index 0 (retorna o mesmo que foi passado)

        // When / Act
        // Utilizado para simular um método HTTP para uma determinada rota
        MvcResult httpResult = mockMvc.perform(post("/person"))
            .andDo(result -> result.getRequest().setContentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(result -> result.getRequest().setContent())
            .andExpect(result -> result.getResponse().setStatus(200))
            .andExpect(result -> result.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        // Then / Assert
        System.out.println(httpResult.getResponse().getContentAsString());
    }
}
