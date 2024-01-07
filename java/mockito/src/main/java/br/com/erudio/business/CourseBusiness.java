package br.com.erudio.business;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.erudio.service.CourseService;

// SUT -> System (Method) Under Test
public class CourseBusiness {

    // Dependency
    private CourseService service;

    public CourseBusiness(CourseService service) {
        this.service = service;
    }

    public List<String> retrieveCoursesRelatedToSpring(String student) {
        List<String> allCourses = service.retrieveCourses(student);

        if ("Foo Bar".equals(student)) return Arrays.asList();

        List<String> filteredCourses = allCourses.stream()
            .filter((course) -> course.contains("Spring"))
            .collect(Collectors.toList());

        return filteredCourses;
    }

    public void deleteCoursesRelatedToSpring(String student) {
        List<String> allCourses = service.retrieveCourses(student);

        allCourses.forEach((course) -> {
            if (!course.contains("Spring")) service.deleteCourse(course);
        });
    }
}
