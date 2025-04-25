package ra.edu.business.dao.Student;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.Student;

import java.util.List;

public interface StudentDao extends AppDao<Student> {
    List<Student> pagination(int page);
    Student findStudentById(int id);
    boolean updatePassword(int studentId, String newPassword);
    List<Student> searchByNameEmailId(String searchValue, int page);
    List<Student> sort(String field, String order, int page);
    int countStudents();
    int countStudentsByNameEmailId(String searchValue);
    boolean checkEmailExists(String email, int studentId);
}