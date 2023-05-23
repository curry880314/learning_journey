package service.impl;

import bean.Course;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceImplTest {

    @Test
    void calculateGradePoint() {
        // Creating an instance of CourseServiceImpl
        CourseServiceImpl service = new CourseServiceImpl();

        // Calculating grade points for a score
        service.CalculateGradePoint("91");

        // Asserting that the calculated grade point is as expected
        assertEquals("4.0", service.CalculateGradePoint("91"));

        // Calculating grade points for another score
        service.CalculateGradePoint("79");

        // Asserting that the calculated grade point is as expected
        assertEquals("2.7", service.CalculateGradePoint("79"));
    }

    @Test
    void get() {
        // Retrieving a course
        Course course = new CourseServiceImpl().get("EBU5027");

        // Asserting that the course name is as expected
        assertEquals("e-Commerce Law", course.getcName());

        // Asserting that the course score is as expected
        assertEquals("85", course.getcScore());

        // Asserting that the course grade point is as expected
        assertEquals("3.3", course.getcGradePoint());
    }

    @Test
    void getAll() {
        // Retrieving all courses for a specific user
        List<Course> actualCourses = new CourseServiceImpl().getAll("2020213301");

        // Creating an instance of CourseServiceImpl
        CourseServiceImpl courseService = new CourseServiceImpl();

        // Asserting that the retrieved courses list is not null
        assertNotNull(actualCourses);

        // Asserting that the number of retrieved courses is as expected
        assertEquals(4, actualCourses.size());

        // Retrieving all courses using another method
        List<Course> list = courseService.getAll("2020213301");

        // Asserting that the score of the first course in the list is as expected
        assertEquals("85", list.get(0).getcScore());
    }

}