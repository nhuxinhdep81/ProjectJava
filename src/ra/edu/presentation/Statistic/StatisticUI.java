package ra.edu.presentation.Statistic;

import ra.edu.MainApplication;
import ra.edu.business.dao.Statistic.StatisticDaoImp;
import ra.edu.business.service.Statistic.StatisticService;
import ra.edu.business.service.Statistic.StatisticServiceImp;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StatisticUI {
    // Mã màu ANSI
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    private static final StatisticService statisticService = new StatisticServiceImp(new StatisticDaoImp());
    private static final Scanner sc = MainApplication.sc;

    public static void displayStatisticMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println(CYAN + "\n╔═════════════════════════════════════════════════════╗");
            System.out.println("║ " + YELLOW + "                   MENU THỐNG KÊ                    " + CYAN + "║");
            System.out.println("╠═════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Thống kê tổng số lượng khóa học và học viên      ║");
            System.out.println("║ 2. Thống kê học viên theo từng khóa học             ║");
            System.out.println("║ 3. Top 5 khóa học đông học viên nhất                ║");
            System.out.println("║ 4. Liệt kê khóa học có trên 10 học viên             ║");
            System.out.println("║ 5. Quay về menu chính                               ║");
            System.out.println("╚═════════════════════════════════════════════════════╝" + RESET);

            System.out.print(GREEN + "Chọn chức năng (1-5): " + RESET);
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(RED + "Lựa chọn phải là một số nguyên. Vui lòng chọn lại." + RESET);
                continue;
            }

            switch (choice) {
                case 1:
                    displayTotalCoursesAndStudents();
                    break;
                case 2:
                    displayStudentsPerCourse();
                    break;
                case 3:
                    displayTop5CoursesByStudents();
                    break;
                case 4:
                    displayCoursesWithMoreThan10Students();
                    break;
                case 5:
                    System.out.println(YELLOW + "Quay lại menu chính..." + RESET);
                    exit = true;
                    break;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
            }
        }
    }

    private static void displayTotalCoursesAndStudents() {
        Map<String, Integer> stats = statisticService.getTotalCoursesAndStudents();
        int totalCourses = stats.getOrDefault("totalCourses", 0);
        int totalStudents = stats.getOrDefault("totalStudents", 0);

        System.out.println(CYAN + "\nThống kê tổng số lượng khóa học và học viên:");
        System.out.println("┌───────────────────────────────────────────┐");
        System.out.printf("│ Tổng số khóa học:%-25d│%n", totalCourses);
        System.out.printf("│ Tổng số học viên:%-25d│%n", totalStudents);
        System.out.println("└───────────────────────────────────────────┘" + RESET);
    }

    private static void displayStudentsPerCourse() {
        List<Map<String, Object>> stats = statisticService.getStudentsPerCourse();
        if (stats.isEmpty()) {
            System.out.println(RED + "Không có dữ liệu để hiển thị." + RESET);
            return;
        }

        System.out.println(CYAN + "\nThống kê học viên theo từng khóa học:");
        System.out.println("┌────────────┬───────────────────────────────┬────────────────┐");
        System.out.printf("│ %-10s │ %-29s │ %-14s │%n", "Course ID", "Course Name", "Student Count");
        System.out.println("├────────────┼───────────────────────────────┼────────────────┤");
        for (Map<String, Object> row : stats) {
            System.out.printf("│ %-10d │ %-29s │ %-14d │%n",
                    row.get("courseId"),
                    row.get("courseName"),
                    row.get("studentCount"));
        }
        System.out.println("└────────────┴───────────────────────────────┴────────────────┘" + RESET);
    }

    private static void displayTop5CoursesByStudents() {
        List<Map<String, Object>> stats = statisticService.getTop5CoursesByStudents();
        if (stats.isEmpty()) {
            System.out.println(RED + "Không có dữ liệu để hiển thị." + RESET);
            return;
        }

        System.out.println(CYAN + "\nTop 5 khóa học đông học viên nhất:");
        System.out.println("┌────────────┬───────────────────────────────┬────────────────┐");
        System.out.printf("│ %-10s │ %-29s │ %-14s │%n", "Course ID", "Course Name", "Student Count");
        System.out.println("├────────────┼───────────────────────────────┼────────────────┤");
        for (Map<String, Object> row : stats) {
            System.out.printf("│ %-10d │ %-29s │ %-14d │%n",
                    row.get("courseId"),
                    row.get("courseName"),
                    row.get("studentCount"));
        }
        System.out.println("└────────────┴───────────────────────────────┴────────────────┘" + RESET);
    }

    private static void displayCoursesWithMoreThan10Students() {
        List<Map<String, Object>> stats = statisticService.getCoursesWithMoreThan10Students();
        if (stats.isEmpty()) {
            System.out.println(RED + "Không có khóa học nào có trên 10 học viên." + RESET);
            return;
        }

        System.out.println(CYAN + "\nDanh sách khóa học có trên 10 học viên:");
        System.out.println("┌────────────┬───────────────────────────────┬────────────────┐");
        System.out.printf("│ %-10s │ %-29s │ %-14s │%n", "Course ID", "Course Name", "Student Count");
        System.out.println("├────────────┼───────────────────────────────┼────────────────┤");
        for (Map<String, Object> row : stats) {
            System.out.printf("│ %-10d │ %-29s │ %-14d │%n",
                    row.get("courseId"),
                    row.get("courseName"),
                    row.get("studentCount"));
        }
        System.out.println("└────────────┴───────────────────────────────┴────────────────┘" + RESET);
    }
}