package spring.org.nutz.castor.castor;

import java.lang.reflect.Array;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.json.Json;
import spring.org.nutz.lang.Lang;
import spring.org.nutz.lang.Strings;

public class String2Array extends Castor<String, Object> {

    public String2Array() {
        this.fromClass = String.class;
        this.toClass = Array.class;
    }

    @Override
    public Object cast(String src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        if (Strings.isQuoteByIgnoreBlank(src, '[', ']')) {
            return Json.fromJson(toType, src);
        }
        String[] ss = Strings.splitIgnoreBlank(src);
        return Lang.array2array(ss, toType.getComponentType());
    }

}
