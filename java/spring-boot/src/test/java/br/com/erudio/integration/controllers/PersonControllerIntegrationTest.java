package br.com.erudio.integration.controllers;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.config.TestConfigs;
import br.com.erudio.integration.testcontainers.AbstractIntegrationTest;
import br.com.erudio.model.Person;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

// Os testes são realizados em ordem e em uma única instância da classe PersonControllerIntegrationTest
// ou seja, o resultado de um influencia o outro
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // porta do application.yml
public class PersonControllerIntegrationTest extends AbstractIntegrationTest {

    /* Especification -> Define como serão as requisições HTTP */
    private static RequestSpecification specification;
    private static ObjectMapper mapper; // Realiza mapeamento de objeto para json, xml etc.
    private static Person person;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper();
        // para que o mapper não falhe ao encontar propriedades desconhecidas
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new Person("Leandro", "Costa", "leandro@hotmail.com", "Uberlandia", "Male");

        specification = new RequestSpecBuilder()
            .setBasePath("/person")
            .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) // Logar request infos no terminal
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) // Logar response infos no terminal
            .build();
    }

    @Test
    @Order(1)
    public void testIntegrationGivenPersonObject_whenCreateAPerson_shouldReturnAPersonObject() throws Exception {
        // será aplicada a especification ao given() do RestAssured
        String content = given().spec(specification) // Given / Arrange
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
            .when() // When / Act
                .post()
            .then() // Then / Assert
                .statusCode(200)
            .extract()
                .body()
                    .asString();

        Person createdPerson = mapper.readValue(content, Person.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getEmail());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);
        assertEquals("Leandro", createdPerson.getFirstName());
        assertEquals("Costa", createdPerson.getLastName());
        assertEquals("leandro@hotmail.com", createdPerson.getEmail());
        assertEquals("Uberlandia", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }

    @Test
    @Order(2)
    public void testIntegrationGivenPersonObject_whenUpdateAPerson_shouldReturnUpdatedPersonObject() throws Exception {
        // Given / Arrange
        person.setFirstName("Luana");
        person.setEmail("luana@hotmail.com");
        person.setGender("Female");

        String content = given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
            .when() // When / Act
                .put()
            .then() // Then / Assert
                .statusCode(200)
            .extract()
                .body()
                    .asString();

        Person updatedPerson = mapper.readValue(content, Person.class);
        person = updatedPerson;

        assertNotNull(updatedPerson.getId());
        assertNotNull(updatedPerson.getFirstName());
        assertNotNull(updatedPerson.getLastName());
        assertNotNull(updatedPerson.getEmail());
        assertNotNull(updatedPerson.getAddress());
        assertNotNull(updatedPerson.getGender());

        assertTrue(updatedPerson.getId() > 0);
        assertEquals("Luana", updatedPerson.getFirstName());
        assertEquals("Costa", updatedPerson.getLastName());
        assertEquals("luana@hotmail.com", updatedPerson.getEmail());
        assertEquals("Uberlandia", updatedPerson.getAddress());
        assertEquals("Female", updatedPerson.getGender());
    }

    @Test
    @Order(3)
    public void testIntegrationGivenPersonId_whenFindById_shouldReturnAPersonObject() throws Exception {
        // Given / Arrange
        long id = person.getId();

        String content = given().spec(specification)
            .when() // When / Act
                .get("/{id}", id)
            .then() // Then / Assert
                .statusCode(200)
            .extract()
                .body()
                    .asString();

        Person foundPerson = mapper.readValue(content, Person.class);

        assertNotNull(foundPerson.getId());
        assertNotNull(foundPerson.getFirstName());
        assertNotNull(foundPerson.getLastName());
        assertNotNull(foundPerson.getEmail());
        assertNotNull(foundPerson.getAddress());
        assertNotNull(foundPerson.getGender());

        assertEquals(id, foundPerson.getId());
        assertEquals("Luana", foundPerson.getFirstName());
        assertEquals("Costa", foundPerson.getLastName());
        assertEquals("luana@hotmail.com", foundPerson.getEmail());
        assertEquals("Uberlandia", foundPerson.getAddress());
        assertEquals("Female", foundPerson.getGender());
    }

    @Test
    @Order(4)
    public void testIntegration_whenFindAll_shouldReturnAPersonList() throws Exception {
        // Given / Arrange
        Person anotherPerson = new Person("Lucas", "Renan", "lucasrenan@hotmail.com", "Sorocaba - SP", "Male");

        // Adicionando uma nova pessoa para a lista antes
        given().spec(specification) // Given / Arrange
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(anotherPerson)
        .when()
            .post()
        .then()
            .statusCode(200);

        String content = given().spec(specification)
            .when() // When / Act
                .get()
            .then() // Then / Assert
                .statusCode(200)
            .extract()
                .body()
                    .asString();

        // Não é possível converter List<Person>.class, por isso, convertemos primeiro para Person[].class
        Person[] contentArray = mapper.readValue(content, Person[].class);
        List<Person> foundList = Arrays.asList(contentArray);

        Person foundPersonOne = foundList.get(0);

        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getEmail());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());

        assertTrue(foundPersonOne.getId() > 0);
        assertEquals("Luana", foundPersonOne.getFirstName());
        assertEquals("Costa", foundPersonOne.getLastName());
        assertEquals("luana@hotmail.com", foundPersonOne.getEmail());
        assertEquals("Uberlandia", foundPersonOne.getAddress());
        assertEquals("Female", foundPersonOne.getGender());

        Person foundPersonTwo = foundList.get(1);

        assertNotNull(foundPersonTwo.getId());
        assertNotNull(foundPersonTwo.getFirstName());
        assertNotNull(foundPersonTwo.getLastName());
        assertNotNull(foundPersonTwo.getEmail());
        assertNotNull(foundPersonTwo.getAddress());
        assertNotNull(foundPersonTwo.getGender());

        assertTrue(foundPersonTwo.getId() > 0);
        assertEquals("Lucas", foundPersonTwo.getFirstName());
        assertEquals("Renan", foundPersonTwo.getLastName());
        assertEquals("lucasrenan@hotmail.com", foundPersonTwo.getEmail());
        assertEquals("Sorocaba - SP", foundPersonTwo.getAddress());
        assertEquals("Male", foundPersonTwo.getGender());
    }


    @Test
    @Order(5)
    public void testIntegrationGivenPersonId_whenDelete_shouldReturnNothing() throws Exception {
        // Given / Arrange
        long id = person.getId();

        given().spec(specification)
            .when() // When / Act
                .delete("/{id}", id)
            .then() // Then / Assert
                .statusCode(204);
    }
}
