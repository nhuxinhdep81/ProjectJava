package ra.edu.business.dao.Course;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.Course;

import java.util.List;


public interface CourseDao extends AppDao {
    List<Course> search(String name);
    List<Course> sort();
    Course findCourseById(String id);
    List<Course> pagination(int page);
}
