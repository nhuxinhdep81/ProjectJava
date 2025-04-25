package ra.edu.business.dao.Statistic;

import java.util.List;
import java.util.Map;

public interface StatisticDao {
    Map<String, Integer> getTotalCoursesAndStudents();
    List<Map<String, Object>> getStudentsPerCourse();
    List<Map<String, Object>> getTop5CoursesByStudents();
    List<Map<String, Object>> getCoursesWithMoreThan10Students();
}