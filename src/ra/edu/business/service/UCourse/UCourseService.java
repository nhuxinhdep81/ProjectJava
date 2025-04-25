package ra.edu.business.service.UCourse;

import ra.edu.business.model.Course;
import ra.edu.business.service.AppService;

import java.util.List;

public interface UCourseService extends AppService<Course> {
    List<Course> getCoursesByPage(int page);
    Course findCourseById(int id);
    List<Course> searchByName(String name, int page);
    int countCourses();
    int countCoursesByName(String name);
}