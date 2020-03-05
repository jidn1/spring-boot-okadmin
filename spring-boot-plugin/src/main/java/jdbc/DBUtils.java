package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Column;
import model.Table;


/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/18 14:27
 * @Description:
 */
public class DBUtils {

    public static Table getTabel(String tableName) {
        DBHelper db = new DBHelper("show full columns from " + tableName + " ");
        Table table = new Table();
        ArrayList<Column> list = new ArrayList();
        try {
            ResultSet ret = db.pst.executeQuery();
            while (ret.next()) {
                if ((!"saasid".equals(ret.getString(1))) &&
                        (!"created".equals(ret.getString(1))) &&
                        (!"modified".equals(ret.getString(1))) &&
                        (!"createTime".equals(ret.getString(1))) &&
                        (!"creator".equals(ret.getString(1))) &&
                        (!"editor".equals(ret.getString(1))) &&
                        (!"editTime".equals(ret.getString(1)))) {
                    Column column = new Column();
                    column.setColumn(ret.getString(1));
                    column.setMysqltype(ret.getString(2));
                    column.setKey(ret.getString(5));
                    if ((ret.getString(9) != null) && (ret.getString(9) != "")) {
                        column.setComments(ret.getString(9));
                    }
                    list.add(column);
                }
            }
            table.setColumns(list);
            ret.close();
            db.close();
            return table;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
