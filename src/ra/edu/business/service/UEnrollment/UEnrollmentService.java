package ra.edu.business.service.UEnrollment;

import ra.edu.business.model.Enrollment;
import ra.edu.business.service.AppService;

import java.util.List;

public interface UEnrollmentService extends AppService<Enrollment> {
    List<Enrollment> getEnrollmentsByStudent(int studentId, int page);
    List<Enrollment> getWaitingEnrollmentsByStudent(int studentId);
    int countEnrollmentsByStudent(int studentId);
    boolean registerCourse(int studentId, int courseId);
    boolean cancelEnrollment(int enrollmentId, int studentId);
    List<Enrollment> getEnrollmentsByStudentSorted(int studentId, String field, String order, int page);
}