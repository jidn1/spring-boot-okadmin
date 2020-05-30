package spring.org.nutz.castor.castor;

import java.util.Map;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.json.Json;

@SuppressWarnings({"rawtypes"})
public class Map2String extends Castor<Map, String> {

    @Override
    public String cast(Map src, Class<?> toType, String... args) throws FailToCastObjectException {
        return Json.toJson(src);
    }

}
