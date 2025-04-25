package ra.edu.business.service.UCourse;

import ra.edu.business.dao.UCourse.UCourseDao;
import ra.edu.business.model.Course;

import java.util.ArrayList;
import java.util.List;

public class UCourseServiceImp implements UCourseService {
    private final UCourseDao courseDao;

    public UCourseServiceImp(UCourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    @Override
    public boolean save(Course course) {
        if (course == null) {
            return false;
        }
        return courseDao.save(course);
    }

    @Override
    public boolean update(Course course) {
        if (course == null) {
            return false;
        }
        return courseDao.update(course);
    }

    @Override
    public boolean delete(Course course) {
        if (course == null) {
            return false;
        }
        return courseDao.delete(course);
    }

    @Override
    public List<Course> getCoursesByPage(int page) {
        if (page < 1) {
            return new ArrayList<>();
        }
        return courseDao.pagination(page);
    }

    @Override
    public Course findCourseById(int id) {
        return courseDao.findCourseById(id);
    }

    @Override
    public List<Course> searchByName(String name, int page) {
        if (name == null || name.trim().isEmpty() || page < 1) {
            return new ArrayList<>();
        }
        return courseDao.searchByName(name.trim(), page);
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
}