package br.com.erudio;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.erudio.model.Person;
import br.com.erudio.service.InterfacePersonService;
import br.com.erudio.service.PersonService;

public class PersonServiceTest {

    /*
    Também é possível e recomendado fazer vários desses testes em um só método quando forem no mesmo objeto
    instanciado
    */

    InterfacePersonService service;
    Person person;

    @BeforeEach
    void beforeEach() {
        person = new Person(
            "Fulano",
            "Silva",
            "fulanosilva@hotmail.com",
            "Rua x, Número 99, Bairro y, Cidade Z / WJ, 00000-001",
            "Male"
        );
        service = new PersonService();
    }

    @Test
    void testCreatePerson_WhenSuccess_ShouldReturnPersonObject() {
        // Given / Arrange

        // When / Act
        Person actual = service.createPerson(person);

        // Then / Assert
        assertNotNull(actual, () -> "O método createPerson não pode retornar null");
    }

    @Test
    void testCreatePerson_WhenSuccess_ShouldContainsFirstNameInReturnedPersonObject() {
        // Given / Arrange

        // When / Act
        Person actual = service.createPerson(person);

        // Then / Assert
        assertEquals(
            person.getFirstName(),
            actual.getFirstName(),
            () -> "Atributo firstName diferente do esperado"
        );
    }

    @Test
    void testCreatePerson_WhenSuccess_ShouldContainsLastNameInReturnedPersonObject() {
        // Given / Arrange

        // When / Act
        Person actual = service.createPerson(person);

        // Then / Assert
        assertEquals(
            person.getLastName(),
            actual.getLastName(),
            () -> "Atributo lastName diferente do esperado"
        );
    }

    @Test
    void testCreatePerson_WhenSuccess_ShouldContainsEmailInReturnedPersonObject() {
        // Given / Arrange

        // When / Act
        Person actual = service.createPerson(person);

        // Then / Assert
        assertEquals(
            person.getEmail(),
            actual.getEmail(),
            () -> "Atributo email diferente do esperado"
        );
    }

    @Test
    void testCreatePerson_WhenSuccess_ShouldContainsAddressInReturnedPersonObject() {
        // Given / Arrange

        // When / Act
        Person actual = service.createPerson(person);

        // Then / Assert
        assertEquals(
            person.getAddress(),
            actual.getAddress(),
            () -> "Atributo address diferente do esperado"
        );
    }

    @Test
    void testCreatePerson_WhenSuccess_ShouldContainsGenderInReturnedPersonObject() {
        // Given / Arrange

        // When / Act
        Person actual = service.createPerson(person);

        // Then / Assert
        assertEquals(
            person.getGender(),
            actual.getGender(),
            () -> "Atributo gender diferente do esperado"
        );
    }

    @Test
    void testCreatePerson_WhenSuccess_ShouldContainsIdInPersonObject() {
        // Given / Arrange

        // When / Act
        Person actual = service.createPerson(person);

        // Then / Assert
        assertNotNull(actual.getId(), () -> "Atributo ID não pode ser nulo");
    }

    @Test
    void testCreatePerson_WhenSuccess_ShouldContainsIdInReturnedPersonObject() {
        // Given / Arrange

        // When / Act
        Person actual = service.createPerson(person);

        // Then / Assert
        assertEquals(
            person.getId(),
            actual.getId(),
            () -> "Atributo id diferente do esperado"
        );
    }

    @Test
    void testCreatePerson_WithNullEmail_ShouldThrowIllegalArgumentException() {
        person.setEmail(null);

        String expectedMessage = "E-mail inválido";

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> service.createPerson(person),
            () -> "E-mail vazio deve ocasionar um erro: IllegalArgumentException"
        );

        assertEquals(
            expectedMessage,
            exception.getMessage(),
            () -> "Mensagem de erro deve ser igual a: E-mail inválido"
        );
    }
}
