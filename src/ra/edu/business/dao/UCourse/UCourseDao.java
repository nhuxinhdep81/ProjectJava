package ra.edu.business.dao.UCourse;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.Course;

import java.util.List;

public interface UCourseDao extends AppDao<Course> {
    List<Course> pagination(int page);
    Course findCourseById(int id);
    List<Course> searchByName(String name, int page);
    int countCourses();
    int countCoursesByName(String name);
}