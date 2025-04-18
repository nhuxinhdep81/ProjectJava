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
        if (student == null || student.getEmail() == null || student.getPassword() == null) {
            return false;
        }
        return authDAO.registerStudent(student);
    }
}