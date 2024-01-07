package br.com.erudio.business;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import br.com.erudio.service.CourseService;

public class CourseBusinessMockWithBDDTest {

    CourseService mockService;
    CourseBusiness business;
    List<String> courses;

    @BeforeEach
    void setup() {
        // Give / Arrange

        // Método mock do mockito -> utilizamos mock do mockito para simular a classe, não acessando diretamente ela
        mockService = mock(CourseService.class);
        business = new CourseBusiness(mockService);

        courses = Arrays.asList(
            "REST API's RESTFul do 0 à Azure com ASP.NET Core 5 e Docker",
            "Agile Desmistificado com Scrum, XP, Kanban e Trello",
            "Spotify Engineering Culture Desmistificado",
            "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker",
            "Docker do Zero à Maestria - Contêinerização Desmistificada",
            "Docker para Amazon AWS Implante Apps Java e .NET com Travis CI",
            "Microsserviços do 0 com Spring Cloud, Spring Boot e Docker",
            "Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#",
            "REST API's RESTFul do 0 à AWS com Spring Boot 3 Kotlin e Docker",
            "Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android",
            "Microsserviços do 0 com Spring Cloud, Kotlin e Docker"
        );
    }

    @Test
    void testCoursesRelatedToSpring_When_UsingAStub() {

        // Para aplicar melhor conceitos do BDD, utilizamos a classe BDDMockito em vez da Mockito para importação
        // Os métodos são mais "naturais / realistas" com os conceitos do BDD | given em vez de when | willReturn em vez de thenReturn
        given(mockService.retrieveCourses("Leandro")).willReturn(courses);

        List<String> filteredCourses = business.retrieveCoursesRelatedToSpring("Leandro");

        // Em vez do assertEquals, utilizaremos o assertThat do hamcrest (para um código mais voltado ao BDD)
        assertThat("filteredCourses.size() need to be 4", filteredCourses.size(), is(4));
    }

    /* Mockito Verify -> Utilizado para métodos que possuem -> void <- como retorno (ex: delete()) */

    @Test
    void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_ShouldCallMethodDeleteCourse() {
        // Given / Arrange
        given(mockService.retrieveCourses("Leandro")).willReturn(courses);

        // When / Act
        business.deleteCoursesRelatedToSpring("Leandro");

        // Then / Assert
        /* Verify do Mockito para testar método delete (void) */
        verify(mockService).deleteCourse("Agile Desmistificado com Scrum, XP, Kanban e Trello");
        // verify(mockService).deleteCourse("Spring"); // falha porque chama o método deleteCourse

        // para garantir que não seja chamado, utilizar o never() no verify
        verify(mockService, never()).deleteCourse("Spring");

        // para repetir mais de uma vez, utiliza-se o times(n)
        verify(mockService, times(1)).deleteCourse("Agile Desmistificado com Scrum, XP, Kanban e Trello");

        // atLeast() -> testa se PELO menos n vezes o teste passará | em vez do atLeast(1), pode-se utilizar o atLeastOnce()
        verify(mockService, atLeastOnce()).deleteCourse("Agile Desmistificado com Scrum, XP, Kanban e Trello");
    }

    /* Utilizando then().should() em vez do verify() */

    @Test
    void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_ShouldCallMethodDeleteCourseV2() {
        // Given / Arrange
        String agileCourse = "Agile Desmistificado com Scrum, XP, Kanban e Trello";
        String spring = "Spring";
        given(mockService.retrieveCourses("Leandro")).willReturn(courses);

        // When / Act
        business.deleteCoursesRelatedToSpring("Leandro");

        // Then / Assert
        then(mockService).should().deleteCourse(agileCourse);
        then(mockService).should(never()).deleteCourse(spring);
        then(mockService).should(times(1)).deleteCourse(agileCourse);
        then(mockService).should(atLeastOnce()).deleteCourse(agileCourse);
    }

    /* Capturando argumentos passados a um mock */

    @Test
    void testDeleteCoursesNotRelatedToSpring_CapturingArguments_ShouldCallMethodDeleteCourse() {
        // Given / Arrange
        courses = Arrays.asList(
            "Agile Desmistificado com Scrum, XP, Kanban e Trello",
            "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker"
        );

        String agileCourse = "Agile Desmistificado com Scrum, XP, Kanban e Trello";
        given(mockService.retrieveCourses("Leandro")).willReturn(courses);


        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        // When / Act
        business.deleteCoursesRelatedToSpring("Leandro");

        // Then / Assert

        // Em vez de passar o agileCourse, passamos um argumentCaptor.capture() e o argumento será passado na asserção
        // Para não apresentar erros, é necessário remover alguns da lista e passar antes do given, conforme acima
        then(mockService).should().deleteCourse(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), is(agileCourse));
    }
}
