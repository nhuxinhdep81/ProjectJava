package ra.edu.business.service.Enrollment;

import ra.edu.business.model.Enrollment;
import ra.edu.business.service.AppService;

import java.util.List;

public interface EnrollmentService extends AppService<Enrollment> {
    List<Enrollment> getConfirmedEnrollmentsByCourse(int courseId, int page); // Giữ nguyên
    int countConfirmedEnrollmentsByCourse(int courseId); // Giữ nguyên
    List<Enrollment> getDeniedCancelEnrollmentsByCourse(int courseId, int page); // Thêm mới
    int countDeniedCancelEnrollmentsByCourse(int courseId); // Thêm mới
    List<Enrollment> getPendingEnrollments();
    boolean approveEnrollment(int enrollmentId, String action);
    boolean deleteEnrollment(int courseId, int studentId);
}