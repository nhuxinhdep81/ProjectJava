package ra.edu;

import ra.edu.business.model.Student;
import ra.edu.business.service.Auth.AuthService;
import ra.edu.business.service.Auth.AuthServiceImp;
import ra.edu.presentation.Authentication.LoginUI;
import ra.edu.validate.Student.StudentValidator;

import java.time.LocalDate;
import java.util.Scanner;

public class MainApplication {
    public static Scanner sc = new Scanner(System.in);
    private static final AuthService authService = new AuthServiceImp();

    // Mã màu ANSI
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String PURPLE = "\u001B[35m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        while (true) {
            System.out.println(PURPLE + "\n╔════════════════════════════════════════╗");
            System.out.println("║ " + YELLOW + "       HỆ THỐNG QUẢN LÝ ĐÀO TẠO" + PURPLE + "        ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ " + CYAN + "1. Đăng nhập với tư cách Quản trị viên" + PURPLE + " ║");
            System.out.println("║ " + CYAN + "2. Đăng nhập với tư cách Học viên" + PURPLE + "      ║");
            System.out.println("║ " + CYAN + "3. Đăng ký tài khoản Học viên" + PURPLE + "          ║");
            System.out.println("║ " + CYAN + "4. Thoát" + PURPLE + "                               ║");
            System.out.println("╚════════════════════════════════════════╝" + RESET);
            System.out.print(GREEN + "→ Mời bạn chọn chức năng (1-4): " + RESET);

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Vui lòng nhập số nguyên từ 1 đến 4!" + RESET);
                continue;
            }

            switch (choice) {
                case 1:
                case 2:
                    LoginUI.login(choice);
                    break;
                case 3:
                    registerStudent();
                    break;
                case 4:
                    System.out.println(YELLOW + "Cảm ơn bạn đã sử dụng hệ thống. Tạm biệt!" + RESET);
                    System.exit(0);
                default:
                    System.out.println(RED + "❌ Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 4!" + RESET);
            }
        }
    }

    private static void registerStudent() {
        System.out.println(CYAN + "\nĐăng ký tài khoản Học viên:" + RESET);
        String name = StudentValidator.validateName(sc);
        LocalDate dob = StudentValidator.validateDob(sc);
        String email = StudentValidator.validateEmail(sc);
        boolean sex = StudentValidator.validateSex(sc);
        String phone = StudentValidator.validatePhone(sc);
        String password = StudentValidator.validatePassword(sc);

        Student student = new Student(0, name, dob, email, sex, phone, password, LocalDate.now(), true);
        if (authService.registerStudent(student)) {
            System.out.println(GREEN + "Đăng ký tài khoản thành công! Bạn có thể đăng nhập ngay." + RESET);
        } else {
            System.out.println(RED + "Đăng ký thất bại! Email có thể đã tồn tại." + RESET);
        }
    }
}