package service;

import bean.Course;
import java.util.List;

public interface CourseService {

    public void save(Course course);

    public void delete(String cId);

    public void update(String cId, Course course);

    public Course get(String cId);

    public List<Course> getAll(String username);

}