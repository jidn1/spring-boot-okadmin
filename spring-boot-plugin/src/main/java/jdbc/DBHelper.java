package jdbc;

import code.plugin.gr.Code;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/18 14:26
 * @Description:
 */
public class DBHelper {

    public static final String name = "com.mysql.jdbc.Driver";
    public Connection conn = null;
    public PreparedStatement pst = null;

    public DBHelper(String sql) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(Code.config.getProperty("url"), Code.config.getProperty("user"), Code.config.getProperty("password"));
            this.pst = this.conn.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DBHelper db = new DBHelper("show columns from app_user");
        try {
            ResultSet ret = db.pst.executeQuery();
            while (ret.next()) {
                String column = ret.getString(1);
                String type = ret.getString(2);
                System.out.println(column + "=========" + type);
            }
            ret.close();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
