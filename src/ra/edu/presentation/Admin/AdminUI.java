package ra.edu.presentation.Admin;

import ra.edu.presentation.Course.CourseUI;

import java.util.Scanner;

public class AdminUI {
    private static final Scanner scanner = new Scanner(System.in);

    // Mã màu ANSI
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String PURPLE = "\u001B[35m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    public static void displayMenuAdmin() {
        int choice;
        do {
            System.out.println(PURPLE + "\n╔══════════════════════════════════════════╗");
            System.out.println("║ " + YELLOW + "         MENU QUẢN TRỊ VIÊN" + PURPLE + "              ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.println("║ " + CYAN + "1. 📚 Quản lý khóa học" + PURPLE + "                   ║");
            System.out.println("║ " + CYAN + "2. 👨‍🎓 Quản lý học viên" + PURPLE + "                   ║");
            System.out.println("║ " + CYAN + "3. 📝 Quản lý đăng ký học" + PURPLE + "                ║");
            System.out.println("║ " + CYAN + "4. 📊 Thống kê học viên theo khóa học" + PURPLE + "    ║");
            System.out.println("║ " + CYAN + "5. 🔒 Đăng xuất" + PURPLE + "                          ║");
            System.out.println("╚══════════════════════════════════════════╝" + RESET);
            System.out.print(GREEN + "→ Mời bạn chọn chức năng (1-5): " + RESET);

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println(BLUE + "🔧 Đang mở giao diện quản lý khóa học..." + RESET);
                    CourseUI.displayCourseUI();
                    break;
                case 2:
                    System.out.println(BLUE + "🔧 Đang phát triển chức năng quản lý học viên..." + RESET);
                    break;
                case 3:
                    System.out.println(BLUE + "🔧 Đang phát triển chức năng quản lý đăng ký học..." + RESET);
                    break;
                case 4:
                    System.out.println(BLUE + "📈 Đang phát triển chức năng thống kê học viên..." + RESET);
                    break;
                case 5:
                    System.out.println(YELLOW + "✅ Đã đăng xuất. Quay lại màn hình chính..." + RESET);
                    break;
                default:
                    System.out.println(RED + "❌ Lựa chọn không hợp lệ. Vui lòng thử lại!" + RESET);
            }

        } while (choice != 5);
    }
}
