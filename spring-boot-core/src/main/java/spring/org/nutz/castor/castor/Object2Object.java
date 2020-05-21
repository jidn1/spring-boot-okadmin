package spring.org.nutz.castor.castor;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.lang.Mirror;

public class Object2Object extends Castor<Object, Object> {

    @Override
    public Object cast(Object src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return Mirror.me(toType).born(src);
    }

}
