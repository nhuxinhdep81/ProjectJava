package ra.edu.business.service.Course;

import ra.edu.business.model.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCoursesByPage(int page);
    boolean addCourse(Course course);
    boolean updateCourse(Course course);
    Course findCourseById(int id);
    boolean deleteCourseById(int id);
    List<Course> searchByName(String name, int page);
    List<Course> sortByField(String field, String order, int page);
    int countCourses();
    int countCoursesByName(String name);
    boolean existsByName(String name);
    boolean existsByNameExceptId(String name, int courseId);
}