package ra.edu.presentation.Course;

import ra.edu.business.model.Course;
import ra.edu.business.service.Course.CourseService;
import ra.edu.business.service.Course.CourseServiceImp;
import ra.edu.business.dao.Course.CourseDaoImp;
import ra.edu.validate.Course.CourseValidator;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class CourseUI {
    private static Scanner scanner = new Scanner(System.in);
    private static final CourseService courseService = new CourseServiceImp(new CourseDaoImp());
    private static final CourseValidator courseValidator = new CourseValidator(courseService);

    // Mã màu ANSI
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String PURPLE = "\u001B[35m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    public static void displayCourseUI() {
        int choice;
        do {
            System.out.println(PURPLE + "\n╔═══════════════════════════════════════════════════╗");
            System.out.println("║              " + YELLOW + "QUẢN LÝ KHÓA HỌC" + PURPLE + "                     ║");
            System.out.println("╠═══════════════════════════════════════════════════╣");
            System.out.println("║ " + CYAN + "1. Hiển thị danh sách khóa học" + PURPLE + "                    ║");
            System.out.println("║ " + CYAN + "2. Thêm mới khóa học" + PURPLE + "                              ║");
            System.out.println("║ " + CYAN + "3. Chỉnh sửa thông tin khóa học" + PURPLE + "                   ║");
            System.out.println("║ " + CYAN + "4. Xóa khóa học (xác nhận trước khi xóa)" + PURPLE + "          ║");
            System.out.println("║ " + CYAN + "5. Tìm kiếm theo tên (tương đối)" + PURPLE + "                  ║");
            System.out.println("║ " + CYAN + "6. Sắp xếp theo tên hoặc id (tăng/giảm dần)" + PURPLE + "       ║");
            System.out.println("║ " + CYAN + "7. Quay về menu chính" + PURPLE + "                             ║");
            System.out.println("╚═══════════════════════════════════════════════════╝" + RESET);
            System.out.print(GREEN + "→ Mời bạn chọn chức năng (1-7): " + RESET);

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        displayCoursesWithPagination();
                        break;
                    case 2:
                        addNewCourse();
                        break;
                    case 3:
                        editCourse();
                        break;
                    case 4:
                        deleteCourse();
                        break;
                    case 5:
                        searchCoursesByName();
                        break;
                    case 6:
                        sortCourses();
                        break;
                    case 7:
                        System.out.println(YELLOW + "Quay về menu chính..." + RESET);
                        break;
                    default:
                        System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 7!" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Vui lòng nhập số nguyên từ 1 đến 7!" + RESET);
                choice = -1;
            }
        } while (choice != 7);
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

    private static void addNewCourse() {
        System.out.println(CYAN + "\nThêm mới khóa học:" + RESET);
        String name = courseValidator.validateCourseName(scanner);
        int duration = courseValidator.validateDuration(scanner);
        String instructor = courseValidator.validateInstructor(scanner);
        LocalDate createAt = LocalDate.now();

        Course course = new Course(0, name, duration, instructor, createAt);
        if (courseService.addCourse(course)) {
            System.out.println(GREEN + "Thêm khóa học thành công!" + RESET);
        } else {
            System.out.println(RED + "Thêm khóa học thất bại!" + RESET);
        }
    }

    private static void editCourse() {
        System.out.println(CYAN + "\nChỉnh sửa thông tin khóa học:" + RESET);
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
        System.out.println("┌────────┬─────────────────────┬────────────┬─────────────────────┬──────────────┐");
        System.out.printf("│ %-6s │ %-19s │ %-10s │ %-19s │ %-12s │\n",
                "ID", "Tên khóa học", "Thời lượng", "Giảng viên", "Ngày tạo");
        System.out.println("├────────┼─────────────────────┼────────────┼─────────────────────┼──────────────┤");
        System.out.printf("│ %-6d │ %-19s │ %-10d │ %-19s │ %-12s │\n",
                course.getCourseId(), course.getName(), course.getDuration(),
                course.getInstructor(), course.getCreateAt().format(dtf));
        System.out.println("└────────┴─────────────────────┴────────────┴─────────────────────┴──────────────┘");

        int choice;
        do {
            System.out.println(PURPLE + "\n╔══════════════════════════════════════╗");
            System.out.println("║ " + CYAN + "    Chọn thuộc tính để chỉnh sửa" + PURPLE + "     ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ " + CYAN + "1. Tên khóa học" + PURPLE + "                      ║");
            System.out.println("║ " + CYAN + "2. Thời lượng" + PURPLE + "                        ║");
            System.out.println("║ " + CYAN + "3. Giảng viên" + PURPLE + "                        ║");
            System.out.println("║ " + CYAN + "4. Hoàn tất chỉnh sửa" + PURPLE + "                ║");
            System.out.println("╚══════════════════════════════════════╝" + RESET);
            System.out.print(GREEN + "→ Mời bạn chọn (1-4): " + RESET);

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        course.setName(courseValidator.validateCourseNameForUpdate(scanner, course.getCourseId()));
                        break;
                    case 2:
                        course.setDuration(courseValidator.validateDuration(scanner));
                        break;
                    case 3:
                        course.setInstructor(courseValidator.validateInstructor(scanner));
                        break;
                    case 4:
                        if (courseService.updateCourse(course)) {
                            System.out.println(GREEN + "Cập nhật khóa học thành công!" + RESET);
                        } else {
                            System.out.println(RED + "Cập nhật khóa học thất bại!" + RESET);
                        }
                        return;
                    default:
                        System.out.println(RED + "Lựa chọn không hợp lệ!" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Vui lòng nhập số nguyên từ 1 đến 4!" + RESET);
            }
        } while (true);
    }

    private static void deleteCourse() {
        System.out.println(CYAN + "\nXóa khóa học:" + RESET);
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
        System.out.println("┌────────┬─────────────────────┬────────────┬─────────────────────┬──────────────┐");
        System.out.printf("│ %-6s │ %-19s │ %-10s │ %-19s │ %-12s │\n",
                "ID", "Tên khóa học", "Thời lượng", "Giảng viên", "Ngày tạo");
        System.out.println("├────────┼─────────────────────┼────────────┼─────────────────────┼──────────────┤");
        System.out.printf("│ %-6d │ %-19s │ %-10d │ %-19s │ %-12s │\n",
                course.getCourseId(), course.getName(), course.getDuration(),
                course.getInstructor(), course.getCreateAt().format(dtf));
        System.out.println("└────────┴─────────────────────┴────────────┴─────────────────────┴──────────────┘");

        System.out.print(YELLOW + "Bạn có chắc chắn muốn xóa khóa học này? (1 để xác nhận, 0 để hủy): " + RESET);
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
            if (courseService.deleteCourseById(courseId)) {
                System.out.println(GREEN + "Xóa khóa học thành công!" + RESET);
            } else {
                Connection conn = null;
                CallableStatement callSt = null;
                try {
                    conn = ra.edu.business.config.ConnectionDB.openConnection();
                    callSt = conn.prepareCall("{CALL delete_course(?, ?, ?)}");
                    callSt.setInt(1, courseId);
                    callSt.registerOutParameter(2, java.sql.Types.BIT);
                    callSt.registerOutParameter(3, java.sql.Types.VARCHAR);
                    callSt.execute();
                    boolean success = callSt.getBoolean(2);
                    String message = callSt.getString(3);
                    if (!success) {
                        System.out.println(RED + message + RESET);
                    }
                } catch (SQLException e) {
                    System.out.println(RED + "Xóa khóa học thất bại do lỗi hệ thống!" + RESET);
                    e.printStackTrace();
                } finally {
                    ra.edu.business.config.ConnectionDB.closeConnection(conn, callSt, null);
                }
            }
        } else {
            System.out.println(YELLOW + "Hủy xóa khóa học." + RESET);
        }
    }

    private static void searchCoursesByName() {
        System.out.println(CYAN + "\nTìm kiếm khóa học theo tên:" + RESET);
        System.out.print("Nhập tên khóa học (hoặc một phần): ");
        String searchName = scanner.nextLine().trim();
        if (searchName.isEmpty()) {
            System.out.println(RED + "Tên tìm kiếm không được để trống!" + RESET);
            return;
        }

        int page = 1;
        int totalPages = courseService.countCoursesByName(searchName);
        while (true) {
            List<Course> courses = courseService.searchByName(searchName, page);
            displayCourseList(courses, page, totalPages, "Kết quả tìm kiếm cho \"" + searchName + "\"");
            page = handlePaginationInput(page, totalPages);
            if (page == -1) break;
        }
    }

    private static void sortCourses() {
        System.out.println(CYAN + "\nSắp xếp danh sách khóa học:" + RESET);
        int fieldChoice;
        String field = "";
        do {
            System.out.println(PURPLE + "\n╔═══════════════════════════════╗");
            System.out.println("║ " + CYAN + "Chọn tiêu chí sắp xếp" + PURPLE + "       ║");
            System.out.println("╠═══════════════════════════════╣");
            System.out.println("║ " + CYAN + "1. Theo tên khóa học" + PURPLE + "         ║");
            System.out.println("║ " + CYAN + "2. Theo ID khóa học" + PURPLE + "          ║");
            System.out.println("║ " + CYAN + "3. Quay lại" + PURPLE + "                  ║");
            System.out.println("╚═══════════════════════════════╝" + RESET);
            System.out.print(GREEN + "→ Mời bạn chọn (1-3): " + RESET);

            try {
                fieldChoice = Integer.parseInt(scanner.nextLine());
                switch (fieldChoice) {
                    case 1:
                        field = "name";
                        break;
                    case 2:
                        field = "course_id";
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println(RED + "Lựa chọn không hợp lệ!" + RESET);
                        continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(RED + "Vui lòng nhập số nguyên từ 1 đến 3!" + RESET);
            }
        } while (true);

        int orderChoice;
        String order = "";
        do {
            System.out.println(PURPLE + "\n╔═══════════════════════════════╗");
            System.out.println("║ " + CYAN + "Chọn thứ tự sắp xếp" + PURPLE + "         ║");
            System.out.println("╠═══════════════════════════════╣");
            System.out.println("║ " + CYAN + "1. Tăng dần" + PURPLE + "                  ║");
            System.out.println("║ " + CYAN + "2. Giảm dần" + PURPLE + "                  ║");
            System.out.println("║ " + CYAN + "3. Quay lại" + PURPLE + "                  ║");
            System.out.println("╚═══════════════════════════════╝" + RESET);
            System.out.print(GREEN + "→ Mời bạn chọn (1-3): " + RESET);

            try {
                orderChoice = Integer.parseInt(scanner.nextLine());
                switch (orderChoice) {
                    case 1:
                        order = "ASC";
                        break;
                    case 2:
                        order = "DESC";
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println(RED + "Lựa chọn không hợp lệ!" + RESET);
                        continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(RED + "Vui lòng nhập số nguyên từ 1 đến 3!" + RESET);
            }
        } while (true);

        int page = 1;
        int totalPages = courseService.countCourses();
        while (true) {
            List<Course> courses = courseService.sortByField(field, order, page);
            String sortDesc = "Sắp xếp theo " + (field.equals("name") ? "tên" : "ID") + " (" + (order.equals("ASC") ? "tăng dần" : "giảm dần") + ")";
            displayCourseList(courses, page, totalPages, sortDesc);
            page = handlePaginationInput(page, totalPages);
            if (page == -1) break;
        }
    }

    private static void displayCourseList(List<Course> courses, int page, int totalPages, String title) {
        if (courses.isEmpty()) {
            System.out.println(YELLOW + "Không có khóa học nào ở trang " + page + "." + RESET);
        } else {
            System.out.println(CYAN + "\n" + title + " (Trang " + page + "/" + totalPages + "):" + RESET);
            System.out.println("┌────────┬─────────────────────┬────────────┬─────────────────────┬──────────────┐");
            System.out.printf("│ %-6s │ %-19s │ %-10s │ %-19s │ %-12s │\n",
                    "ID", "Tên khóa học", "Thời lượng", "Giảng viên", "Ngày tạo");
            System.out.println("├────────┼─────────────────────┼────────────┼─────────────────────┼──────────────┤");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Course course : courses) {
                System.out.printf("│ %-6d │ %-19s │ %-10d │ %-19s │ %-12s │\n",
                        course.getCourseId(),
                        course.getName(),
                        course.getDuration(),
                        course.getInstructor(),
                        course.getCreateAt().format(dtf));
            }
            System.out.println("└────────┴─────────────────────┴────────────┴─────────────────────┴──────────────┘");

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