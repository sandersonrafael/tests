package br.com.erudio.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@ExtendWith(MockitoExtension.class) // Necessário para poder mockar as classes
public class PersonServicesTest {

    // Necessário mockar o repository, pois o service possui uma dependência dele. Se não, não é teste "unitário"
    @Mock
    private PersonRepository repository;

    // O @InjectMocks injeta o mock anterior na classe informada, substituindo a sua dependência de PersonRepository
    @InjectMocks
    private PersonServices services;

    private Person person0;
    private Person person1;

    @BeforeEach
    public void setup() {
        // Given / Arrange
        person0 = new Person(
            "Leandro",
            "Costa",
            "leandro@hotmail.com",
            "Uberlandia",
            "Male"
        );

        person1 = new Person(
            "Luane",
            "Silva",
            "lulu@hotmail.com",
            "São Paulo",
            "Female"
        );
    }

    @Test
    void testGivenPersonObject_whenSavePerson_thenReturnPersonObject() {
        // Given / Arrange -> Pode ser when / willReturn do BDDMockito
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.save(person0)).thenReturn(person0);

        // When / Act
        Person savedPerson = services.create(person0);

        // Then / Assert
        assertNotNull(savedPerson);
        // assertTrue(savedPerson.getId() > 0);
    }

    @Test
    void testGivenExistingEmail_whenSavePerson_thenThrowsException() {
        // Given / Arrange -> Pode ser when / willReturn do BDDMockito
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(person0));

        // When / Act
        assertThrows(ResourceNotFoundException.class, () -> {
            services.create(person0);
        });

        // Then / Assert
        // verifica se o repository NUNCA possui o método save acionado, com qualquer instância de Person
        verify(repository, never()).save(any(Person.class));
    }

    @Test
    void testGivenPersonList_whenFindAll_thenReturnPersonList() {
        // Given / Arrange -> Pode ser when / willReturn do BDDMockito
        when(repository.findAll()).thenReturn(List.of(person0, person1));

        // When / Act
        List<Person> personList = services.findAll();

        // Then / Assert
        assertNotNull(personList);
        assertEquals(2, personList.size());
    }

    @Test
    void testGivenEmptyPersonList_whenFindAll_thenReturnEmptyPersonList() {
        // Given / Arrange -> Pode ser when / willReturn do BDDMockito
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // When / Act
        List<Person> personList = services.findAll();

        // Then / Assert
        assertTrue(personList.isEmpty());
        assertEquals(0, personList.size());
    }

    @Test
    void testGivenPersonObject_whenUpdatePerson_thenReturnUpdatedPerson() {
        // Given / Arrange -> Pode ser when / willReturn do BDDMockito
        when(repository.findById(anyLong())).thenReturn(Optional.of(person0));

        person0.setId(1L);
        person0.setFirstName("Luane");
        person0.setEmail("lulu@hotmail.com");

        when(repository.save(person0)).thenReturn(person0);

        // When / Act
        Person updatedPerson = services.update(person0);

        // Then / Assert
        assertNotNull(updatedPerson);
        assertEquals("Luane", updatedPerson.getFirstName());
        assertEquals("lulu@hotmail.com", updatedPerson.getEmail());
    }

    @Test
    void testGivenPersonId_whenDeletePerson_thenReturnVoid() {
        // Given / Arrange -> Pode ser when / willReturn do BDDMockito
        when(repository.findById(anyLong())).thenReturn(Optional.of(person0));

        doNothing().when(repository).delete(any(Person.class));

        // When / Act
        services.delete(1L);

        // Then / Assert
        // Verificará se o método foi invocado. Se for invocado mais de uma vez, algo está errado
        verify(repository, times(1)).delete(person0);
    }
}
