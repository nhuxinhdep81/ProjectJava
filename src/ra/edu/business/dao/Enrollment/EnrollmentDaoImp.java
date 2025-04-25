package ra.edu.business.dao.Enrollment;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Enrollment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDaoImp implements EnrollmentDao {

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
    public List<Enrollment> getConfirmedEnrollmentsByCourse(int courseId, int page) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Enrollment> enrollments = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL get_confirmed_enrollments_by_course_pagination(?, ?)}");
            callSt.setInt(1, courseId);
            callSt.setInt(2, page);
            rs = callSt.executeQuery();
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setStudentId(rs.getInt("student_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
                enrollment.setStatus(rs.getString("status"));
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
    public int countConfirmedEnrollmentsByCourse(int courseId) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL count_confirmed_enrollments_by_course(?)}");
            callSt.setInt(1, courseId);
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
    public List<Enrollment> getDeniedCancelEnrollmentsByCourse(int courseId, int page) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Enrollment> enrollments = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL get_denied_cancel_enrollments_by_course_pagination(?, ?)}");
            callSt.setInt(1, courseId);
            callSt.setInt(2, page);
            rs = callSt.executeQuery();
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setStudentId(rs.getInt("student_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
                enrollment.setStatus(rs.getString("status"));
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
    public int countDeniedCancelEnrollmentsByCourse(int courseId) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL count_denied_cancel_enrollments_by_course(?)}");
            callSt.setInt(1, courseId);
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
    public List<Enrollment> getPendingEnrollments() {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Enrollment> enrollments = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL get_pending_enrollments()}");
            rs = callSt.executeQuery();
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setStudentId(rs.getInt("student_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
                enrollment.setStatus(rs.getString("status"));
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
    public boolean approveEnrollment(int enrollmentId, String action) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL approve_enrollment(?, ?, ?, ?)}");
            callSt.setInt(1, enrollmentId);
            callSt.setString(2, action);
            callSt.registerOutParameter(3, Types.BIT);
            callSt.registerOutParameter(4, Types.VARCHAR);
            callSt.execute();
            boolean success = callSt.getBoolean(3);
            if (!success) {
                String message = callSt.getString(4);
                System.err.println("Xử lý đơn đăng ký thất bại: " + message);
            }
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callSt, null);
        }
    }

    @Override
    public boolean deleteEnrollment(int courseId, int studentId) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL delete_enrollment(?, ?, ?, ?)}");
            callSt.setInt(1, courseId);
            callSt.setInt(2, studentId);
            callSt.registerOutParameter(3, Types.BIT);
            callSt.registerOutParameter(4, Types.VARCHAR);
            callSt.execute();
            boolean success = callSt.getBoolean(3);
            if (!success) {
                String message = callSt.getString(4);
                System.err.println("Xóa đơn đăng ký thất bại: " + message);
            }
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callSt, null);
        }
    }
}