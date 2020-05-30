package spring.org.nutz.castor.castor;

import java.util.Map;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.lang.Lang;

@SuppressWarnings({"rawtypes"})
public class Map2Object extends Castor<Map, Object> {

    @Override
    public Object cast(Map src, Class<?> toType, String... args) throws FailToCastObjectException {
        return Lang.map2Object(src, toType);
    }

}
