package ra.edu.business.dao.Student;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImp implements StudentDao {

    // Thêm các hằng số màu để sử dụng trong class này
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    @Override
    public List<Student> findAll() {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL get_all_students()}");
            rs = callSt.executeQuery();
            while (rs.next()) {
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
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return students;
    }

    @Override
    public List<Student> pagination(int page) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL get_students_pagination(?)}");
            callSt.setInt(1, page);
            rs = callSt.executeQuery();
            while (rs.next()) {
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
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return students;
    }

    @Override
    public boolean save(Student student) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL add_student(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            callSt.setString(1, student.getName());
            callSt.setDate(2, java.sql.Date.valueOf(student.getDob()));
            callSt.setString(3, student.getEmail());
            callSt.setBoolean(4, student.isSex());
            callSt.setString(5, student.getPhone());
            callSt.setString(6, student.getPassword());
            callSt.setDate(7, java.sql.Date.valueOf(student.getCreateAt()));
            callSt.registerOutParameter(8, Types.BIT);
            callSt.registerOutParameter(9, Types.VARCHAR);
            callSt.execute();

            boolean success = callSt.getBoolean(8);
            String message = callSt.getString(9);
            if (!success) {
                System.out.println(RED + message + RESET);
            }
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(RED + "Lỗi hệ thống khi thêm học viên." + RESET);
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callSt, null);
        }
    }

    @Override
    public boolean update(Student student) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL update_student(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            callSt.setInt(1, student.getStudentId());
            callSt.setString(2, student.getName());
            callSt.setDate(3, java.sql.Date.valueOf(student.getDob()));
            callSt.setString(4, student.getEmail());
            callSt.setBoolean(5, student.isSex());
            callSt.setString(6, student.getPhone());
            callSt.setString(7, student.getPassword());
            callSt.registerOutParameter(8, Types.BIT);
            callSt.registerOutParameter(9, Types.VARCHAR);
            callSt.execute();

            boolean success = callSt.getBoolean(8);
            String message = callSt.getString(9);
            if (!success) {
                System.out.println(RED + message + RESET);
            }
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(RED + "Lỗi hệ thống khi cập nhật học viên." + RESET);
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callSt, null);
        }
    }

    @Override
    public boolean delete(Student student) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL delete_student(?, ?, ?)}");
            callSt.setInt(1, student.getStudentId());
            callSt.registerOutParameter(2, Types.BIT);
            callSt.registerOutParameter(3, Types.VARCHAR);
            callSt.execute();
            boolean success = callSt.getBoolean(2);
            if (!success) {
                String message = callSt.getString(3);
                System.out.println(RED + "Vô hiệu hóa học viên thất bại: " + message + RESET);
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
    public Student findStudentById(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL find_student_by_id(?)}");
            callSt.setInt(1, id);
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
    public List<Student> searchByNameEmailId(String searchValue, int page) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL search_students_by_name_email_id_pagination(?, ?)}");
            callSt.setString(1, searchValue);
            callSt.setInt(2, page);
            rs = callSt.executeQuery();
            while (rs.next()) {
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
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return students;
    }

    @Override
    public List<Student> sort(String field, String order, int page) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL sort_students_pagination(?, ?, ?)}");
            callSt.setString(1, field);
            callSt.setString(2, order);
            callSt.setInt(3, page);
            rs = callSt.executeQuery();
            while (rs.next()) {
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
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt, rs);
        }
        return students;
    }

    @Override
    public int countStudents() {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL count_students()}");
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
    public int countStudentsByNameEmailId(String searchValue) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL count_students_by_name_email_id(?)}");
            callSt.setString(1, searchValue);
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
    public boolean updatePassword(int studentId, String newPassword) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL update_student_password(?, ?, ?, ?)}");
            callSt.setInt(1, studentId);
            callSt.setString(2, newPassword);
            callSt.registerOutParameter(3, Types.BIT);
            callSt.registerOutParameter(4, Types.VARCHAR);
            callSt.execute();
            boolean success = callSt.getBoolean(3);
            if (!success) {
                String message = callSt.getString(4);
                System.out.println(RED + "Cập nhật mật khẩu thất bại: " + message + RESET);
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
    public boolean checkEmailExists(String email, int studentId) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{CALL check_email_exists(?, ?, ?)}");
            callSt.setString(1, email);
            callSt.setInt(2, studentId);
            callSt.registerOutParameter(3, Types.BIT);
            callSt.execute();
            return callSt.getBoolean(3);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callSt, null);
        }
    }
}