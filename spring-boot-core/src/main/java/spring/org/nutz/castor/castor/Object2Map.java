package spring.org.nutz.castor.castor;

import java.util.Map;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.lang.Lang;

@SuppressWarnings({"rawtypes"})
public class Object2Map extends Castor<Object, Map> {

    @SuppressWarnings("unchecked")
    @Override
    public Map cast(Object src, Class<?> toType, String... args) throws FailToCastObjectException {
        return Lang.obj2map(src, (Class<? extends Map>) ((Class<? extends Map>) toType));
    }

}
