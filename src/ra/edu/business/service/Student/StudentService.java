package ra.edu.business.service.Student;

import ra.edu.business.service.AppService;
import ra.edu.business.model.Student;

import java.util.List;

public interface StudentService extends AppService<Student> {
    List<Student> getStudentsByPage(int page);
    Student findStudentById(int id);
    boolean disableStudentById(int id);
    List<Student> searchByNameEmailId(String searchValue, int page);
    List<Student> sortByField(String field, String order, int page);
    int countStudents();
    int countStudentsByNameEmailId(String searchValue);
    boolean updatePassword(int studentId, String newPassword);
    boolean checkEmailExists(String email, int studentId);
}