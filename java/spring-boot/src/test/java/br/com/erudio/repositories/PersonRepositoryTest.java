package br.com.erudio.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.erudio.model.Person;

@DataJpaTest // Utilizado para que a execução utilize o application.yml de tests em vez do main
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    private Person person;
    private String firstName;
    private String lastName;

    @BeforeEach
    public void setup() {
        // Given / Arrange
        person = new Person("Leandro", "Costa", "leandro@hotmail.com", "Uberlandia", "Male");
        firstName = "Leandro";
        lastName = "Costa";
    }

    @Test
    void testGivenPersonObject_whenSave_thenReturnSavedPerson() {
        // When / Act
        Person savedPerson = repository.save(person);

        // Then / Assert
        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }

    @Test
    void testGivenPersonList_whenFindAll_thenReturnSavedPersonList() {
        // Given / Arrange
        Person person1 = new Person("Luane", "Silva", "lulu@hotmail.com", "São Paulo", "Female");
        repository.saveAll(Arrays.asList(person, person1));

        // When / Act
        List<Person> persons = repository.findAll();

        // Then / Assert
        assertNotNull(persons);
        assertEquals(2, persons.size());
    }

    @Test
    void testGivenPersonObject_whenFindById_thenReturnSavedPersonObject() {
        // Given / Arrange
        person = repository.save(person);

        // When / Act
        Person savedPerson = repository.findById(person.getId()).get();

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person.getId(), savedPerson.getId());
    }

    @Test
    void testGivenPersonObject_whenFindByEmail_thenReturnSavedPersonObject() {
        // Given / Arrange
        person = repository.save(person);

        // When / Act
        Person savedPerson = repository.findByEmail(person.getEmail()).get();

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person.getEmail(), savedPerson.getEmail());
    }

    @Test
    void testGivenPersonObject_whenUpdatePerson_thenReturnUpdatedPersonObject() {
        // Given / Arrange
        person = repository.save(person);
        String newFirstName = "Luan";
        String newEmail = "luan@hotmail.com";

        // When / Act
        Person updatedPerson = repository.findById(person.getId()).get();
        updatedPerson.setFirstName(newFirstName);
        updatedPerson.setEmail(newEmail);

        // Then / Assert
        assertNotNull(updatedPerson);
        assertEquals(newFirstName, updatedPerson.getFirstName());
        assertEquals(newEmail, updatedPerson.getEmail());
    }

    @Test
    void testGivenPersonObject_whenDelete_thenReturnRemovePerson() {
        // Given / Arrange
        repository.save(person);

        // When / Act
        repository.deleteById(person.getId());

        Optional<Person> savedPerson = repository.findById(person.getId());

        // Then / Assert
        assertTrue(savedPerson.isEmpty());
    }

    @Test
    void testGivenFirstNameAndLastName_whenFindByJPQL_thenReturnSavedPersonObject() {
        // Given / Arrange
        person = repository.save(person);

        // When / Act
        Person savedPerson = repository.findByJPQL(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @Test
    void testGivenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnSavedPersonObject() {
        // Given / Arrange
        person = repository.save(person);

        // When / Act
        Person savedPerson = repository.findByJPQLNamedParams(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @Test
    void testGivenFirstNameAndLastName_whenFindByNativeSQL_thenReturnSavedPersonObject() {
        // Given / Arrange
        person = repository.save(person);

        // When / Act
        Person savedPerson = repository.findByNativeSQL(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @Test
    void testGivenFirstNameAndLastName_whenFindByNativeSQLWithNamedParams_thenReturnSavedPersonObject() {
        // Given / Arrange
        person = repository.save(person);

        // When / Act
        Person savedPerson = repository.findByNativeSQLWithNamedParams(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }
}
