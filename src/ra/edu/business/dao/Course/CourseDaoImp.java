package ra.edu.business.dao.Course;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImp implements CourseDao {

    @Override
    public List<Course> pagination(int page) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL get_courses_pagination(?)}");
            callSt.setInt(1, page);
            rs = callSt.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getString("course_id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreateAt(rs.getDate("create_at").toLocalDate());
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (callSt != null) callSt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return courses;
    }

    @Override
    public boolean save(Object o) {
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL add_course(?, ?, ?, ?, ?)}");
            callSt.setString(1, course.getCourseId());
            callSt.setString(2, course.getName());
            callSt.setInt(3, course.getDuration());
            callSt.setString(4, course.getInstructor());
            callSt.setDate(5, java.sql.Date.valueOf(course.getCreateAt()));
            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (callSt != null) callSt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean update(Object o) {
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL update_course(?, ?, ?, ?)}");
            callSt.setString(1, course.getCourseId());
            callSt.setString(2, course.getName());
            callSt.setInt(3, course.getDuration());
            callSt.setString(4, course.getInstructor());
            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (callSt != null) callSt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Course findCourseById(String id) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL find_course_by_id(?)}");
            callSt.setString(1, id);
            rs = callSt.executeQuery();
            if (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getString("course_id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreateAt(rs.getDate("create_at").toLocalDate());
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return null;
    }

    @Override
    public List<Course> search(String name) {
        return new ArrayList<>(); // Not implemented
    }

    @Override
    public List<Course> sort() {
        return new ArrayList<>(); // Not implemented
    }

    @Override
    public List findAll() {
        return new ArrayList<>(); // Not implemented
    }

    @Override
    public boolean delete(Object o) {
        return false; // Not implemented
    }
}