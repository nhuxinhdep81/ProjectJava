package ra.edu.presentation.Student;

import ra.edu.business.model.Student;
import ra.edu.business.service.Student.StudentService;
import ra.edu.business.service.Student.StudentServiceImp;
import ra.edu.business.dao.Student.StudentDaoImp;
import ra.edu.validate.Student.StudentValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ManagementStudentUI {
    private static Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentServiceImp(new StudentDaoImp());
    private static final StudentValidator studentValidator = new StudentValidator();

    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String PURPLE = "\u001B[35m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    public static void displayStudentManagementUI() {
        int choice;
        do {
            System.out.println(PURPLE + "┌────────────────────────────────────────┐");
            System.out.println("│" + YELLOW + "           QUẢN LÝ HỌC VIÊN            " + PURPLE + " │");
            System.out.println("├────────────────────────────────────────┤");
            System.out.println("│ " + CYAN + "1. Hiển thị danh sách học viên        " + PURPLE + " │");
            System.out.println("│ " + CYAN + "2. Thêm mới học viên                  " + PURPLE + " │");
            System.out.println("│ " + CYAN + "3. Chỉnh sửa thông tin học viên       " + PURPLE + " │");
            System.out.println("│ " + CYAN + "4. Vô hiệu hóa học viên               " + PURPLE + " │");
            System.out.println("│ " + CYAN + "5. Tìm kiếm theo tên, email, ID       " + PURPLE + " │");
            System.out.println("│ " + CYAN + "6. Sắp xếp theo tên hoặc ID           " + PURPLE + " │");
            System.out.println("│ " + CYAN + "7. Quay về menu chính                 " + PURPLE + " │");
            System.out.println("└────────────────────────────────────────┘" + RESET);
            System.out.print(GREEN + "➤ Chọn chức năng (1-7): " + RESET);

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        displayStudentsWithPagination();
                        break;
                    case 2:
                        addNewStudent();
                        break;
                    case 3:
                        editStudent();
                        break;
                    case 4:
                        disableStudent();
                        break;
                    case 5:
                        searchStudentsByNameEmailId();
                        break;
                    case 6:
                        sortStudents();
                        break;
                    case 7:
                        System.out.println(YELLOW + "↩ Quay về menu chính..." + RESET);
                        break;
                    default:
                        System.out.println(RED + "❌ Lựa chọn không hợp lệ! Chọn từ 1-7." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Vui lòng nhập số từ 1-7!" + RESET);
                choice = -1;
            }
        } while (choice != 7);
    }

    private static void displayStudentsWithPagination() {
        int page = 1;
        int totalPages = studentService.countStudents();
        while (true) {
            List<Student> students = studentService.getStudentsByPage(page);
            displayStudentList(students, page, totalPages, "Danh sách học viên");
            page = handlePaginationInput(page, totalPages);
            if (page == -1) break;
        }
    }

    private static void addNewStudent() {
        System.out.println(CYAN + "\n✚ Thêm mới học viên:" + RESET);
        String name = studentValidator.validateStudentName(scanner);
        LocalDate dob = studentValidator.validateDob(scanner);
        String email = studentValidator.validateEmail(scanner, 0);
        boolean sex = studentValidator.validateSex(scanner);
        String phone = studentValidator.validatePhone(scanner);
        String password = studentValidator.validatePassword(scanner);
        LocalDate createAt = LocalDate.now();

        Student student = new Student(0, name, dob, email, sex, phone, password, createAt, true);
        if (studentService.save(student)) {
            System.out.println(GREEN + "✅ Thêm học viên thành công!" + RESET);
        }
    }

    private static void editStudent() {
        System.out.println(CYAN + "\n✎ Chỉnh sửa thông tin học viên:" + RESET);
        System.out.print("Nhập ID học viên: ");
        int studentId;
        try {
            studentId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(RED + "❌ ID học viên phải là số!" + RESET);
            return;
        }
        Student student = studentService.findStudentById(studentId);
        if (student == null) {
            System.out.println(RED + "❌ Không tìm thấy học viên với ID " + studentId + "!" + RESET);
            return;
        }

        System.out.println(CYAN + "\nThông tin học viên:" + RESET);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("┌────┬────────────────────┬────────────┬─────────────────────────┬──────────┬──────────────┬────────────┬─────────────────┐");
        System.out.printf("│ %-2s │ %-18s │ %-10s │ %-23s │ %-8s│ %-12s │ %-10s │ %-8s      │%n",
                "ID", "Tên", "Ngày sinh", "Email", "Giới tính", "Điện thoại", "Ngày tạo", "Trạng thái");
        System.out.println("├────┼────────────────────┼────────────┼─────────────────────────┼──────────┼──────────────┼────────────┼─────────────────┤");
        System.out.printf("│ %-2d │ %-18.18s │ %-10s │ %-23.23s │ %-8s │ %-12s │ %-10s │ %-8s      │%n",
                student.getStudentId(),
                student.getName(),
                student.getDob().format(dtf),
                student.getEmail(),
                student.isSex() ? "Nam" : "Nữ",
                student.getPhone() != null ? student.getPhone() : "N/A",
                student.getCreateAt().format(dtf),
                student.isActived() ? "Đang hoạt động" : "Vô hiệu hoá");
        System.out.println("└────┴────────────────────┴────────────┴─────────────────────────┴──────────┴──────────────┴────────────┴─────────────────┘");

        int choice;
        do {
            System.out.println(PURPLE + "┌──────────────────────────────┐");
            System.out.println("│" + CYAN + "   Chọn thuộc tính chỉnh sửa " + PURPLE + " │");
            System.out.println("├──────────────────────────────┤");
            System.out.println("│ " + CYAN + "1. Tên học viên            " + PURPLE + "  │");
            System.out.println("│ " + CYAN + "2. Ngày sinh               " + PURPLE + "  │");
            System.out.println("│ " + CYAN + "3. Email                   " + PURPLE + "  │");
            System.out.println("│ " + CYAN + "4. Giới tính               " + PURPLE + "  │");
            System.out.println("│ " + CYAN + "5. Điện thoại              " + PURPLE + "  │");
            System.out.println("│ " + CYAN + "6. Mật khẩu                " + PURPLE + "  │");
            System.out.println("│ " + CYAN + "7. Hoàn tất chỉnh sửa      " + PURPLE + "  │");
            System.out.println("└──────────────────────────────┘" + RESET);
            System.out.print(GREEN + "➤ Chọn (1-7): " + RESET);

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        student.setName(studentValidator.validateStudentName(scanner));
                        break;
                    case 2:
                        student.setDob(studentValidator.validateDob(scanner));
                        break;
                    case 3:
                        student.setEmail(studentValidator.validateEmail(scanner, student.getStudentId()));
                        break;
                    case 4:
                        student.setSex(studentValidator.validateSex(scanner));
                        break;
                    case 5:
                        student.setPhone(studentValidator.validatePhone(scanner));
                        break;
                    case 6:
                        student.setPassword(studentValidator.validatePassword(scanner));
                        break;
                    case 7:
                        if (studentService.update(student)) {
                            System.out.println(GREEN + "✅ Cập nhật học viên thành công!" + RESET);
                        }
                        return;
                    default:
                        System.out.println(RED + "❌ Lựa chọn không hợp lệ!" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Vui lòng nhập số từ 1-7!" + RESET);
            }
        } while (true);
    }

    private static void disableStudent() {
        System.out.println(CYAN + "\n⛔ Vô hiệu hóa học viên:" + RESET);
        System.out.print("Nhập ID học viên: ");
        int studentId;
        try {
            studentId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(RED + "❌ ID học viên phải là số!" + RESET);
            return;
        }
        Student student = studentService.findStudentById(studentId);
        if (student == null) {
            System.out.println(RED + "❌ Không tìm thấy học viên với ID " + studentId + "!" + RESET);
            return;
        }
        if (!student.isActived()) {
            System.out.println(RED + "❌ Học viên đã bị vô hiệu hóa!" + RESET);
            return;
        }

        System.out.println(CYAN + "\nThông tin học viên:" + RESET);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("ID: %d | Tên: %s | Ngày sinh: %s | Email: %s | Giới tính: %s | SĐT: %s | Ngày tạo: %s | Trạng thái: %s\n",
                student.getStudentId(), student.getName(), student.getDob().format(dtf), student.getEmail(),
                student.isSex() ? "Nam" : "Nữ", student.getPhone() != null ? student.getPhone() : "N/A", student.getCreateAt().format(dtf),
                student.isActived() ? "Kích hoạt" : "Vô hiệu hóa");
        System.out.print(YELLOW + "⚠ Bạn có chắc muốn vô hiệu hóa? (1: Có, 0: Không): " + RESET);
        String confirm;
        try {
            confirm = scanner.nextLine().trim();
            if (!confirm.equals("1") && !confirm.equals("0")) {
                System.out.println(RED + "❌ Vui lòng nhập 1 hoặc 0!" + RESET);
                return;
            }
        } catch (Exception e) {
            System.out.println(RED + "❌ Lựa chọn không hợp lệ!" + RESET);
            return;
        }

        if (confirm.equals("1")) {
            if (studentService.disableStudentById(studentId)) {
                System.out.println(GREEN + "✅ Vô hiệu hóa học viên thành công!" + RESET);
            }
        } else {
            System.out.println(YELLOW + "↩ Hủy vô hiệu hóa." + RESET);
        }
    }

    private static void searchStudentsByNameEmailId() {
        System.out.println(CYAN + "\n🔍 Tìm kiếm học viên:" + RESET);
        System.out.print("Nhập tên, email hoặc ID: ");
        String searchValue = scanner.nextLine().trim();
        if (searchValue.isEmpty()) {
            System.out.println(RED + "❌ Giá trị tìm kiếm không được trống!" + RESET);
            return;
        }

        int page = 1;
        int totalPages = studentService.countStudentsByNameEmailId(searchValue);
        while (true) {
            List<Student> students = studentService.searchByNameEmailId(searchValue, page);
            displayStudentList(students, page, totalPages, "Kết quả tìm kiếm: \"" + searchValue + "\"");
            page = handlePaginationInput(page, totalPages);
            if (page == -1) break;
        }
    }

    private static void sortStudents() {
        System.out.println(CYAN + "\n↕ Sắp xếp danh sách học viên:" + RESET);
        int fieldChoice;
        String field = "";
        do {
            System.out.println(PURPLE + "┌──────────────────────────┐");
            System.out.println("│" + CYAN + "   Chọn tiêu chí sắp xếp " + PURPLE + " │");
            System.out.println("├──────────────────────────┤");
            System.out.println("│ " + CYAN + "1. Theo tên học viên    " + PURPLE + " │");
            System.out.println("│ " + CYAN + "2. Theo ID học viên     " + PURPLE + " │");
            System.out.println("│ " + CYAN + "3. Quay lại             " + PURPLE + " │");
            System.out.println("└──────────────────────────┘" + RESET);
            System.out.print(GREEN + "➤ Chọn (1-3): " + RESET);

            try {
                fieldChoice = Integer.parseInt(scanner.nextLine());
                switch (fieldChoice) {
                    case 1:
                        field = "name";
                        break;
                    case 2:
                        field = "student_id";
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println(RED + "❌ Lựa chọn không hợp lệ!" + RESET);
                        continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Vui lòng nhập số từ 1-3!" + RESET);
            }
        } while (true);

        int orderChoice;
        String order = "";
        do {
            System.out.println(PURPLE + "┌──────────────────────────┐");
            System.out.println("│" + CYAN + "   Chọn thứ tự sắp xếp   " + PURPLE + " │");
            System.out.println("├──────────────────────────┤");
            System.out.println("│ " + CYAN + "1. Tăng dần            " + PURPLE + "  │");
            System.out.println("│ " + CYAN + "2. Giảm dần            " + PURPLE + "  │");
            System.out.println("│ " + CYAN + "3. Quay lại            " + PURPLE + "  │");
            System.out.println("└──────────────────────────┘" + RESET);
            System.out.print(GREEN + "➤ Chọn (1-3): " + RESET);

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
                        System.out.println(RED + "❌ Lựa chọn không hợp lệ!" + RESET);
                        continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Vui lòng nhập số từ 1-3!" + RESET);
            }
        } while (true);

        int page = 1;
        int totalPages = studentService.countStudents();
        while (true) {
            List<Student> students = studentService.sortByField(field, order, page);
            String sortDesc = "Sắp xếp theo " + (field.equals("name") ? "tên" : "ID") + " (" + (order.equals("ASC") ? "tăng dần" : "giảm dần") + ")";
            displayStudentList(students, page, totalPages, sortDesc);
            page = handlePaginationInput(page, totalPages);
            if (page == -1) break;
        }
    }

    private static void displayStudentList(List<Student> students, int page, int totalPages, String title) {
        System.out.println(CYAN + "\n" + title + " (Trang " + page + "/" + totalPages + ")" + RESET);
        if (students.isEmpty()) {
            System.out.println(YELLOW + "⚠ Không có học viên nào ở trang này." + RESET);
        } else {
            System.out.println("┌────┬────────────────────┬────────────┬─────────────────────────┬───────────┬──────────────┬────────────┬───────────┐");
            System.out.printf("│ %-2s │ %-18s │ %-10s │ %-23s │ %-8s │ %-12s │ %-10s │ %-7s│%n",
                    "ID", "Tên", "Ngày sinh", "Email", "Giới tính", "Điện thoại", "Ngày tạo", "Trạng thái");
            System.out.println("├────┼────────────────────┼────────────┼─────────────────────────┼───────────┼──────────────┼────────────┼───────────┤");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Student student : students) {
                System.out.printf("│ %-2d │ %-18.18s │ %-10s │ %-23.23s │ %-9s │ %-12s │ %-10s │ %-8s │%n",
                        student.getStudentId(),
                        student.getName(),
                        student.getDob().format(dtf),
                        student.getEmail(),
                        student.isSex() ? "Nam" : "Nữ",
                        student.getPhone() != null ? student.getPhone() : "N/A",
                        student.getCreateAt().format(dtf),
                        student.isActived() ? "Kích hoạt" : "Vô hiệu");
            }
            System.out.println("└────┴────────────────────┴────────────┴─────────────────────────┴───────────┴──────────────┴────────────┴───────────┘");
        }
        System.out.print(YELLOW + "Trang: ");
        for (int i = 1; i <= totalPages; i++) {
            System.out.print(i == page ? "[" + i + "] " : i + " ");
        }
        System.out.println(RESET);
    }

    private static int handlePaginationInput(int currentPage, int totalPages) {
        System.out.print(GREEN + "➤ Nhập số trang (1-" + totalPages + "), 'n': tiếp, 'p': trước, 'q': thoát: " + RESET);
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
                    System.out.println(RED + "❌ Trang phải từ 1 đến " + totalPages + "!" + RESET);
                    return currentPage;
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Lựa chọn không hợp lệ!" + RESET);
                return currentPage;
            }
        }
    }
}