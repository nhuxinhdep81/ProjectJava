package ra.edu.presentation.Admin;

import ra.edu.presentation.Course.CourseUI;

import java.util.Scanner;

public class AdminUI {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayMenuAdmin() {
        int choice;
        do {
            System.out.println("========= MENU ADMIN =========");
            System.out.println("1. Quản lý khóa học");
            System.out.println("2. Quản lý học viên");
            System.out.println("3. Quản lý đăng ký học");
            System.out.println("4. Thống kê học viên theo khóa học");
            System.out.println("5. Đăng xuất");
            System.out.println("================================");
            System.out.print("Nhập lựa chọn của bạn: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println(" Quản lý khóa học");
                    CourseUI.displayCourseUI();
                    break;
                case 2:
                    System.out.println(" Quản lý học viên");
                    break;
                case 3:
                    System.out.println(" Quản lý đăng ký học");
                    break;
                case 4:
                    System.out.println(" Thống kê học viên theo khóa học");
                    break;
                case 5:
                    System.out.println(" Đã đăng xuất.");
                    break;
                default:
                    System.out.println(" Lựa chọn không hợp lệ. Vui lòng thử lại!");
            }

        } while (choice != 5);
    }
}

