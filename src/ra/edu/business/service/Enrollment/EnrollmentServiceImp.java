package ra.edu.business.service.Enrollment;

import ra.edu.business.dao.Enrollment.EnrollmentDao;
import ra.edu.business.model.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentServiceImp implements EnrollmentService {
    private final EnrollmentDao enrollmentDao;

    public EnrollmentServiceImp(EnrollmentDao enrollmentDao) {
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
    public List<Enrollment> getConfirmedEnrollmentsByCourse(int courseId, int page) {
        if (courseId < 1 || page < 1) {
            return new ArrayList<>();
        }
        return enrollmentDao.getConfirmedEnrollmentsByCourse(courseId, page);
    }

    @Override
    public int countConfirmedEnrollmentsByCourse(int courseId) {
        if (courseId < 1) {
            return 0;
        }
        return enrollmentDao.countConfirmedEnrollmentsByCourse(courseId);
    }

    @Override
    public List<Enrollment> getDeniedCancelEnrollmentsByCourse(int courseId, int page) {
        if (courseId < 1 || page < 1) {
            return new ArrayList<>();
        }
        return enrollmentDao.getDeniedCancelEnrollmentsByCourse(courseId, page);
    }

    @Override
    public int countDeniedCancelEnrollmentsByCourse(int courseId) {
        if (courseId < 1) {
            return 0;
        }
        return enrollmentDao.countDeniedCancelEnrollmentsByCourse(courseId);
    }

    @Override
    public List<Enrollment> getPendingEnrollments() {
        return enrollmentDao.getPendingEnrollments();
    }

    @Override
    public boolean approveEnrollment(int enrollmentId, String action) {
        if (enrollmentId < 1) {
            return false;
        }
        return enrollmentDao.approveEnrollment(enrollmentId, action);
    }

    @Override
    public boolean deleteEnrollment(int courseId, int studentId) {
        if (courseId < 1 || studentId < 1) {
            return false;
        }
        return enrollmentDao.deleteEnrollment(courseId, studentId);
    }
}