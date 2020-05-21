package spring.org.nutz.el.opt.custom;

import java.util.List;

import spring.org.nutz.el.ElException;
import spring.org.nutz.el.opt.RunMethod;
import spring.org.nutz.plugin.Plugin;

/**
 * 去掉字符串两边的空格
 * @author juqkai(juqkai@gmail.com)
 *
 */
public class Trim implements RunMethod, Plugin {
    public Object run(List<Object> fetchParam) {
        if(fetchParam.size() <= 0){
            throw new ElException("trim方法参数错误");
        }
        String obj = (String) fetchParam.get(0);
        return obj.trim();
    }

    public boolean canWork() {
        return true;
    }

    public String fetchSelf() {
        return "trim";
    }
}
