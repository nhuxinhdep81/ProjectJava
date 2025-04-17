package ra.edu.presentation.Course;

import ra.edu.business.model.Course;
import ra.edu.business.service.Course.CourseService;
import ra.edu.business.service.Course.CourseServiceImp;
import ra.edu.business.dao.Course.CourseDaoImp;
import ra.edu.validate.Course.CourseValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class CourseUI {
    private static Scanner scanner = new Scanner(System.in);
    private static final CourseService courseService = new CourseServiceImp(new CourseDaoImp());

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
            System.out.println(PURPLE + "\n╔═════════════════════════════════════════════╗");
            System.out.println("║           " + YELLOW + "   QUẢN LÝ KHÓA HỌC" + PURPLE + "               ║");
            System.out.println("╠═════════════════════════════════════════════╣");
            System.out.println("║ " + CYAN + "1. Hiển thị danh sách khóa học" + PURPLE + "              ║");
            System.out.println("║ " + CYAN + "2. Thêm mới khóa học" + PURPLE + "                        ║");
            System.out.println("║ " + CYAN + "3. Chỉnh sửa thông tin khóa học" + PURPLE + "             ║");
            System.out.println("║ " + CYAN + "4. Xóa khóa học (xác nhận trước khi xóa)" + PURPLE + "    ║");
            System.out.println("║ " + CYAN + "5. Tìm kiếm theo tên (tương đối)" + PURPLE + "            ║");
            System.out.println("║ " + CYAN + "6. Sắp xếp theo tên hoặc id (tăng/giảm dần)" + PURPLE + " ║");
            System.out.println("║ " + CYAN + "7. Quay về menu chính" + PURPLE + "                       ║");
            System.out.println("╚═════════════════════════════════════════════╝" + RESET);
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
                    case 5:
                    case 6:
                        System.out.println(YELLOW + "Chức năng chưa được triển khai." + RESET);
                        break;
                    case 7:
                        System.out.println(YELLOW + " Quay về menu chính..." + RESET);
                        break;
                    default:
                        System.out.println(RED + " Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 7!" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + " Vui lòng nhập số nguyên từ 1 đến 7!" + RESET);
                choice = -1;
            }
        } while (choice != 7);
    }

    private static void displayCoursesWithPagination() {
        int page = 1;
        while (true) {
            List<Course> courses = courseService.getCoursesByPage(page);
            if (courses.isEmpty()) {
                System.out.println(YELLOW + "Không có khóa học nào ở trang " + page + "." + RESET);
                if (page > 1) page--;
                else break;
            } else {
                System.out.println(CYAN + "\nDanh sách khóa học (Trang " + page + "):" + RESET);
                System.out.println("----------------------------------------------------------------------");
                System.out.printf("%-10s %-20s %-10s %-20s %-15s\n",
                        "ID", "Tên khóa học", "Thời lượng", "Giảng viên", "Ngày tạo");
                System.out.println("----------------------------------------------------------------------");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                for (Course course : courses) {
                    System.out.printf("%-10s %-20s %-10d %-20s %-15s\n",
                            course.getCourseId(),
                            course.getName(),
                            course.getDuration(),
                            course.getInstructor(),
                            course.getCreateAt().format(dtf));
                }
                System.out.println("--------------------------------------------------");
            }
            System.out.print(GREEN + "Nhấn 'n' để xem trang tiếp theo, 'p' để quay lại, hoặc 'q' để thoát: " + RESET);
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("n")) page++;
            else if (input.equals("p") && page > 1) page--;
            else if (input.equals("q")) break;
            else System.out.println(RED + "Lựa chọn không hợp lệ!" + RESET);
        }
    }

    private static void addNewCourse() {
        System.out.println(CYAN + "\nThêm mới khóa học:" + RESET);
        String courseId;
        while (true) {
            System.out.print("Nhập mã khóa học (CXXXX): ");
            courseId = scanner.nextLine();
            if (courseId.matches("C\\d{4}") && courseService.findCourseById(courseId) == null) break;
            System.out.println(RED + "Mã khóa học không hợp lệ hoặc đã tồn tại!" + RESET);
        }
        String name = CourseValidator.validateCourseName(scanner);
        int duration = CourseValidator.validateDuration(scanner);
        String instructor = CourseValidator.validateInstructor(scanner);
        LocalDate createAt = LocalDate.now(); // Use current date

        Course course = new Course(courseId, name, duration, instructor, createAt);
        if (courseService.addCourse(course)) {
            System.out.println(GREEN + "Thêm khóa học thành công!" + RESET);
        } else {
            System.out.println(RED + "Thêm khóa học thất bại!" + RESET);
        }
    }

    private static void editCourse() {
        System.out.println(CYAN + "\nChỉnh sửa thông tin khóa học:" + RESET);
        System.out.print("Nhập mã khóa học (CXXXX): ");
        String courseId = scanner.nextLine();
        Course course = courseService.findCourseById(courseId);
        if (course == null) {
            System.out.println(RED + "Không tìm thấy khóa học với mã " + courseId + "!" + RESET);
            return;
        }

        System.out.println(CYAN + "\nThông tin khóa học:" + RESET);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("ID: %s, Tên: %s, Thời lượng: %d, Giảng viên: %s, Ngày tạo: %s\n",
                course.getCourseId(), course.getName(), course.getDuration(),
                course.getInstructor(), course.getCreateAt().format(dtf));

        int choice;
        do {
            System.out.println(PURPLE + "\n╔══════════════════════════════════════╗");
            System.out.println("║ " + CYAN + "Chọn thuộc tính để chỉnh sửa" + PURPLE + "         ║");
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
                        course.setName(CourseValidator.validateCourseName(scanner));
                        break;
                    case 2:
                        course.setDuration(CourseValidator.validateDuration(scanner));
                        break;
                    case 3:
                        course.setInstructor(CourseValidator.validateInstructor(scanner));
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
}