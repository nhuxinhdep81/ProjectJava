package ra.edu.business.service.Auth;

import ra.edu.business.dao.Auth.AuthDao;
import ra.edu.business.dao.Auth.AuthDaoImp;
import ra.edu.business.model.Admin;
import ra.edu.business.model.Student;

public class AuthServiceImp implements AuthService {
    private static Object currentUser = null;
    private final AuthDao authDAO = new AuthDaoImp();

    @Override
    public Object login(String username, String password) {
        Admin admin = authDAO.adminLogin(username, password);
        if (admin != null) {
            currentUser = admin;
            return admin;
        }
        Student student = authDAO.studentLogin(username, password);
        if (student != null) {
            currentUser = student;
            return student;
        }
        return null;
    }

    @Override
    public boolean registerStudent(Student student) {
        if (student == null) {
            System.out.println(RED + "Thông tin học viên không hợp lệ." + RESET);
            return false;
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty() || !student.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            System.out.println(RED + "Email không hợp lệ." + RESET);
            return false;
        }
        if (student.getPassword() == null || student.getPassword().length() < 6) {
            System.out.println(RED + "Mật khẩu phải có ít nhất 6 ký tự." + RESET);
            return false;
        }
        if (student.getPhone() != null && !student.getPhone().isEmpty() && !student.getPhone().matches("\\d{10}")) {
            System.out.println(RED + "Số điện thoại phải có đúng 10 chữ số." + RESET);
            return false;
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            System.out.println(RED + "Tên không được để trống." + RESET);
            return false;
        }
        if (student.getDob() == null) {
            System.out.println(RED + "Ngày sinh không hợp lệ." + RESET);
            return false;
        }

        return authDAO.registerStudent(student);
    }

    @Override
    public boolean isStudentDeactivated(String email) {
        return authDAO.isStudentDeactivated(email);
    }

    // Thêm các hằng số màu để sử dụng trong class này
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";
}