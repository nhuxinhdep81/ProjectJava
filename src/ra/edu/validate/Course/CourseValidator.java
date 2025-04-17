package ra.edu.validate.Course;

import ra.edu.validate.Validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CourseValidator {

    public static String validateCourseName(Scanner sc) {
        return Validator.validateString("Nhập tên khóa học: ", sc, 1, 50);
    }

    public static int validateDuration(Scanner sc) {
        int duration;
        do {
            duration = Validator.validateInteger("Nhập thời lượng khóa học (số buổi): ", sc);
            if (duration <= 0) {
                System.out.println("Thời lượng phải lớn hơn 0.");
            }
        } while (duration <= 0);
        return duration;
    }

    public static String validateInstructor(Scanner sc) {
        return Validator.validateString("Nhập tên giảng viên: ", sc, 1, 100);
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}
