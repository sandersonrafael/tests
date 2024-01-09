package br.com.erudio.mockito_hamcrest;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.business.CourseBusiness;
import br.com.erudio.service.CourseService;

@ExtendWith(MockitoExtension.class) // Utilizado para poder utilizar o mockito para criar mocks sem atribuir com o método
public class CourseBusinessMockitoInjectMocksTest {

    /* com o @ExtendWith(MockitoExtension.class), o mockito mocka automaticamente o service através da classe declarada
       sem precisar chamar o método do tipo: mockService = mock(CouseService.class)
    */
    @Mock
    CourseService mockService;

    @InjectMocks // Injeta o objeto mockado acima (mockService) dentro do objeto seguinte (como um argumento)
    CourseBusiness business;
    // equivalente a: business = new CourseBusiness(mockService);

    List<String> courses;

    @Captor // Injeta automaticamente o argument captor para poder se verificar os métodos mockados passando argumentos
    ArgumentCaptor<String> argumentCaptor;

    @BeforeEach
    void setup() {
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

        given(mockService.retrieveCourses("Leandro")).willReturn(courses);

        List<String> filteredCourses = business.retrieveCoursesRelatedToSpring("Leandro");

        assertThat("filteredCourses.size() need to be 4", filteredCourses.size(), is(4));
    }

    @Test
    void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_ShouldCallMethodDeleteCourse() {
        given(mockService.retrieveCourses("Leandro")).willReturn(courses);

        business.deleteCoursesRelatedToSpring("Leandro");

        verify(mockService).deleteCourse("Agile Desmistificado com Scrum, XP, Kanban e Trello");
        verify(mockService, never()).deleteCourse("Spring");
        verify(mockService, times(1)).deleteCourse("Agile Desmistificado com Scrum, XP, Kanban e Trello");
        verify(mockService, atLeastOnce()).deleteCourse("Agile Desmistificado com Scrum, XP, Kanban e Trello");
    }

    @Test
    void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_ShouldCallMethodDeleteCourseV2() {
        String agileCourse = "Agile Desmistificado com Scrum, XP, Kanban e Trello";
        String spring = "Spring";
        given(mockService.retrieveCourses("Leandro")).willReturn(courses);

        business.deleteCoursesRelatedToSpring("Leandro");

        then(mockService).should().deleteCourse(agileCourse);
        then(mockService).should(never()).deleteCourse(spring);
        then(mockService).should(times(1)).deleteCourse(agileCourse);
        then(mockService).should(atLeastOnce()).deleteCourse(agileCourse);
    }

    @Test
    void testDeleteCoursesNotRelatedToSpring_CapturingArguments_ShouldCallMethodDeleteCourse() {
        courses = Arrays.asList(
            "Agile Desmistificado com Scrum, XP, Kanban e Trello",
            "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker"
        );

        String agileCourse = "Agile Desmistificado com Scrum, XP, Kanban e Trello";
        given(mockService.retrieveCourses("Leandro")).willReturn(courses);

        // Já foi declarado acima pela annotation @Captor
        // ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        business.deleteCoursesRelatedToSpring("Leandro");

        then(mockService).should().deleteCourse(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), is(agileCourse));
    }
}
