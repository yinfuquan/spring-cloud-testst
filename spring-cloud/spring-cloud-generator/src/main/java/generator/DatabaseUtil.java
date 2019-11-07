package generator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/bzl_face_db?useSSL=false&characterEncoding=UTF8&allowMultiQueries=true&serverTimezone=UTC&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private static final String SQL = "SELECT * FROM ";// 数据库操作

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    /**
     * 获取数据库下的所有表名
     */
    public static List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
//获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
//从元数据中获取到所有的表名
            rs = db.getTables("bzl_face_db", null, null, new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
            }
        }
        return tableNames;
    }

    /**
     * 获取数据库下的所有表名
     */
    public static List<String> getTableNamesByConnection() throws SQLException {

        Connection conn = DriverManager.getConnection(SuperGenerator.url, SuperGenerator.username,SuperGenerator.password);

        List<String> tableNames = new ArrayList<>();
        ResultSet rs = null;
        try {
//获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
//从元数据中获取到所有的表名
            rs = db.getTables(SuperGenerator.database, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
            }
        }
        return tableNames;
    }


}