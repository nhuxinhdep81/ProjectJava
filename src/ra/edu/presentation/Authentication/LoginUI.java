package ra.edu.presentation.Authentication;

import ra.edu.MainApplication;
import ra.edu.business.model.Admin;
import ra.edu.business.model.Student;
import ra.edu.business.service.Auth.AuthServiceImp;
import ra.edu.presentation.Admin.AdminUI;
import ra.edu.presentation.Student.StudentUI;
import ra.edu.validate.Validator;

public class LoginUI {
    public static void login(int role) {
        AuthServiceImp authService = new AuthServiceImp();
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("=== ĐĂNG NHẬP ===");
            String username = Validator.validateUsername(MainApplication.sc);
            String password = Validator.validatePassword(MainApplication.sc);

            Object user = authService.login(username, password);
            if (user == null) {
                System.out.println("Đăng nhập thất bại. Vui lòng thử lại.");
            } else {
                loggedIn = true;
                if (role == 1 && user instanceof Admin) {
                    System.out.println("Chào mừng Quản trị viên!");
                    AdminUI.displayMenuAdmin();
                } else if (role == 2 && user instanceof Student) {
                    System.out.println("Chào mừng Học viên!");
                    StudentUI.displayMenuStudent();
                } else {
                    System.out.println("Tài khoản không thuộc vai trò đã chọn. Vui lòng thử lại.");
                    loggedIn = false;
                }
            }
        }
    }
}
