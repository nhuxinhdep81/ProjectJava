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
                student.setId(rs.getString("id"));
                student.setName(rs.getString("name"));
                student.setDob(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhone(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                student.setCreateAt(LocalDate.parse(rs.getString("create_at")));
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
}