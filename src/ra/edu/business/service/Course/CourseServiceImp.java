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
        if (course == null) {
            return false;
        }
        return courseDao.save(course);
    }

    @Override
    public boolean updateCourse(Course course) {
        if (course == null) {
            return false;
        }
        return courseDao.update(course);
    }

    @Override
    public Course findCourseById(int id) {
        return courseDao.findCourseById(id);
    }

    @Override
    public boolean deleteCourseById(int id) {
        Course course = findCourseById(id);
        if (course == null) {
            return false;
        }
        return courseDao.delete(course);
    }

    @Override
    public List<Course> searchByName(String name, int page) {
        if (name == null || name.trim().isEmpty() || page < 1) {
            return new ArrayList<>();
        }
        return courseDao.searchByName(name.trim(), page);
    }

    @Override
    public List<Course> sortByField(String field, String order, int page) {
        if (field == null || order == null || page < 1 ||
                (!field.equals("name") && !field.equals("course_id")) ||
                (!order.equals("ASC") && !order.equals("DESC"))) {
            return new ArrayList<>();
        }
        return courseDao.sort(field, order, page);
    }

    @Override
    public int countCourses() {
        return courseDao.countCourses();
    }

    @Override
    public int countCoursesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return 0;
        }
        return courseDao.countCoursesByName(name.trim());
    }

    @Override
    public boolean existsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return courseDao.existsByName(name.trim());
    }

    @Override
    public boolean existsByNameExceptId(String name, int courseId) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return courseDao.existsByNameExceptId(name.trim(), courseId);
    }
}