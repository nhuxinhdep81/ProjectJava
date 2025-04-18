package ra.edu.business.dao.Course;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.Course;

import java.util.List;

public interface CourseDao extends AppDao {
    List<Course> searchByName(String name, int page);
    List<Course> sort(String field, String order, int page);
    Course findCourseById(int id);
    List<Course> pagination(int page);
    int countCourses();
    int countCoursesByName(String name);
    boolean existsByName(String name);
    boolean existsByNameExceptId(String name, int courseId);
}