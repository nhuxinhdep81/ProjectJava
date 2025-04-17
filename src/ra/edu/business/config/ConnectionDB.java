package ra.edu.business.config;

import java.sql.*;

public class ConnectionDB {
    private static final String URl = "jdbc:mysql://localhost:3306/course_management_system";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection openConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URl, USER, PASSWORD);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection conn, CallableStatement callSt, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (callSt != null) {
            try {
                callSt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

