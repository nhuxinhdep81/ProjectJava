package ra.edu.validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static int validateInteger(String message, Scanner sc) {
        while (true) {
            try {
                System.out.print(message);
                String input = sc.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Giá trị nhập vào không phải số nguyên. Vui lòng thử lại");
            }
        }
    }

    public static boolean validateBoolean(String message, Scanner sc) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            }
            System.out.println("Giá trị không hợp lệ. Vui lòng nhập 'true' hoặc 'false'");
        }
    }

    public static String validateString(String message, Scanner sc, int min, int max) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Giá trị không được để trống");
            } else if (input.length() < min || input.length() > max) {
                System.out.println("Độ dài phải từ " + min + " đến " + max + " ký tự");
            } else {
                return input;
            }
        }
    }

    public static String validateEmail(Scanner sc) {
        String regex = "^[\\w.-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(regex);

        while (true) {
            String input = sc.nextLine().trim();
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                return input;
            }
            System.out.println("Định dạng email không hợp lệ. Vui lòng nhập email @gmail.com");
        }
    }

    public static String validatePhone(String message, Scanner sc) {
        String regex = "^(03|05|07|08|09)\\d{8}$";

        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (input.matches(regex)) {
                return input;
            }
            System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập số hợp lệ ở Việt Nam");
        }
    }

    public static LocalDate validateDate(String message, Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ (dd/MM/yyyy)");
            }
        }
    }

    public static String validateUsername(Scanner sc) {
        while (true) {
            System.out.print("Nhập tên người dùng: ");
            String username = sc.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Tên người dùng không được để trống. Vui lòng thử lại");
                continue;
            }

            return username;
        }
    }

    public static String validatePassword(Scanner sc) {
        while (true) {
            System.out.print("Nhập mật khẩu: ");
            String password = sc.nextLine().trim();

            if (password.isEmpty()) {
                System.out.println("Mật khẩu không được để trống. Vui lòng thử lại");
                continue;
            }
            if (password.length() < 6) {
                System.out.println("Mật khẩu phải có ít nhất 6 ký tự. Vui lòng thử lại");
                continue;
            }

            return password;
        }
    }
}
