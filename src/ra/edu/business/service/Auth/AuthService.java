package ra.edu.business.service.Auth;

import ra.edu.business.model.Student;

public interface AuthService {
    Object login(String username, String password);
    boolean registerStudent(Student student);
}