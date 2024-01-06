package br.com.erudio.business;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.erudio.service.CourserService;
import br.com.erudio.stubs.CourseServiceStub;

public class CourseBusinessStubTest {

    @Test
    void testCoursesRelatedToSpring_When_UsingAStub() {
        // Given / Arrange
        CourserService stubService = new CourseServiceStub();
        CourseBusiness business = new CourseBusiness(stubService);

        /* Case 1 */
        // // When / Act
        // List<String> filteredCourses = business.retrieveCoursesRelatedToSpring("Leandro");
        // // Then / Assert
        // assertEquals(4, filteredCourses.size(), () -> "Quantidade de cursos diferente do esperado");

        /* Case 2 */
        List<String> filteredCourses = business.retrieveCoursesRelatedToSpring("Foo Bar");
        assertEquals(0, filteredCourses.size(), () -> "Quantidade de cursos diferente do esperado");
    }
}
