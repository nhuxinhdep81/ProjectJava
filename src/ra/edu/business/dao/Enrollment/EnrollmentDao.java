package ra.edu.business.dao.Enrollment;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.Enrollment;

import java.util.List;

public interface EnrollmentDao extends AppDao<Enrollment> {
    List<Enrollment> getConfirmedEnrollmentsByCourse(int courseId, int page);
    int countConfirmedEnrollmentsByCourse(int courseId);
    List<Enrollment> getDeniedCancelEnrollmentsByCourse(int courseId, int page);
    int countDeniedCancelEnrollmentsByCourse(int courseId);
    List<Enrollment> getPendingEnrollments();
    boolean approveEnrollment(int enrollmentId, String action);
    boolean deleteEnrollment(int courseId, int studentId);
}