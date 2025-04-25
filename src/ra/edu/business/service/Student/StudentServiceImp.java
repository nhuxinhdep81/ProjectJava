package ra.edu.business.service.Student;

import ra.edu.business.dao.Student.StudentDao;
import ra.edu.business.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImp implements StudentService {
    private final StudentDao studentDao;

    // Thêm các hằng số màu để sử dụng trong class này
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    public StudentServiceImp(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public boolean save(Student student) {
        if (student == null) {
            System.out.println(RED + "Thông tin học viên không hợp lệ." + RESET);
            return false;
        }
        return studentDao.save(student);
    }

    @Override
    public boolean update(Student student) {
        if (student == null) {
            System.out.println(RED + "Thông tin học viên không hợp lệ." + RESET);
            return false;
        }
        return studentDao.update(student);
    }

    @Override
    public boolean delete(Student student) {
        if (student == null) {
            System.out.println(RED + "Thông tin học viên không hợp lệ." + RESET);
            return false;
        }
        return studentDao.delete(student);
    }

    @Override
    public List<Student> getStudentsByPage(int page) {
        if (page < 1) {
            return new ArrayList<>();
        }
        return studentDao.pagination(page);
    }

    @Override
    public Student findStudentById(int id) {
        return studentDao.findStudentById(id);
    }

    @Override
    public boolean disableStudentById(int id) {
        Student student = studentDao.findStudentById(id);
        if (student == null) {
            System.out.println(RED + "Không tìm thấy học viên với ID " + id + "!" + RESET);
            return false;
        }
        return studentDao.delete(student);
    }

    @Override
    public List<Student> searchByNameEmailId(String searchValue, int page) {
        if (searchValue == null || searchValue.trim().isEmpty() || page < 1) {
            return new ArrayList<>();
        }
        return studentDao.searchByNameEmailId(searchValue.trim(), page);
    }

    @Override
    public List<Student> sortByField(String field, String order, int page) {
        if (field == null || order == null || page < 1) {
            return new ArrayList<>();
        }
        return studentDao.sort(field.trim(), order.trim(), page);
    }

    @Override
    public int countStudents() {
        return studentDao.countStudents();
    }

    @Override
    public int countStudentsByNameEmailId(String searchValue) {
        if (searchValue == null || searchValue.trim().isEmpty()) {
            return 0;
        }
        return studentDao.countStudentsByNameEmailId(searchValue.trim());
    }

    @Override
    public boolean updatePassword(int studentId, String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }
        return studentDao.updatePassword(studentId, newPassword.trim());
    }

    @Override
    public boolean checkEmailExists(String email, int studentId) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return studentDao.checkEmailExists(email.trim(), studentId);
    }
}