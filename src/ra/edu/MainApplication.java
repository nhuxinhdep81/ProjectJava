package ra.edu;

import ra.edu.presentation.Authentication.LoginUI;

import java.util.Scanner;

public class MainApplication {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("======== HỆ THỐNG QUẢN LÝ ĐÀO TẠO ========");
            System.out.println("1. Đăng nhập với tư cách Quản trị viên");
            System.out.println("2. Đăng nhập với tư cách Học viên");
            System.out.println("3. Thoát");
            System.out.println("===========================================");
            System.out.print("Nhập lựa chọn: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                case 2:
                    LoginUI.login(choice);
                    break;
                case 3:
                    System.out.println("Cảm ơn bạn đã sử dụng hệ thống. Tạm biệt!");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }
}
