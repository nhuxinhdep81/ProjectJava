package ra.edu.presentation.Enrollment;

import ra.edu.MainApplication;
import ra.edu.business.dao.Enrollment.EnrollmentDaoImp;
import ra.edu.business.model.Enrollment;
import ra.edu.business.service.Enrollment.EnrollmentService;
import ra.edu.business.service.Enrollment.EnrollmentServiceImp;

import java.util.List;
import java.util.Scanner;

public class EnrollmentUI {
    // Mã màu ANSI
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    private static final EnrollmentService enrollmentService = new EnrollmentServiceImp(new EnrollmentDaoImp());
    private static final Scanner sc = MainApplication.sc;

    public static void displayEnrollmentMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println(CYAN + "\n╔═══════════════════════════════════════════════════════════════════╗");
            System.out.println("║" + String.format("%18s", "") + YELLOW + "MENU QUẢN LÝ ĐĂNG KÝ KHÓA HỌC" + CYAN + String.format("%20s", "") + "║");
            System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Hiển thị học viên theo từng khóa học (phân trang)              ║");
            System.out.println("║ 2. Duyệt sinh viên đăng ký khóa học                               ║");
            System.out.println("║ 3. Xóa đơn đăng ký đã bị từ chối hoặc hủy                         ║");
            System.out.println("║ 4. Quay về menu chính                                             ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════╝" + RESET);


