package com.oauth.vo;

import java.util.List;

/**
 * @Copyright: 正经吉
 * @author: MenuTree
 * @version: V1.0
 * @Date: 2020/7/12
 */
public class MenuTree {

    private String title;

    private boolean spread;

    private String mkey;

    private List<MenuTree> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public List<MenuTree> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTree> children) {
        this.children = children;
    }
}
