package ra.edu.validate.Student;

import ra.edu.validate.Validator;

import java.time.LocalDate;
import java.util.Scanner;

public class StudentValidator {
    private static final Validator validator = new Validator();

    public static String validateName(Scanner scanner) {
        return validator.validateString("Nhập tên học viên (1-100 ký tự): ", scanner, 1, 100, "Tên học viên");
    }

    public static LocalDate validateDob(Scanner scanner) {
        return validator.validateDate("Nhập ngày sinh (dd/MM/yyyy): ", scanner, 15, 100);
    }

    public static String validateEmail(Scanner scanner) {
        return validator.validateEmail("Nhập email: ", scanner);
    }

    public static boolean validateSex(Scanner scanner) {
        return validator.validateSex("Nhập giới tính (1/Nam hoặc 0/Nữ): ", scanner);
    }

    public static String validatePhone(Scanner scanner) {
        return validator.validatePhone("Nhập số điện thoại (10 số, để trống nếu không có): ", scanner, true);
    }

    public static String validatePassword(Scanner scanner) {
        return validator.validatePassword("Nhập mật khẩu (tối thiểu 6 ký tự): ", scanner, 6);
    }
}