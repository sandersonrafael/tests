package br.com.erudio.controllers;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

// import os métodos http para utilizar no MockMvc

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc; // Utilizado para mockar as requisições HTTP

    @Autowired
    private ObjectMapper mapper; // Utilizado para serializar e desserializar JSONs

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
            // willAnswer retorna o argumento passado no given, de index 0 (retorna o mesmo que foi passado)

        /* Dica para importar métodos estáticos !!! */
        // Em vez de digitar nomeDoMetodo e apertar CTRL + Espaço, digitar .nomeDoMetodo e digitar CTRL + Espaço

        // When / Act
        // Utilizado para simular um método HTTP para uma determinada rota
        ResultActions response = mockMvc.perform(
            post("/person")
                .contentType(MediaType.APPLICATION_JSON) // seleciona o content e seta o seu type
                .content(mapper.writeValueAsString(person0)) // seleciona o content e seta seu conteúdo com json
        );

        // Then / Assert

        /* Os dados JSON são obtidos pelo Spring Boot através do $.nomeDoAtributo */
        // Os .andExpect são verificações para os testes, podendo resultar em erro
        response
            .andDo(print()) // printa o resultado na tela, com os valores do Json
            .andExpect(status().isOk()) // verifica se o status é ok
            .andExpect(jsonPath("$.firstName", is(person0.getFirstName()))) // verifica se o atributo do json é igual ao solicitado
            .andExpect(jsonPath("$.lastName", is(person0.getLastName()))) // verifica se o atributo do json é igual ao solicitado
            .andExpect(jsonPath("$.email", is(person0.getEmail()))); // verifica se o atributo do json é igual ao solicitado
    }

    @Test
    void testGivenPersonList_whenFindAll_thenReturnPersonList() throws Exception {
        // Given / Arrange
        List<Person> persons = List.of(person0, person1);
        given(services.findAll()).willReturn(persons);

        // When / Act
        ResultActions response = mockMvc.perform(get("/person"));

        // Then / Assert
        response
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void testGivenPersonId_whenFindById_thenReturnPersonObject() throws Exception {
        // Given / Arrange
        long personId = 1L;
        given(services.findById(personId)).willReturn(person0);

        // When / Act
        // O segundo argumento do método get é o parâmetro de url, se houverem mais, seguem na sequência de params
        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        // Then / Assert
        response
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is(person0.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(person0.getLastName())))
            .andExpect(jsonPath("$.email", is(person0.getEmail())));
    }

    @Test
    void testGivenInvalidPersonId_whenFindById_thenReturnNotFound() throws Exception {
        // Given / Arrange
        long personId = 1L;
        given(services.findById(personId)).willThrow(ResourceNotFoundException.class);

        // When / Act
        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        // Then / Assert
        response
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    void testGivenUpdatedPerson_whenUpdate_thenReturnUpdatedPersonObject() throws Exception {
        // Given / Arrange
        long personId = 1L;
        given(services.findById(personId)).willReturn(person0);
        given(services.update(any(Person.class))).willAnswer(aswer -> aswer.getArgument(0));

        // When / Act
        ResultActions response = mockMvc.perform(
            put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person1))
        );

        // Then / Assert
        response
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is(person1.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(person1.getLastName())))
            .andExpect(jsonPath("$.email", is(person1.getEmail())));
    }

    @Test
    void testGivenUnexistingPerson_whenUpdate_thenReturnNotFound() throws Exception {
        // Given / Arrange
        long personId = 1L;
        given(services.findById(personId)).willThrow(ResourceNotFoundException.class);
        given(services.update(any(Person.class))).willAnswer(aswer -> aswer.getArgument(1)); // para forçar o erro, solicita argumento que não existe

        // When / Act
        ResultActions response = mockMvc.perform(
            put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person1))
        );

        // Then / Assert
        response
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    void testGivenPersonId_whenDelete_thenReturnNoContent() throws Exception {
        // Given / Arrange
        long personId = 1L;
        willDoNothing().given(services).delete(personId); // infere que a resposta do método delete não retorna nada

        // When / Act
        ResultActions response = mockMvc.perform(delete("/person/{id}", personId));

        // Then / Assert
        response
            .andExpect(status().isNoContent())
            .andDo(print());
    }
}
