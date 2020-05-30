package spring.org.nutz.castor.castor;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;

public class Object2String extends Castor<Object, String> {

    @Override
    public String cast(Object src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return src.toString();
    }

}
