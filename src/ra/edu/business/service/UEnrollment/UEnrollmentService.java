package ra.edu.business.service.UEnrollment;

import ra.edu.business.model.Enrollment;
import ra.edu.business.service.AppService;

import java.util.List;

public interface UEnrollmentService extends AppService<Enrollment> {
    boolean registerCourse(int studentId, int courseId);
    List<Enrollment> getEnrollmentsByStudent(int studentId, int page);
    int countEnrollmentsByStudent(int studentId);
    List<Enrollment> getWaitingEnrollmentsByStudent(int studentId);
    boolean cancelEnrollment(int enrollmentId, int studentId);
}