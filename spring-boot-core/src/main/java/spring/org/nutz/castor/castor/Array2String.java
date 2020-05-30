package spring.org.nutz.castor.castor;

import java.lang.reflect.Array;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.json.Json;
import spring.org.nutz.json.JsonFormat;
import spring.org.nutz.lang.Lang;

public class Array2String extends Castor<Object, String> {

    public Array2String() {
        this.fromClass = Array.class;
        this.toClass = String.class;
    }

    @Override
    public String cast(Object src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        if (null != src && CharSequence.class.isAssignableFrom(src.getClass().getComponentType())) {
            return Lang.concat(",", (CharSequence[]) src).toString();
        }
        return Json.toJson(src, JsonFormat.compact());
    }

}
