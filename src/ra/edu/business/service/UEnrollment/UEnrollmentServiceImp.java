package ra.edu.business.service.UEnrollment;

import ra.edu.business.dao.UEnrollment.UEnrollmentDao;
import ra.edu.business.model.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class UEnrollmentServiceImp implements UEnrollmentService {
    private final UEnrollmentDao enrollmentDao;

    public UEnrollmentServiceImp(UEnrollmentDao enrollmentDao) {
        this.enrollmentDao = enrollmentDao;
    }

    @Override
    public List<Enrollment> findAll() {
        return enrollmentDao.findAll();
    }

    @Override
    public boolean save(Enrollment enrollment) {
        return enrollmentDao.save(enrollment);
    }

    @Override
    public boolean update(Enrollment enrollment) {
        return enrollmentDao.update(enrollment);
    }

    @Override
    public boolean delete(Enrollment enrollment) {
        return enrollmentDao.delete(enrollment);
    }

    @Override
    public boolean registerCourse(int studentId, int courseId) {
        return enrollmentDao.registerCourse(studentId, courseId);
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudent(int studentId, int page) {
        if (page < 1) {
            return new ArrayList<>();
        }
        return enrollmentDao.getEnrollmentsByStudent(studentId, page);
    }

    @Override
    public int countEnrollmentsByStudent(int studentId) {
        return enrollmentDao.countEnrollmentsByStudent(studentId);
    }

    @Override
    public List<Enrollment> getWaitingEnrollmentsByStudent(int studentId) {
        return enrollmentDao.getWaitingEnrollmentsByStudent(studentId);
    }

    @Override
    public boolean cancelEnrollment(int enrollmentId, int studentId) {
        return enrollmentDao.cancelEnrollment(enrollmentId, studentId);
    }
}