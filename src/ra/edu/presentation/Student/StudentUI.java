package ra.edu.presentation.Student;

import ra.edu.business.model.Course;
import ra.edu.business.model.Enrollment;
import ra.edu.business.model.Student;
import ra.edu.business.service.UCourse.UCourseService;
import ra.edu.business.service.UCourse.UCourseServiceImp;
import ra.edu.business.service.UEnrollment.UEnrollmentService;
import ra.edu.business.service.UEnrollment.UEnrollmentServiceImp;
import ra.edu.business.service.Student.StudentService;
import ra.edu.business.service.Student.StudentServiceImp;
import ra.edu.business.dao.UCourse.UCourseDaoImp;
import ra.edu.business.dao.UEnrollment.UEnrollmentDaoImp;
import ra.edu.business.dao.Student.StudentDaoImp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class StudentUI {
    private static Scanner scanner = new Scanner(System.in);
    private static final UCourseService courseService = new UCourseServiceImp(new UCourseDaoImp());
    private static final UEnrollmentService enrollmentService = new UEnrollmentServiceImp(new UEnrollmentDaoImp());
    private static final StudentService studentService = new StudentServiceImp(new StudentDaoImp());

    // Mã màu ANSI
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String PURPLE = "\u001B[35m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    public static void displayStudentMenu(Student student) {
        System.out.println(YELLOW + "\nĐăng nhập thành công! Chào mừng " + student.getName() + " (ID: " + student.getStudentId() + ")" + RESET);
        int choice;
        do {
            System.out.println(PURPLE + "\n╔═════════════════════════════════════════════╗");
            System.out.println("║           " + YELLOW + "    MENU HỌC VIÊN" + PURPLE + "                 ║");
            System.out.println("╠═════════════════════════════════════════════╣");
            System.out.println("║ " + CYAN + "1. Xem danh sách khóa học" + PURPLE + "                   ║");
            System.out.println("║ " + CYAN + "2. Đăng ký khóa học" + PURPLE + "                         ║");
            System.out.println("║ " + CYAN + "3. Xem khóa học đã đăng ký" + PURPLE + "                  ║");
            System.out.println("║ " + CYAN + "4. Hủy đăng ký (nếu chưa bắt đầu)" + PURPLE + "           ║");
            System.out.println("║ " + CYAN + "5. Đổi mật khẩu" + PURPLE + "                             ║");
            System.out.println("║ " + CYAN + "6. Đăng xuất" + PURPLE + "                                ║");
            System.out.println("╚═════════════════════════════════════════════╝" + RESET);
            System.out.print(GREEN + "→ Mời bạn chọn chức năng (1-6): " + RESET);

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        viewCourses(student);
                        break;
                    case 2:
                        registerCourse(student);
                        break;
                    case 3:
                        viewRegisteredCourses(student);
                        break;
                    case 4:
                        cancelRegistration(student);
                        break;
                    case 5:
                        changePassword(student);
                        break;
                    case 6:
                        System.out.println(YELLOW + "Đăng xuất thành công. Quay về menu chính..." + RESET);
                        break;
                    default:
                        System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 6!" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Vui lòng nhập số nguyên từ 1 đến 6!" + RESET);
                choice = -1;
            }
        } while (choice != 6);
    }

    private static void viewCourses(Student student) {
        int choice;
        do {
            System.out.println(PURPLE + "\n╔═══════════════════════════════╗");
            System.out.println("║ " + CYAN + "Xem danh sách khóa học" + PURPLE + "        ║");
            System.out.println("╠═══════════════════════════════╣");
            System.out.println("║ " + CYAN + "1. Xem tất cả khóa học" + PURPLE + "        ║");
            System.out.println("║ " + CYAN + "2. Tìm kiếm khóa học theo tên" + PURPLE + " ║");
            System.out.println("║ " + CYAN + "3. Quay lại" + PURPLE + "                   ║");
            System.out.println("╚═══════════════════════════════╝" + RESET);
            System.out.print(GREEN + "→ Mời bạn chọn (1-3): " + RESET);

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        displayCoursesWithPagination();
                        break;
                    case 2:
                        searchCoursesByName();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println(RED + "Lựa chọn không hợp lệ!" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Vui lòng nhập số nguyên từ 1 đến 3!" + RESET);
            }
        } while (true);
    }

    private static void displayCoursesWithPagination() {
        int page = 1;
        int totalPages = courseService.countCourses();
        while (true) {
            List<Course> courses = courseService.getCoursesByPage(page);
            displayCourseList(courses, page, totalPages, "Danh sách khóa học");
            page = handlePaginationInput(page, totalPages);
            if (page == -1) break;
        }
    }

    private static void searchCoursesByName() {
        System.out.println(CYAN + "\nTìm kiếm khóa học theo tên:" + RESET);
        System.out.print("Nhập tên khóa học: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println(RED + "Tên khóa học không được để trống!" + RESET);
            return;
        }

        int page = 1;
        int totalPages = courseService.countCoursesByName(name);
        while (true) {
            List<Course> courses = courseService.searchByName(name, page);
            displayCourseList(courses, page, totalPages, "Kết quả tìm kiếm cho \"" + name + "\"");
            page = handlePaginationInput(page, totalPages);
            if (page == -1) break;
        }
    }

    private static void displayCourseList(List<Course> courses, int page, int totalPages, String title) {
        if (courses.isEmpty()) {
            System.out.println(YELLOW + "Không có khóa học nào ở trang " + page + "." + RESET);
        } else {
            System.out.println(CYAN + "\n" + title + " (Trang " + page + "/" + totalPages + "):" + RESET);
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.printf("%-5s %-30s %-10s %-20s %-12s\n",
                    "ID", "Tên khóa học", "Thời lượng", "Giảng viên", "Ngày tạo");
            System.out.println("-----------------------------------------------------------------------------------");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Course course : courses) {
                System.out.printf("%-5d %-30s %-10d %-20s %-12s\n",
                        course.getCourseId(),
                        course.getName(),
                        course.getDuration(),
                        course.getInstructor(),
                        course.getCreateAt().format(dtf));
            }
            System.out.println("-----------------------------------------------------------------------------------");
        }
        System.out.print(YELLOW + "Trang: ");
        for (int i = 1; i <= totalPages; i++) {
            if (i == page) {
                System.out.print("[" + i + "] ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println(RESET);
    }

    private static void registerCourse(Student student) {
        System.out.println(CYAN + "\nĐăng ký khóa học:" + RESET);
        System.out.print("Nhập ID khóa học: ");
        int courseId;
        try {
            courseId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(RED + "ID khóa học phải là số nguyên!" + RESET);
            return;
        }

        Course course = courseService.findCourseById(courseId);
        if (course == null) {
            System.out.println(RED + "Không tìm thấy khóa học với ID " + courseId + "!" + RESET);
            return;
        }

        System.out.println(CYAN + "\nThông tin khóa học:" + RESET);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("ID: %d, Tên: %s, Thời lượng: %d ngày, Giảng viên: %s, Ngày tạo: %s\n",
                course.getCourseId(), course.getName(), course.getDuration(), course.getInstructor(), course.getCreateAt().format(dtf));
        System.out.print(YELLOW + "Bạn có chắc chắn muốn đăng ký khóa học này? (1 để xác nhận, 0 để hủy): " + RESET);
        String confirm;
        try {
            confirm = scanner.nextLine().trim();
            if (!confirm.equals("1") && !confirm.equals("0")) {
                System.out.println(RED + "Vui lòng nhập 1 hoặc 0!" + RESET);
                return;
            }
        } catch (Exception e) {
            System.out.println(RED + "Lựa chọn không hợp lệ!" + RESET);
            return;
        }

        if (confirm.equals("1")) {
            if (enrollmentService.registerCourse(student.getStudentId(), courseId)) {
                System.out.println(GREEN + "Đăng ký khóa học thành công! Đơn đăng ký đang chờ xử lý." + RESET);
            } else {
                Connection conn = null;
                CallableStatement callSt = null;
                try {
                    conn = ra.edu.business.config.ConnectionDB.openConnection();
                    callSt = conn.prepareCall("{CALL register_course(?, ?, ?, ?)}");
                    callSt.setInt(1, student.getStudentId());
                    callSt.setInt(2, courseId);
                    callSt.registerOutParameter(3, Types.BIT);
                    callSt.registerOutParameter(4, Types.VARCHAR);
                    callSt.execute();
                    boolean success = callSt.getBoolean(3);
                    String message = callSt.getString(4);
                    if (!success) {
                        System.out.println(RED + message + RESET);
                    }
                } catch (SQLException e) {
                    System.out.println(RED + "Đăng ký khóa học thất bại do lỗi hệ thống!" + RESET);
                    e.printStackTrace();
                } finally {
                    ra.edu.business.config.ConnectionDB.closeConnection(conn, callSt, null);
                }
            }
        } else {
            System.out.println(YELLOW + "Hủy đăng ký khóa học." + RESET);
        }
    }

    private static void viewRegisteredCourses(Student student) {
        int page = 1;
        int totalPages = enrollmentService.countEnrollmentsByStudent(student.getStudentId());
        while (true) {
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(student.getStudentId(), page);
            displayEnrollmentList(enrollments, page, totalPages, "Danh sách khóa học đã đăng ký");
            page = handlePaginationInput(page, totalPages);
            if (page == -1) break;
        }
    }

    private static void displayEnrollmentList(List<Enrollment> enrollments, int page, int totalPages, String title) {
        if (enrollments.isEmpty()) {
            System.out.println(YELLOW + "Bạn chưa đăng ký khóa học nào ở trang " + page + "." + RESET);
        } else {
            System.out.println(CYAN + "\n" + title + " (Trang " + page + "/" + totalPages + "):" + RESET);
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.printf("%-5s %-30s %-20s %-15s\n",
                    "ID Đơn", "Tên khóa học", "Ngày đăng ký", "Trạng thái");
            System.out.println("-----------------------------------------------------------------------------------");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            for (Enrollment enrollment : enrollments) {
                System.out.printf("%-5d %-30s %-20s %-15s\n",
                        enrollment.getId(),
                        enrollment.getCourseName(),
                        enrollment.getRegisteredAt().format(dtf),
                        enrollment.getStatus());
            }
            System.out.println("-----------------------------------------------------------------------------------");
        }
        System.out.print(YELLOW + "Trang: ");
        for (int i = 1; i <= totalPages; i++) {
            if (i == page) {
                System.out.print("[" + i + "] ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println(RESET);
    }

    private static void cancelRegistration(Student student) {
        System.out.println(CYAN + "\nHủy đăng ký khóa học:" + RESET);
        List<Enrollment> waitingEnrollments = enrollmentService.getWaitingEnrollmentsByStudent(student.getStudentId());
        if (waitingEnrollments.isEmpty()) {
            System.out.println(YELLOW + "Bạn không có đơn đăng ký nào ở trạng thái chờ để hủy." + RESET);
            return;
        }

        displayEnrollmentList(waitingEnrollments, 1, 1, "Danh sách đơn đăng ký đang chờ");
        System.out.print("Nhập ID đơn đăng ký để hủy: ");
        int enrollmentId;
        try {
            enrollmentId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(RED + "ID đơn đăng ký phải là số nguyên!" + RESET);
            return;
        }

        Enrollment enrollment = waitingEnrollments.stream()
                .filter(e -> e.getId() == enrollmentId)
                .findFirst()
                .orElse(null);
        if (enrollment == null) {
            System.out.println(RED + "Không tìm thấy đơn đăng ký với ID " + enrollmentId + " ở trạng thái chờ!" + RESET);
            return;
        }

        System.out.print(YELLOW + "Bạn có chắc chắn muốn hủy đơn đăng ký này? (1 để xác nhận, 0 để hủy): " + RESET);
        String confirm;
        try {
            confirm = scanner.nextLine().trim();
            if (!confirm.equals("1") && !confirm.equals("0")) {
                System.out.println(RED + "Vui lòng nhập 1 hoặc 0!" + RESET);
                return;
            }
        } catch (Exception e) {
            System.out.println(RED + "Lựa chọn không hợp lệ!" + RESET);
            return;
        }

        if (confirm.equals("1")) {
            if (enrollmentService.cancelEnrollment(enrollmentId, student.getStudentId())) {
                System.out.println(GREEN + "Hủy đăng ký khóa học thành công!" + RESET);
            } else {
                Connection conn = null;
                CallableStatement callSt = null;
                try {
                    conn = ra.edu.business.config.ConnectionDB.openConnection();
                    callSt = conn.prepareCall("{CALL cancel_enrollment(?, ?, ?, ?)}");
                    callSt.setInt(1, enrollmentId);
                    callSt.setInt(2, student.getStudentId());
                    callSt.registerOutParameter(3, Types.BIT);
                    callSt.registerOutParameter(4, Types.VARCHAR);
                    callSt.execute();
                    boolean success = callSt.getBoolean(3);
                    String message = callSt.getString(4);
                    if (!success) {
                        System.out.println(RED + message + RESET);
                    }
                } catch (SQLException e) {
                    System.out.println(RED + "Hủy đăng ký thất bại do lỗi hệ thống!" + RESET);
                    e.printStackTrace();
                } finally {
                    ra.edu.business.config.ConnectionDB.closeConnection(conn, callSt, null);
                }
            }
        } else {
            System.out.println(YELLOW + "Hủy thao tác hủy đăng ký." + RESET);
        }
    }

    private static void changePassword(Student student) {
        System.out.println(CYAN + "\nĐổi mật khẩu:" + RESET);
        System.out.print("Nhập mật khẩu hiện tại: ");
        String currentPassword = scanner.nextLine().trim();
        System.out.print("Nhập số điện thoại: ");
        String phone = scanner.nextLine().trim();

        // Kiểm tra mật khẩu và số điện thoại
        if (!currentPassword.equals(student.getPassword()) ||
                (student.getPhone() == null && !phone.isEmpty()) ||
                (student.getPhone() != null && !student.getPhone().equals(phone))) {
            System.out.println(RED + "Mật khẩu hoặc số điện thoại không đúng! Vui lòng thử lại." + RESET);
            return;
        }

        System.out.print("Nhập mật khẩu mới (ít nhất 6 ký tự): ");
        String newPassword = scanner.nextLine().trim();
        if (newPassword.isEmpty()) {
            System.out.println(RED + "Mật khẩu mới không được để trống!" + RESET);
            return;
        }
        if (newPassword.length() < 6) {
            System.out.println(RED + "Mật khẩu mới phải có ít nhất 6 ký tự!" + RESET);
            return;
        }

        if (studentService.updatePassword(student.getStudentId(), newPassword)) {
            System.out.println(GREEN + "Đổi mật khẩu thành công!" + RESET);
            student.setPassword(newPassword);
        } else {
            System.out.println(RED + "Đổi mật khẩu thất bại do lỗi hệ thống!" + RESET);
        }
    }

    private static int handlePaginationInput(int currentPage, int totalPages) {
        System.out.println(GREEN + "Nhấn 'n' để xem trang tiếp theo, 'p' để quay lại, 'q' để thoát," + RESET);
        System.out.print(GREEN + "hoặc nhập số trang (1-" + totalPages + "): " + RESET);
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("n") && currentPage < totalPages) {
            return currentPage + 1;
        } else if (input.equals("p") && currentPage > 1) {
            return currentPage - 1;
        } else if (input.equals("q")) {
            return -1;
        } else {
            try {
                int page = Integer.parseInt(input);
                if (page >= 1 && page <= totalPages) {
                    return page;
                } else {
                    System.out.println(RED + "Trang phải từ 1 đến " + totalPages + "!" + RESET);
                    return currentPage;
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Lựa chọn không hợp lệ!" + RESET);
                return currentPage;
            }
        }
    }
}