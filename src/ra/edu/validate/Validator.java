package ra.edu.validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(03|05|07|08|09)\\d{8}$");

    public static int validateInteger(String message, Scanner sc, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                String input = sc.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Giá trị phải từ " + min + " đến " + max + ". Vui lòng thử lại.");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên hợp lệ.");
            }
        }
    }

    public static boolean validateSex(String message, Scanner sc) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (input.equals("1") || input.equalsIgnoreCase("nam")) {
                return true;
            } else if (input.equals("0") || input.equalsIgnoreCase("nữ") || input.equalsIgnoreCase("nu")) {
                return false;
            }
            System.out.println("Vui lòng nhập '1' hoặc 'Nam' cho Nam, '0' hoặc 'Nữ' cho Nữ.");
        }
    }

    public static String validateString(String message, Scanner sc, int min, int max, String fieldName) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println(fieldName + " không được để trống.");
            } else if (input.length() < min || input.length() > max) {
                System.out.println(fieldName + " phải có độ dài từ " + min + " đến " + max + " ký tự.");
            } else {
                return input;
            }
        }
    }

    public static String validateEmail(String message, Scanner sc) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (EMAIL_PATTERN.matcher(input).matches()) {
                return input;
            }
            System.out.println("Email không hợp lệ. Vui lòng nhập email hợp lệ (ví dụ: user@domain.com).");
        }
    }

    public static String validatePhone(String message, Scanner sc, boolean allowEmpty) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (allowEmpty && input.isEmpty()) {
                return null;
            }
            if (PHONE_PATTERN.matcher(input).matches()) {
                return input;
            }
            System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập số 10 chữ số, bắt đầu bằng 03/05/07/08/09.");
        }
    }

    public static LocalDate validateDate(String message, Scanner sc, int minAge, int maxAge) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            try {
                LocalDate date = LocalDate.parse(input, formatter);
                LocalDate now = LocalDate.now();
                if (date.isBefore(now.minusYears(minAge)) && date.isAfter(now.minusYears(maxAge))) {
                    return date;
                }
                System.out.println("Ngày sinh phải từ " + minAge + " đến " + maxAge + " tuổi.");
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ (dd/MM/yyyy). Vui lòng thử lại.");
            }
        }
    }

    public static String validateUsername(String message, Scanner sc, int min, int max) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Tên người dùng không được để trống.");
            } else if (input.length() < min || input.length() > max) {
                System.out.println("Tên người dùng phải có độ dài từ " + min + " đến " + max + " ký tự.");
            } else {
                return input;
            }
        }
    }

    public static String validatePassword(String message, Scanner sc, int minLength) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Mật khẩu không được để trống.");
            } else if (input.length() < minLength) {
                System.out.println("Mật khẩu phải có ít nhất " + minLength + " ký tự.");
            } else {
                return input;
            }
        }
    }
}