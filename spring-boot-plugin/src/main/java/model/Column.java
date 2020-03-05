package model;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/18 14:23
 * @Description:
 */
public class Column {

    private String column = "";
    private String getcolumn = "";
    private String mysqltype = "";
    private String javatype = "";
    private String key = "";
    private String comments;


    public String getColumn() {
        return this.column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getMysqltype() {
        return this.mysqltype;
    }

    public void setMysqltype(String mysqltype) {
        this.mysqltype = mysqltype;
    }

    public String getJavatype() {
        return replaceType(this.mysqltype);
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String replaceType(String type) {
        if (type.contains("bigint")) {
            return "Long";
        }
        if (type.contains("datetime")) {
            return "Date";
        }
        if (type.contains("varchar")) {
            return "String";
        }
        if (type.contains("int")) {
            return "Integer";
        }
        if (type.contains("decimal")) {
            return "BigDecimal";
        }
        return "String";
    }

    public String getGetcolumn() {
        this.getcolumn = (this.column.substring(0, 1).toUpperCase() + this.column.substring(1));
        return this.getcolumn;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
