package ra.edu.presentation.Student;


import java.util.Scanner;

public class StudentUI {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayMenuStudent() {
        int choice;
        do {
            System.out.println("========= MENU HỌC VIÊN =========");
            System.out.println("1. Xem danh sách khóa học");
            System.out.println("2. Đăng ký khóa học");
            System.out.println("3. Xem khóa học đã đăng ký");
            System.out.println("4. Hủy đăng ký (nếu chưa bắt đầu)");
            System.out.println("5. Đổi mật khẩu");
            System.out.println("6. Đăng xuất");
            System.out.println("===================================");
            System.out.print("Nhập lựa chọn của bạn: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    System.out.println(" Đã đăng xuất.");
                    break;
                default:
                    System.out.println("️ Lựa chọn không hợp lệ. Vui lòng thử lại!");
            }

        } while (choice != 6);
    }
}

