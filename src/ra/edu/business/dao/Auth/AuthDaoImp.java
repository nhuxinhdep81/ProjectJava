package ra.edu.business.dao.Auth;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Admin;
import ra.edu.business.model.Student;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AuthDaoImp implements AuthDao {

    @Override
    public Student studentLogin(String email, String password) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call check_student_login(?, ?)}");
            callSt.setString(1, email);
            callSt.setString(2, password);
            rs = callSt.executeQuery();
            if (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("name"));
                student.setDob(rs.getDate("dob").toLocalDate());
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhone(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                student.setCreateAt(rs.getDate("create_at").toLocalDate());
                student.setActived(rs.getBoolean("isActived"));
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return null;
    }

    @Override
    public Admin adminLogin(String username, String password) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call check_admin_login(?, ?)}");
            callSt.setString(1, username);
            callSt.setString(2, password);
            rs = callSt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return null;
    }

    @Override
    public boolean registerStudent(Student student) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call register_student(?, ?, ?, ?, ?, ?, ?, ?)}");
            callSt.setString(1, student.getName());
            callSt.setDate(2, java.sql.Date.valueOf(student.getDob()));
            callSt.setString(3, student.getEmail());
            callSt.setBoolean(4, student.isSex());
            callSt.setString(5, student.getPhone() != null && student.getPhone().isEmpty() ? null : student.getPhone());
            callSt.setString(6, student.getPassword());
            callSt.registerOutParameter(7, java.sql.Types.BIT);
            callSt.registerOutParameter(8, java.sql.Types.VARCHAR);
            callSt.execute();

            boolean success = callSt.getBoolean(7);
            String message = callSt.getString(8);
            if (!success) {
                System.out.println(RED + message + RESET); // In thông điệp lỗi từ stored procedure
            }
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(RED + "Lỗi hệ thống khi đăng ký học viên." + RESET);
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callSt, null);
        }
    }

    @Override
    public boolean isStudentDeactivated(String email) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("SELECT isActived FROM student WHERE email = ?");
            callSt.setString(1, email);
            rs = callSt.executeQuery();
            if (rs.next()) {
                return !rs.getBoolean("isActived"); // Trả về true nếu isActived = 0 (bị vô hiệu hóa)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return false; // Nếu không tìm thấy email, trả về false
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public boolean save(Object o) {
        return false;
    }

    @Override
    public boolean update(Object o) {
        return false;
    }

    @Override
    public boolean delete(Object o) {
        return false;
    }

    // Thêm các hằng số màu từ MainApplication để sử dụng trong class này
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";
}