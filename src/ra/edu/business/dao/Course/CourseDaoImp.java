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
                course.setCourseId(rs.getInt("course_id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreateAt(rs.getDate("create_at").toLocalDate());
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
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
            callSt = conn.prepareCall("{CALL add_course(?, ?, ?, ?)}");
            callSt.setString(1, course.getName());
            callSt.setInt(2, course.getDuration());
            callSt.setString(3, course.getInstructor());
            callSt.setDate(4, java.sql.Date.valueOf(course.getCreateAt()));
            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callSt, null);
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
            callSt.setInt(1, course.getCourseId());
            callSt.setString(2, course.getName());
            callSt.setInt(3, course.getDuration());
            callSt.setString(4, course.getInstructor());
            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callSt, null);
        }
    }

    @Override
    public Course findCourseById(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL find_course_by_id(?)}");
            callSt.setInt(1, id);
            rs = callSt.executeQuery();
            if (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
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
    public List<Course> searchByName(String name, int page) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL search_courses_by_name_pagination(?, ?)}");
            callSt.setString(1, name);
            callSt.setInt(2, page);
            rs = callSt.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreateAt(rs.getDate("create_at").toLocalDate());
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return courses;
    }

    @Override
    public List<Course> sort(String field, String order, int page) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL sort_courses_pagination(?, ?, ?)}");
            callSt.setString(1, field);
            callSt.setString(2, order);
            callSt.setInt(3, page);
            rs = callSt.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreateAt(rs.getDate("create_at").toLocalDate());
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return courses;
    }

    @Override
    public int countCourses() {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL count_courses()}");
            rs = callSt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_pages");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return 0;
    }

    @Override
    public int countCoursesByName(String name) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL count_courses_by_name(?)}");
            callSt.setString(1, name);
            rs = callSt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_pages");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return 0;
    }

    @Override
    public boolean existsByName(String name) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL check_course_name_exists(?)}");
            callSt.setString(1, name);
            rs = callSt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return false;
    }

    @Override
    public boolean existsByNameExceptId(String name, int courseId) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL check_course_name_exists_except_id(?, ?)}");
            callSt.setString(1, name);
            callSt.setInt(2, courseId);
            rs = callSt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return false;
    }

    @Override
    public List findAll() {
        return new ArrayList<>(); // Not implemented
    }

    @Override
    public boolean delete(Object o) {
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL delete_course(?)}");
            callSt.setInt(1, course.getCourseId());
            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callSt, null);
        }
    }
}