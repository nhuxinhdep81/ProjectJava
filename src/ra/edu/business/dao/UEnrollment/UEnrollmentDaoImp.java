package ra.edu.business.dao.UEnrollment;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Enrollment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UEnrollmentDaoImp implements UEnrollmentDao {

    @Override
    public List<Enrollment> findAll() {
        return new ArrayList<>();
    }

    @Override
    public boolean save(Enrollment enrollment) {
        return false;
    }

    @Override
    public boolean update(Enrollment enrollment) {
        return false;
    }

    @Override
    public boolean delete(Enrollment enrollment) {
        return false;
    }

    @Override
    public boolean registerCourse(int studentId, int courseId) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL register_course(?, ?, ?, ?)}");
            callSt.setInt(1, studentId);
            callSt.setInt(2, courseId);
            callSt.registerOutParameter(3, Types.BIT);
            callSt.registerOutParameter(4, Types.VARCHAR);
            callSt.execute();
            boolean success = callSt.getBoolean(3);
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callSt, null);
        }
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudent(int studentId, int page) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Enrollment> enrollments = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL get_enrollments_by_student_pagination(?, ?)}");
            callSt.setInt(1, studentId);
            callSt.setInt(2, page);
            rs = callSt.executeQuery();
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setStudentId(rs.getInt("student_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
                enrollment.setStatus(rs.getString("status"));
                enrollment.setCourseName(rs.getString("course_name"));
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return enrollments;
    }

    @Override
    public int countEnrollmentsByStudent(int studentId) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL count_enrollments_by_student(?)}");
            callSt.setInt(1, studentId);
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
    public List<Enrollment> getWaitingEnrollmentsByStudent(int studentId) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Enrollment> enrollments = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL get_waiting_enrollments_by_student(?)}");
            callSt.setInt(1, studentId);
            rs = callSt.executeQuery();
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setStudentId(rs.getInt("student_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
                enrollment.setStatus(rs.getString("status"));
                enrollment.setCourseName(rs.getString("course_name"));
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return enrollments;
    }

    @Override
    public boolean cancelEnrollment(int enrollmentId, int studentId) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL cancel_enrollment(?, ?, ?, ?)}");
            callSt.setInt(1, enrollmentId);
            callSt.setInt(2, studentId);
            callSt.registerOutParameter(3, Types.BIT);
            callSt.registerOutParameter(4, Types.VARCHAR);
            callSt.execute();
            boolean success = callSt.getBoolean(3);
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callSt, null);
        }
    }
}