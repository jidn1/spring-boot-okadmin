package model;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/18 14:24
 * @Description:
 */
public class Comments {

    private String name;
    private Boolean search;
    private String condit;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSearch() {
        return this.search;
    }

    public void setSearch(Boolean search) {
        this.search = search;
    }

    public String getCondit() {
        return this.condit;
    }

    public void setCondit(String condit) {
        this.condit = condit;
    }
}
