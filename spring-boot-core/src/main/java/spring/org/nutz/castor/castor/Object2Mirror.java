package spring.org.nutz.castor.castor;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.lang.Mirror;

@SuppressWarnings({"rawtypes"})
public class Object2Mirror extends Castor<Object, Mirror> {

    @Override
    public Mirror cast(Object src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return Mirror.me(src.getClass());
    }

}
