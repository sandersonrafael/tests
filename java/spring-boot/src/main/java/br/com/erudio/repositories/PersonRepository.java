package br.com.erudio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.erudio.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);

    // pode ser usado :variavel ou =?1, =?2, =?n -> igual à ordem das variáveis nos argumentos
    @Query("select p from Person p where p.firstName = ?1 and p.lastName = ?2")
    Person findByJPQL(String firstName, String lastName);

    @Query("select p from Person p where p.firstName = :firstName and p.lastName = :lastName")
    Person findByJPQLNamedParams(
        @Param("firstName") String firstName, // Se o nome do Param for igual ao nome da variável, a annotation é opcional
        @Param("lastName") String lastName
    );

    // Inserindo o argumento value na annotation e o nativeQuery = true, se torna SQL real, não JPQL
    @Query(value = "SELECT * FROM person p WHERE p.first_name = ?1 AND p.last_name = ?2", nativeQuery = true)
    Person findByNativeSQL(String firstName,String lastName);

    @Query(value = "SELECT * FROM person p WHERE p.first_name = :firstName AND p.last_name = :lastName", nativeQuery = true)
    Person findByNativeSQLWithNamedParams(
        @Param("firstName") String firstName,
        @Param("lastName") String lastName
    );
}
