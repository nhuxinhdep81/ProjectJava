package ra.edu.business.service.Course;

import ra.edu.business.dao.Course.CourseDao;
import ra.edu.business.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseServiceImp implements CourseService {
    private final CourseDao courseDao;

    public CourseServiceImp(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public List<Course> getCoursesByPage(int page) {
        if (page < 1) {
            return new ArrayList<>();
        }
        return courseDao.pagination(page);
    }

    @Override
    public boolean addCourse(Course course) {
        if (course == null || course.getCourseId() == null) {
            return false;
        }
        return courseDao.save(course);
    }

    @Override
    public boolean updateCourse(Course course) {
        if (course == null || course.getCourseId() == null) {
            return false;
        }
        return courseDao.update(course);
    }

    @Override
    public Course findCourseById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return courseDao.findCourseById(id);
    }
}