            System.out.print(GREEN + "Chọn chức năng (1-4): " + RESET);
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(RED + "Lựa chọn phải là một số nguyên. Vui lòng chọn lại." + RESET);
                continue;
            }

            switch (choice) {
                case 1:
                    displayConfirmedEnrollmentsByCourse();
                    break;
                case 2:
                    approveEnrollment();
                    break;
                case 3:
                    deleteEnrollment();
                    break;
                case 4:
                    System.out.println(YELLOW + "Quay lại menu chính..." + RESET);
                    exit = true;
                    break;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
            }
        }
    }

    private static void displayConfirmedEnrollmentsByCourse() {
        System.out.print(GREEN + "Nhập ID khóa học: " + RESET);
        int courseId;
        try {
            courseId = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(RED + "ID khóa học phải là một số nguyên. Vui lòng thử lại." + RESET);
            return;
        }

        int totalPages = enrollmentService.countConfirmedEnrollmentsByCourse(courseId);
        if (totalPages == 0) {
            System.out.println(RED + "Không có học viên nào đã xác nhận đăng ký khóa học này." + RESET);
            return;
        }

        int page = 1;
        boolean back = false;
        while (!back) {
            List<Enrollment> enrollments = enrollmentService.getConfirmedEnrollmentsByCourse(courseId, page);
            if (enrollments.isEmpty()) {
                System.out.println(RED + "Không có dữ liệu để hiển thị." + RESET);
                return;
            }

            System.out.println(CYAN + "\nDanh sách học viên đăng ký khóa học (ID: " + courseId + ") - Trang " + page + "/" + totalPages);
            System.out.println("┌──────┬────────────┬────────────┬──────────────────────────┬────────────┐");
            System.out.printf("│ %-4s │ %-10s │ %-10s │ %-24s │ %-10s │%n", "ID", "Student ID", "Course ID", "Registered At", "Status");
            System.out.println("├──────┼────────────┼────────────┼──────────────────────────┼────────────┤");
            for (Enrollment enrollment : enrollments) {
                System.out.printf("│ %-4d │ %-10d │ %-10d │ %-24s │ %-10s │%n",
                        enrollment.getId(),
                        enrollment.getStudentId(),
                        enrollment.getCourseId(),
                        enrollment.getRegisteredAt(),
                        enrollment.getStatus());
            }
            System.out.println("└──────┴────────────┴────────────┴──────────────────────────┴────────────┘" + RESET);

            System.out.print(GREEN + "Nhấn 'p' để xem trang trước, 'n' để xem trang sau, hoặc 'q' để quay lại: " + RESET);
            String action = sc.nextLine().trim().toLowerCase();
            if (action.equals("p")) {
                if (page > 1) page--;
            } else if (action.equals("n")) {
                if (page < totalPages) page++;
            } else if (action.equals("q")) {
                back = true;
            } else {
                System.out.println(RED + "Lựa chọn không hợp lệ." + RESET);
            }
        }
    }

    private static void displayDeniedCancelEnrollmentsByCourse(int courseId, int page, int totalPages) {
        List<Enrollment> enrollments = enrollmentService.getDeniedCancelEnrollmentsByCourse(courseId, page);
        if (enrollments.isEmpty()) {
            System.out.println(RED + "Không có dữ liệu để hiển thị." + RESET);
            return;
        }

        System.out.println(CYAN + "\nDanh sách học viên đã bị từ chối hoặc hủy đăng ký khóa học (ID: " + courseId + ") - Trang " + page + "/" + totalPages);
        System.out.println("┌──────┬────────────┬────────────┬──────────────────────────┬────────────┐");
        System.out.printf("│ %-4s │ %-10s │ %-10s │ %-24s │ %-10s │%n", "ID", "Student ID", "Course ID", "Registered At", "Status");
        System.out.println("├──────┼────────────┼────────────┼──────────────────────────┼────────────┤");
        for (Enrollment enrollment : enrollments) {
            System.out.printf("│ %-4d │ %-10d │ %-10d │ %-24s │ %-10s │%n",
                    enrollment.getId(),
                    enrollment.getStudentId(),
                    enrollment.getCourseId(),
                    enrollment.getRegisteredAt(),
                    enrollment.getStatus());
        }
        System.out.println("└──────┴────────────┴────────────┴──────────────────────────┴────────────┘" + RESET);
    }

    private static void approveEnrollment() {
        List<Enrollment> pendingEnrollments = enrollmentService.getPendingEnrollments();
        if (pendingEnrollments.isEmpty()) {
            System.out.println(RED + "Không có đơn đăng ký nào đang chờ duyệt." + RESET);
            return;
        }

        System.out.println(CYAN + "\nDanh sách đơn đăng ký chờ duyệt");
        System.out.println("┌──────┬────────────┬────────────┬──────────────────────────┬────────────┐");
        System.out.printf("│ %-4s │ %-10s │ %-10s │ %-24s │ %-10s │%n", "ID", "Student ID", "Course ID", "Registered At", "Status");
        System.out.println("├──────┼────────────┼────────────┼──────────────────────────┼────────────┤");
        for (Enrollment enrollment : pendingEnrollments) {
            System.out.printf("│ %-4d │ %-10d │ %-10d │ %-24s │ %-10s │%n",
                    enrollment.getId(),
                    enrollment.getStudentId(),
                    enrollment.getCourseId(),
                    enrollment.getRegisteredAt(),
                    enrollment.getStatus());
        }
        System.out.println("└──────┴────────────┴────────────┴──────────────────────────┴────────────┘" + RESET);

        System.out.print(GREEN + "Nhập ID đơn đăng ký cần xử lý: " + RESET);
        int enrollmentId;
        try {
            enrollmentId = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(RED + "ID đơn đăng ký phải là một số nguyên. Vui lòng thử lại." + RESET);
            return;
        }

        System.out.println(YELLOW + "Chọn hành động:");
        System.out.println("1. Confirm (Duyệt đơn)");
        System.out.println("2. Denied (Từ chối đơn)");
        System.out.print("Nhập lựa chọn (1 hoặc 2): " + RESET);
        String choice = sc.nextLine().trim();

        String action;
        if (choice.equals("1")) {
            action = "approve";
        } else if (choice.equals("2")) {
            action = "deny";
        } else {
            System.out.println(RED + "Lựa chọn không hợp lệ. Hủy thao tác." + RESET);
            return;
        }

        System.out.print(YELLOW + "Bạn có chắc chắn muốn " + (action.equals("approve") ? "duyệt" : "từ chối") + " đơn đăng ký này? (y/n): " + RESET);
        String confirm = sc.nextLine().trim().toLowerCase();
        if (confirm.equals("y")) {
            boolean success = enrollmentService.approveEnrollment(enrollmentId, action);
            if (success) {
                System.out.println(GREEN + (action.equals("approve") ? "Duyệt đơn đăng ký thành công." : "Từ chối đơn đăng ký thành công.") + RESET);
            }
        } else {
            System.out.println(YELLOW + "Hủy thao tác xử lý đơn đăng ký." + RESET);
        }
    }

    private static void deleteEnrollment() {
        System.out.print(GREEN + "Nhập ID khóa học: " + RESET);
        int courseId;
        try {
            courseId = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(RED + "ID khóa học phải là một số nguyên. Vui lòng thử lại." + RESET);
            return;
        }

        int totalPages = enrollmentService.countDeniedCancelEnrollmentsByCourse(courseId);
        if (totalPages == 0) {
            System.out.println(RED + "Không có học viên nào đã bị từ chối hoặc hủy đăng ký khóa học này." + RESET);
            return;
        }

        int page = 1;
        boolean back = false;
        while (!back) {
            displayDeniedCancelEnrollmentsByCourse(courseId, page, totalPages);

            System.out.print(GREEN + "Nhấn 'p' để xem trang trước, 'n' để xem trang sau, 'd' để xóa đơn, hoặc 'q' để quay lại: " + RESET);
            String action = sc.nextLine().trim().toLowerCase();
            if (action.equals("p")) {
                if (page > 1) page--;
            } else if (action.equals("n")) {
                if (page < totalPages) page++;
            } else if (action.equals("d")) {
                System.out.print(GREEN + "Nhập ID sinh viên cần xóa: " + RESET);
                int studentId;
                try {
                    studentId = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println(RED + "ID sinh viên phải là một số nguyên. Vui lòng thử lại." + RESET);
                    continue;
                }

                List<Enrollment> enrollments = enrollmentService.getDeniedCancelEnrollmentsByCourse(courseId, page);
                Enrollment target = null;
                for (Enrollment enrollment : enrollments) {
                    if (enrollment.getStudentId() == studentId) {
                        target = enrollment;
                        break;
                    }
                }

                if (target == null) {
                    System.out.println(RED + "Không tìm thấy sinh viên với ID này trong danh sách đã bị từ chối hoặc hủy." + RESET);
                    continue;
                }

                System.out.print(YELLOW + "Bạn có chắc chắn muốn xóa đơn đăng ký này? (y/n): " + RESET);
                String confirm = sc.nextLine().trim().toLowerCase();
                if (confirm.equals("y")) {
                    boolean success = enrollmentService.deleteEnrollment(courseId, studentId);
                    if (success) {
                        System.out.println(GREEN + "Xóa đơn đăng ký thành công." + RESET);
                        // Cập nhật lại totalPages sau khi xóa
                        totalPages = enrollmentService.countDeniedCancelEnrollmentsByCourse(courseId);
                        if (page > totalPages && page > 1) page--;
                        if (totalPages == 0) {
                            System.out.println(RED + "Không còn đơn đăng ký nào để hiển thị." + RESET);
                            return;
                        }
                    }
                } else {
                    System.out.println(YELLOW + "Hủy thao tác xóa đơn đăng ký." + RESET);
                }
            } else if (action.equals("q")) {
                back = true;
            } else {
                System.out.println(RED + "Lựa chọn không hợp lệ." + RESET);
            }
        }
    }
}