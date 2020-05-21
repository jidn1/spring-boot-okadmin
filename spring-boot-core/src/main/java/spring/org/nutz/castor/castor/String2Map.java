package spring.org.nutz.castor.castor;

import java.util.Map;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.json.Json;
import spring.org.nutz.lang.Lang;

@SuppressWarnings({"rawtypes"})
public class String2Map extends Castor<String, Map> {

    @Override
    public Map cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
        return (Map) Json.fromJson(Lang.inr(src));
    }

}
