package ra.edu.business.dao.Statistic;

import ra.edu.business.config.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticDaoImp implements StatisticDao {

    @Override
    public Map<String, Integer> getTotalCoursesAndStudents() {
        Connection conn = null;
        CallableStatement callSt = null;
        Map<String, Integer> result = new HashMap<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL get_total_courses_and_students(?, ?)}");
            callSt.registerOutParameter(1, Types.INTEGER);
            callSt.registerOutParameter(2, Types.INTEGER);
            callSt.execute();
            result.put("totalCourses", callSt.getInt(1));
            result.put("totalStudents", callSt.getInt(2));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, null);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getStudentsPerCourse() {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL get_students_per_course()}");
            rs = callSt.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("courseId", rs.getInt("course_id"));
                row.put("courseName", rs.getString("course_name"));
                row.put("studentCount", rs.getInt("student_count"));
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getTop5CoursesByStudents() {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL get_top_5_courses_by_students()}");
            rs = callSt.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("courseId", rs.getInt("course_id"));
                row.put("courseName", rs.getString("course_name"));
                row.put("studentCount", rs.getInt("student_count"));
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getCoursesWithMoreThan10Students() {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL get_courses_with_more_than_10_students()}");
            rs = callSt.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("courseId", rs.getInt("course_id"));
                row.put("courseName", rs.getString("course_name"));
                row.put("studentCount", rs.getInt("student_count"));
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return result;
    }
}