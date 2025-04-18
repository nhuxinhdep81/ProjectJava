package ra.edu.validate.Course;

import ra.edu.business.service.Course.CourseService;
import ra.edu.validate.Validator;

import java.util.Scanner;

public class CourseValidator {
    private static final Validator validator = new Validator();
    private final CourseService courseService;

    public CourseValidator(CourseService courseService) {
        this.courseService = courseService;
    }

    public String validateCourseName(Scanner scanner) {
        while (true) {
            String name = validator.validateString("Nhập tên khóa học (1-100 ký tự): ", scanner, 1, 100, "Tên khóa học");
            if (courseService.existsByName(name)) {
                System.out.println("\u001B[31m" + "Tên khóa học '" + name + "' đã tồn tại! Vui lòng nhập tên khác." + "\u001B[0m");
            } else {
                return name;
            }
        }
    }

    public String validateCourseNameForUpdate(Scanner scanner, int courseId) {
        while (true) {
            String name = validator.validateString("Nhập tên khóa học (1-100 ký tự): ", scanner, 1, 100, "Tên khóa học");
            if (courseService.existsByNameExceptId(name, courseId)) {
                System.out.println("\u001B[31m" + "Tên khóa học '" + name + "' đã tồn tại! Vui lòng nhập tên khác." + "\u001B[0m");
            } else {
                return name;
            }
        }
    }

    public int validateDuration(Scanner scanner) {
        return validator.validateInteger("Nhập thời lượng khóa học (1-1000 ngày): ", scanner, 1, 1000);
    }

    public String validateInstructor(Scanner scanner) {
        return validator.validateString("Nhập tên giảng viên (1-100 ký tự): ", scanner, 1, 100, "Tên giảng viên");
    }
}