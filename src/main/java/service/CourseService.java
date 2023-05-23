package service;

import bean.Course;
import java.util.List;

public interface CourseService {

    /**
     * Saves a course.
     *
     * @param course Course object to be saved
     */
    public void save(Course course);

    /**
     * Deletes a course with the specified course ID and username.
     *
     * @param cId      Course ID
     * @param username User name
     */
    public void delete(String cId, String username);

    /**
     * Updates a course with the specified course ID.
     *
     * @param cId    Course ID
     * @param course Updated Course object
     */
    public void update(String cId, Course course);

    /**
     * Retrieves a course with the specified course ID.
     *
     * @param cId Course ID
     * @return Course object if found, null otherwise
     */
    public Course get(String cId);

    /**
     * Retrieves all courses associated with the specified username.
     *
     * @param username User name
     * @return List of Course objects associated with the username
     */
    public List<Course> getAll(String username);

}