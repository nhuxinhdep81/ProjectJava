package ra.edu.presentation.Authentication;

import ra.edu.MainApplication;
import ra.edu.business.model.Admin;
import ra.edu.business.model.Student;
import ra.edu.business.service.Auth.AuthServiceImp;
import ra.edu.presentation.Admin.AdminUI;
import ra.edu.presentation.Student.StudentUI;
import ra.edu.validate.Validator;

public class LoginUI {
    // Mã màu ANSI
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    public static void login(int role) {
        AuthServiceImp authService = new AuthServiceImp();
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println(CYAN + "\n╔════════════════════════════╗");
            System.out.println("║ " + YELLOW + "  ĐĂNG NHẬP" + (role == 1 ? " QUẢN TRỊ VIÊN" : " BẰNG HỌC VIÊN") + CYAN + "  ║");
            System.out.println("╚════════════════════════════╝" + RESET);

            String username;
            if (role == 1) {
                username = Validator.validateUsername("Nhập tên đăng nhập (5-50 ký tự): ", MainApplication.sc, 5, 50);
            } else {
                username = Validator.validateEmail("Nhập email: ", MainApplication.sc);
            }
            String password = Validator.validatePassword("Nhập mật khẩu (tối thiểu 6 ký tự): ", MainApplication.sc, 6);

            System.out.print(GREEN + "Nhấn Enter để đăng nhập hoặc 'q' để quay lại: " + RESET);
            String action = MainApplication.sc.nextLine().trim();
            if (action.equalsIgnoreCase("q")) {
                System.out.println(YELLOW + "Quay lại menu chính..." + RESET);
                return;
            }

            Object user = authService.login(username, password);
            if (user == null) {
                System.out.println(RED + "Đăng nhập thất bại. Tên đăng nhập/email hoặc mật khẩu không đúng." + RESET);
            } else {
                loggedIn = true;
                if (role == 1 && user instanceof Admin) {
                    System.out.println(GREEN + "Chào mừng Quản trị viên!" + RESET);
                    AdminUI.displayMenuAdmin();
                } else if (role == 2 && user instanceof Student) {
                    System.out.println(GREEN + "Chào mừng Học viên!" + RESET);
                    StudentUI.displayMenuStudent();
                } else {
                    System.out.println(RED + "Tài khoản không thuộc vai trò đã chọn. Vui lòng thử lại." + RESET);
                    loggedIn = false;
                }
            }
        }
    }
}