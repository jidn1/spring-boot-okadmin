package spring.org.nutz.castor.castor;

import java.lang.reflect.Array;
import java.util.Map;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.lang.Lang;

@SuppressWarnings({"rawtypes"})
public class Map2Array extends Castor<Map, Object> {

    public Map2Array() {
        this.fromClass = Map.class;
        this.toClass = Array.class;
    }

    @Override
    public Object cast(Map src, Class<?> toType, String... args) throws FailToCastObjectException {
        return Lang.collection2array(src.values(), toType.getComponentType());
    }

}
