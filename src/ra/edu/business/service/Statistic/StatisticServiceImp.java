package ra.edu.business.service.Statistic;

import ra.edu.business.dao.Statistic.StatisticDao;

import java.util.List;
import java.util.Map;

public class StatisticServiceImp implements StatisticService {
    private final StatisticDao statisticDao;

    public StatisticServiceImp(StatisticDao statisticDao) {
        this.statisticDao = statisticDao;
    }

    @Override
    public Map<String, Integer> getTotalCoursesAndStudents() {
        return statisticDao.getTotalCoursesAndStudents();
    }

    @Override
    public List<Map<String, Object>> getStudentsPerCourse() {
        return statisticDao.getStudentsPerCourse();
    }

    @Override
    public List<Map<String, Object>> getTop5CoursesByStudents() {
        return statisticDao.getTop5CoursesByStudents();
    }

    @Override
    public List<Map<String, Object>> getCoursesWithMoreThan10Students() {
        return statisticDao.getCoursesWithMoreThan10Students();
    }
}