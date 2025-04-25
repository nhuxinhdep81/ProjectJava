package ra.edu.validate.Student;

import ra.edu.business.service.Student.StudentService;
import ra.edu.business.service.Student.StudentServiceImp;
import ra.edu.business.dao.Student.StudentDaoImp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StudentValidator {
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final StudentService studentService = new StudentServiceImp(new StudentDaoImp());

    public static String validateStudentName(Scanner scanner) {
        while (true) {
            System.out.print("Nhập tên học viên: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println(RED + "Tên không được để trống!" + RESET);
            } else if (name.length() < 2 || name.length() > 100) {
                System.out.println(RED + "Tên phải từ 2 đến 100 ký tự!" + RESET);
            } else {
                return name;
            }
        }
    }

    public static LocalDate validateDob(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
            try {
                String dobStr = scanner.nextLine().trim();
                if (dobStr.isEmpty()|| dobStr == null) {
                    System.out.println(RED + "Ngày sinh không được trống!" + RESET);
                    continue;
                }
                LocalDate dob = LocalDate.parse(dobStr, formatter);
                LocalDate now = LocalDate.now();
                if (dob.isAfter(now)) {
                    System.out.println(RED + "Ngày sinh không được ở tương lai!" + RESET);
                } else if (dob.isBefore(now.minusYears(100))) {
                    System.out.println(RED + "Ngày sinh không hợp lệ (quá 100 năm)!" + RESET);
                } else {
                    return dob;
                }
            } catch (Exception e) {
                System.out.println(RED + "Ngày sinh không đúng định dạng dd/MM/yyyy!" + RESET);
            }
        }
    }

    public static String validateEmail(Scanner scanner, int studentId) {
        while (true) {
            System.out.print("Nhập email: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println(RED + "Email không được để trống!" + RESET);
                continue;
            }
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                System.out.println(RED + "Email không đúng định dạng (ví dụ: example@domain.com)!" + RESET);
                continue;
            }
            // Kiểm tra email trùng lặp
            if (studentService.checkEmailExists(email, studentId)) {
                System.out.println(RED + "Email đã tồn tại. Vui lòng sử dụng email khác!" + RESET);
                continue;
            }
            return email;
        }
    }

    public static boolean validateSex(Scanner scanner) {
        while (true) {
            System.out.print("Nhập giới tính (Nam/Nữ): ");
            String sexStr = scanner.nextLine().trim();
            if (sexStr.equalsIgnoreCase("Nam")) {
                return true;
            } else if (sexStr.equalsIgnoreCase("Nữ")) {
                return false;
            } else {
                System.out.println(RED + "Giới tính phải là 'Nam' hoặc 'Nữ'!" + RESET);
            }
        }
    }

    public static String validatePhone(Scanner scanner) {
        while (true) {
            System.out.print("Nhập số điện thoại (10 chữ số, để trống nếu không có): ");
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty()) {
                return null;
            }
            if (!phone.matches("\\d{10}")) {
                System.out.println(RED + "Số điện thoại phải có đúng 10 chữ số!" + RESET);
            } else {
                return phone;
            }
        }
    }

    public static String validatePassword(Scanner scanner) {
        while (true) {
            System.out.print("Nhập mật khẩu (ít nhất 6 ký tự): ");
            String password = scanner.nextLine().trim();
            if (password.length() < 6) {
                System.out.println(RED + "Mật khẩu phải có ít nhất 6 ký tự!" + RESET);
            } else {
                return password;
            }
        }
    }
}