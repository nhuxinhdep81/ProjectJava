package ra.edu.business.dao.UEnrollment;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.Enrollment;

import java.util.List;

public interface UEnrollmentDao extends AppDao<Enrollment> {
    boolean registerCourse(int studentId, int courseId);
    List<Enrollment> getEnrollmentsByStudent(int studentId, int page);
    int countEnrollmentsByStudent(int studentId);
    List<Enrollment> getWaitingEnrollmentsByStudent(int studentId);
    boolean cancelEnrollment(int enrollmentId, int studentId);
}