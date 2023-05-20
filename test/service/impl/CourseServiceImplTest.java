package service.impl;

import bean.Course;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceImplTest {

    @Test
    void calculateGradePoint() {
        CourseServiceImpl service = new CourseServiceImpl();
        service.CalculateGradePoint("91");
        assertEquals("4.0", service.CalculateGradePoint("91"));
        service.CalculateGradePoint("79");
        assertEquals("2.7", service.CalculateGradePoint("79"));
    }

    @Test
    void get() {
        Course course = new CourseServiceImpl().get("EBU5027");
        assertEquals("e-Commerce Law", course.getcName());
        assertEquals("85", course.getcScore());
        assertEquals("3.3", course.getcGradePoint());
    }

    @Test
    void getAll() {
        List<Course> actualCourses = new CourseServiceImpl().getAll("2020213301");
        CourseServiceImpl courseService = new CourseServiceImpl();
        assertNotNull(actualCourses);
        assertEquals(4, actualCourses.size());
        List<Course> list = courseService.getAll("2020213301");
        assertEquals("85", list.get(0).getcScore());
    }
}