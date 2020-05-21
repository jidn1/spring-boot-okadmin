package spring.org.nutz.el.obj;

import spring.org.nutz.el.ElCache;

public interface Elobj {
    public String getVal();
    public Object fetchVal();
    public void setEc(ElCache ec);
}
