package ra.edu.business.service.Course;

import ra.edu.business.model.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCoursesByPage(int page);
    boolean addCourse(Course course);
    boolean updateCourse(Course course);
    Course findCourseById(String id);
}