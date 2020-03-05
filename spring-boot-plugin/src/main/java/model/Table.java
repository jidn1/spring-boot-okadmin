package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/18 14:19
 * @Description:
 */
public class Table {

    private List<Column> columns;

    public List<Column> getColumns() {
        return this.columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public Set<String> getColumnsType() {
        Set<String> set = new HashSet();
        for (Column c : this.columns) {
            set.add(c.getJavatype());
        }
        return set;
    }
}
