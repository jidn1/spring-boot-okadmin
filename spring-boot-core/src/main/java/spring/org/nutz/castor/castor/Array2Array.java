package spring.org.nutz.castor.castor;

import java.lang.reflect.Array;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.lang.Lang;

public class Array2Array extends Castor<Object, Object> {

    public Array2Array() {
        this.fromClass = Array.class;
        this.toClass = Array.class;
    }

    @Override
    public Object cast(Object src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return Lang.array2array(src, toType.getComponentType());
    }

}
